#!/usr/bin/env bash
# Although we have several modules in this repo we only release one `communication`
# We flatten the pom.xml to no longer have a parent and we copy api spec and event zip to the same artifact.
# This means from the user perspective there is only one artifact com.backbase.communication:communication:${version}:${classifier}

echo "Updating versions"
mvn -s settings.xml -B -q -U versions:set -DnewVersion=${PROJECT_VERSION}
mvn -s settings.xml -pl ${ARTIFACT_NAME}-spec -B -q -U versions:set -DnewVersion=${PROJECT_VERSION}

echo "Rebuilding artifacts"
echo --------------------------------------------------------
mvn -s settings.xml -B clean flatten:flatten install -Dmaven.test.skip=true

echo "Releasing ${ARTIFACT_NAME} war to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -Dversion=${PROJECT_VERSION} \
  -DgroupId="com.backbase.${ARTIFACT_NAME}" \
  -DartifactId="${ARTIFACT_NAME}" \
  -DpomFile="${ARTIFACT_NAME}-service/.flattened-pom.xml" \
  -Dfile="${ARTIFACT_NAME}-service/target/${ARTIFACT_NAME}-${PROJECT_VERSION}.war"

echo "Releasing ${ARTIFACT_NAME} exec jar to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgroupId="com.backbase.${ARTIFACT_NAME}" \
  -DartifactId="${ARTIFACT_NAME}" \
  -Dversion=${PROJECT_VERSION} \
  -DgeneratePom=false \
  -Dfile="${ARTIFACT_NAME}-service/target/${ARTIFACT_NAME}-${PROJECT_VERSION}-exec.jar"

echo "Releasing ${ARTIFACT_NAME} spec zip ${PROJECT_VERSION} to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgroupId="com.backbase.${ARTIFACT_NAME}" \
  -DartifactId="${ARTIFACT_NAME}" \
  -Dversion=${PROJECT_VERSION} \
  -Dpackaging=zip \
  -DgeneratePom=false \
  -Dclassifier=api \
  -Dfile="${ARTIFACT_NAME}-spec/target/${ARTIFACT_NAME}-spec-${PROJECT_VERSION}.zip"

echo "Releasing ${ARTIFACT_NAME} events zip ${PROJECT_VERSION} to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgroupId="com.backbase.${ARTIFACT_NAME}" \
  -DartifactId="${ARTIFACT_NAME}" \
  -Dversion=${PROJECT_VERSION} \
  -Dpackaging=zip \
  -DgeneratePom=false \
  -Dclassifier=events \
  -Dfile="${ARTIFACT_NAME}-spec/target/${ARTIFACT_NAME}-spec-${PROJECT_VERSION}-events.zip"