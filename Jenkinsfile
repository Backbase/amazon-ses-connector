@Library('jenkins-shared-library@latest') l1
@Library('cx-shared-library@nojira-with-interl-repo') l2

def MVN_VERSION = 'maven 3.8.1'
def JDK_VERSION = 'jdk-17'

def checkHarborImageExists(String image, String tag) {
    withEnv(["image=${image}", "tag=${tag}"]) {
        withCredentials([
                usernamePassword(credentialsId: 'artifactory_auto-test', usernameVariable: 'repouser', passwordVariable: 'repopwd')
        ]) {
            return sh(
                    script: 'curl --user ${repouser}:${repopwd} --silent -f -lL https://harbor.backbase.eu/api/repositories/development/${image}/tags/${tag}/ > /dev/null',
                    returnStatus: true) == 0
        }
    }
}

def checkoutCommonCiScripts() {
    sh 'rm common-ci-scripts -rf'
    dir('common-ci-scripts') {
        git([credentialsId: "stash_clippyservice_ssh_key", url: "ssh://git@stash.backbase.com:7999/sdlc/common-ci-scripts.git", branch: "master"])
    }
}

pipeline {
    // ref: https://sdlc.backbase.eu/docs/reference/reference.html#jenkins-agents
    agent {
        kubernetes {
            inheritFrom "kaniko"
        }
    }

    // override the default Java version used in this pipeline
    tools {
        jdk JDK_VERSION
    }

    parameters {

    }

    environment {
        def workspace = pwd()
        PROJECT_KEY = 'MSG'
        LOGS_FOLDER = "${workspace}" + "/test_logs"
        HARBOR_CREDS = credentials("harbor-credentials")
        HARBOR_REPO_DEVELOPMENT = "development"
        HARBOR_REPO_STAGING = "staging"
        SES_EMAIL_INTEGRATION_SERVICE_IMAGE_VERSION = "${GIT_COMMIT}"
    }

    triggers {
        pollSCM('')
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    ciSCMCheckout()
                    checkoutCommonCiScripts()
                    pom = readMavenPom(file: 'pom.xml')

                    RELEASE_POM_VERSION = pom.version.replace("-SNAPSHOT", "")
                    TARGET_BRANCH = "${env.CHANGE_BRANCH ?: env.BRANCH_NAME}"
                    DO_RELEASE = TARGET_BRANCH.startsWith("release/")
                    SERVICE_NAME = "ses-email-integration-service"
                    IMAGE_EXISTS = checkHarborImageExists("${SERVICE_NAME}", "${GIT_COMMIT}")

                    env.BUILD_SLACK_CHANNEL = "herald-builds"
                    echo "BUILD_SLACK_CHANNEL: ${BUILD_SLACK_CHANNEL}"
                }

                script {
                    if (DO_RELEASE) {
                        currentBuild.displayName = "${SERVICE_NAME}-release"
                    } else {
                        currentBuild.displayName = "${TARGET_BRANCH}-${BUILD_NUMBER}"
                    }
                    sh('mkdir ${LOGS_FOLDER}')
                }
            }
        }

        stage('Build project and unit tests') {
            when {
                expression { IMAGE_EXISTS == false }
            }
            steps {
                script {
                    withMaven(maven: MVN_VERSION, jdk: JDK_VERSION, mavenSettingsConfig: 'artifacts_bb_com_settings') {
                        sh 'mvn --no-transfer-progress -T 2 clean install'
                    }
                }
            }
        }

        stage('Static Quality Analysis with Sonar') {
            steps {
                runMavenSonarScan_v2("${PROJECT_KEY}:${pom.artifactId}")
            }
        }

        stage('Security scans') {
            stages {
                stage('Security scan latest') {
                    when {
                        expression { DO_RELEASE == false }
                    }
                    steps {
                        withMaven(maven: MVN_VERSION, jdk: JDK_VERSION, mavenSettingsConfig: "${SETTINGS_ID}") {
                            withCredentials([string(credentialsId: 'blackduck-api', variable: 'BD_HUB_TOKEN')]) {
                                sh """
                                    bash common-ci-scripts/securityScan.sh \
                                        --projectType maven \
                                        --projectName ses-email-integration-service \
                                        --sourcePath ses-email-integration-service \
                                        --scanArtifacts ses-email-integration-service/target/ses-email-integration-service-${pom.version}.war
                                """
                            }
                        }
                    }
                }

                stage('Security scan release') {
                    when {
                        expression { DO_RELEASE }
                    }
                    steps {
                        withMaven(maven: MVN_VERSION, jdk: JDK_VERSION, mavenSettingsConfig: "${SETTINGS_ID}") {
                            withCredentials([string(credentialsId: 'blackduck-api', variable: 'BD_HUB_TOKEN')]) {
                                sh """
                                    bash common-ci-scripts/securityScan.sh \
                                        --projectType maven \
                                        --projectName ses-email-integration-service \
                                        --sourcePath ses-email-integration-service \
                                        --version ${RELEASE_POM_VERSION} \
                                        --scanArtifacts ses-email-integration-service/target/ses-email-integration-service-${pom.version}.war
                                """
                            }
                        }
                    }
                }
            }
        }

        stage('Build and push docker images') {
            when {
                expression { IMAGE_EXISTS == false }
            }
            steps {
                dir("${env.WORKSPACE}/ses-email-integration-service") {
                    mvnBuild('mvn package --no-transfer-progress -Dmaven.test.skip.exec=true -P docker-image -Ddocker.repo.project=${HARBOR_REPO_DEVELOPMENT} -Djib.to.tags=${GIT_COMMIT} -Djib.to.auth.username=${HARBOR_CREDS_USR} -Djib.to.auth.password=${HARBOR_CREDS_PSW}')
                }
            }
        }


        stage('Release artifacts') {
            when {
                expression { DO_RELEASE }
            }
            environment {
                PROJECT_VERSION = releaser.getNextReleaseVersion(RELEASE_POM_VERSION)
                ARTIFACT_NAME = "${SERVICE_NAME}"
            }
            steps {
                script {
                    env.SES_EMAIL_INTEGRATION_SERVICE_IMAGE_VERSION = "${env.PROJECT_VERSION}"
                    releaser.tagReleaseVersion(env.PROJECT_VERSION)
                    currentBuild.displayName = "${SERVICE_NAME}-" + env.PROJECT_VERSION
                }
                withCredentials([usernamePassword(credentialsId: "artifactory_auto-release",
                        usernameVariable: 'ARTIFACTORY_USERNAME', passwordVariable: 'ARTIFACTORY_PASSWORD')]) {
                    withMaven(maven: MVN_VERSION, jdk: JDK_VERSION) {
                        sh '''
                         echo "Deploying release with version ${PROJECT_VERSION}"
                         ci/scripts/deploy-artifacts.sh
                        '''
                    }
                }

                dir("${env.WORKSPACE}/${ARTIFACT_NAME}-service") {
                    mvnBuild('mvn package -Dmaven.test.skip.exec=true -P docker-image -Ddocker.repo.project=staging -Djib.to.tags=${PROJECT_VERSION} -Djib.to.auth.username=${HARBOR_CREDS_USR} -Djib.to.auth.password=${HARBOR_CREDS_PSW}')
                }

                node ('aws-static-jdk11') {
                    withMaven(maven: MVN_VERSION, jdk: JDK_VERSION) {
                       checkoutCommonCiScripts()
                       withCredentials([usernamePassword(credentialsId: 'harbor-credentials', usernameVariable: 'HARBOR_CREDS_USR', passwordVariable: 'HARBOR_CREDS_PWD')]) {
                           sh '''
                           common-ci-scripts/copyImageTag.sh --from "staging/${ARTIFACT_NAME}:${PROJECT_VERSION}" --to  "staging/${ARTIFACT_NAME}:release" --overwrite  true
                           '''
                       }
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'test_logs/*', fingerprint: true, allowEmptyArchive: true
        }
        aborted {
            slackSend(channel: "#${BUILD_SLACK_CHANNEL}", color: '#D3D3D3', message: " 🤫 Build for `${env.JOB_NAME}` has been aborted \n${env.RUN_DISPLAY_URL}")
        }
        failure {
            slackSend(channel: "#${BUILD_SLACK_CHANNEL}", color: '#FF0000', message: " 👎 @here Build for `${env.JOB_NAME}` has failed \n${env.RUN_DISPLAY_URL}")
        }
        success {
            slackSend(channel: "#${BUILD_SLACK_CHANNEL}", color: '#00FF00', message: " 👍 Build for `${env.JOB_NAME}` has been successful \n${env.RUN_DISPLAY_URL}")
        }
    }
}
