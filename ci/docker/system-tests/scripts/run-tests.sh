#!/usr/bin/env bash
set -e

echo "GATEWAY IS SET UP TO ${GATEWAY_BASEURI}"

#bash check-health.sh "ping" "${GATEWAY_BASEURI}/api/auth/login"
#bash check-health.sh "health" "${GATEWAY_BASEURI}/actuator/health/liveness"
#bash check-health.sh "health" "${GATEWAY_BASEURI}/api/communication/actuator/health"
sleep 60
java -jar app.jar "$@"
