#!/usr/bin/env bash

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
  -DpomFile="${ARTIFACT_NAME}/.flattened-pom.xml" \
  -Dfile="${ARTIFACT_NAME}/target/${ARTIFACT_NAME}-${PROJECT_VERSION}.war"

echo "Releasing ${ARTIFACT_NAME} exec jar to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgroupId="com.backbase.${ARTIFACT_NAME}" \
  -DartifactId="${ARTIFACT_NAME}" \
  -Dversion=${PROJECT_VERSION} \
  -DgeneratePom=false \
  -Dfile="${ARTIFACT_NAME}/target/${ARTIFACT_NAME}-${PROJECT_VERSION}-exec.jar"


