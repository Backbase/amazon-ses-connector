#!/usr/bin/env bash

echo "Updating versions"
mvn -s settings.xml -B -q -U versions:set -DnewVersion=${PROJECT_VERSION}

echo "Rebuilding artifacts"
echo --------------------------------------------------------
mvn -s settings.xml -B install -Dskip.unit.tests=true -Dskip.integration.tests=true

echo "Releasing ${ARTIFACT_NAME} war to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgeneratePom=true \
  -Dversion=${PROJECT_VERSION} \
  -DgroupId="com.backbase.communication" \
  -DartifactId="${ARTIFACT_NAME}" \
  -Dfile="target/${ARTIFACT_NAME}-${PROJECT_VERSION}.war"

echo "Releasing ${ARTIFACT_NAME} exec jar to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgroupId="com.backbase.communication" \
  -DartifactId="${ARTIFACT_NAME}" \
  -Dversion=${PROJECT_VERSION} \
  -DgeneratePom=false \
  -Dfile="target/${ARTIFACT_NAME}-${PROJECT_VERSION}-exec.jar"

echo "Releasing ${ARTIFACT_NAME} meta json to artifactory"
echo -------------------------------------------------------
mvn -s settings.xml -B -e build-helper:parse-version deploy:deploy-file -DupdateReleaseInfo=true \
  -Durl=https://artifacts.backbase.com/backbase-development-rc-releases \
  -DrepositoryId=backbase.artifacts.repository \
  -DgroupId="com.backbase.communication" \
  -DartifactId=${ARTIFACT_NAME} \
  -Dversion=${PROJECT_VERSION} \
  -DgeneratePom=false \
  -Dclassifier=meta \
  -Dfile="target/${ARTIFACT_NAME}-${PROJECT_VERSION}-meta.json"
