FROM docker-proxy.backbase.eu/dockerhub/library/openjdk:17.0.2-slim

RUN apt-get update && apt-get install -y curl

WORKDIR /opt

COPY ci/docker/system-tests/scripts/run-tests.sh run-tests.sh

RUN ["chmod", "+x", "run-tests.sh"]

COPY ses-email-integration-test/target/es-email-integration-test-*-exec.jar app.jar

ENTRYPOINT ["sh", "./run-tests.sh"]