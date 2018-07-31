#!/usr/bin/env bash

version="latest"

if [ $# -gt 0 ]; then
  version=$1
fi

# files must be in a folder or subfolder
mkdir -p target
cp ../reference-auth-server-webapp/target/*.war target
cp ../reference-auth-server-webapp/target/dependency/jetty-runner.jar target
cp ../reference-auth-server-webapp/src/main/resources/jetty.xml target

docker \
  build -t hspconsortium/auth:${version} \
  --build-arg WAR_FILE=target/*.war \
  --build-arg JETTY_RUNNER_FILE=target/jetty-runner.jar \
  --build-arg JETTY_CONFIG_FILE=target/jetty.xml  \
  .
