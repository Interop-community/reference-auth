#!/usr/bin/env bash

echo "Note: this should be run from the project root directory"

docker \
  build -t hspconsortium/auth:latest \
  --build-arg WAR_FILE=reference-auth-server-webapp/target/*.war \
  --build-arg JETTY_RUNNER_FILE=reference-auth-server-webapp/target/dependency/jetty-runner.jar \
  --build-arg JETTY_CONFIG_FILE=reference-auth-server-webapp/src/main/resources/jetty.xml  \
  .
