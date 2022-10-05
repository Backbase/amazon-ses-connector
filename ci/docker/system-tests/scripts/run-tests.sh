#!/usr/bin/env bash
set -e

#bash check-health.sh "ping" "${GATEWAY_BASEURI}/api/auth/login"
#bash check-health.sh "health" "${GATEWAY_BASEURI}/actuator/health/liveness"
#bash check-health.sh "health" "${GATEWAY_BASEURI}/api/communication/actuator/health"
sleep 100
java -jar app.jar "$@"
