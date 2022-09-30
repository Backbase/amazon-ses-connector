#!/usr/bin/env bash
set -e

echo "GATEWAY IS SET UP TO ${GATEWAY_BASEURI}"

java -jar app.jar "$@"
