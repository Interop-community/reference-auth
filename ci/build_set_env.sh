#!/usr/bin/env bash

set -e

echo "starting $0..."

export DOCKER_REPO="hspconsortium"; echo DOCKER_REPO
export IMAGE_NAME=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.artifactId}' --non-recursive exec:exec -f reference-auth-server-webapp/pom.xml); echo $IMAGE_NAME
export AWS_CONTAINER_NAME=hspc-reference-auth; echo $AWS_CONTAINER_NAME
export PROJECT_VERSION=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec); echo $PROJECT_VERSION
export IMAGE_COORDINATES="${DOCKER_REPO}/${IMAGE_NAME}:${PROJECT_VERSION}"; echo $IMAGE_COORDINATES;
export IMAGE_PORT="8060"
export IMAGE_MEMORY_RESERVATION="400"
export SPRING_PROFILES_ACTIVE="test,users-firebase";

echo "export DOCKER_REPO=$(DOCKER_REPO)" >> set_env.sh
echo "export IMAGE_NAME=$(IMAGE_NAME)" >> set_env.sh
echo "export AWS_CONTAINER_NAME=$(AWS_CONTAINER_NAME)" >> set_env.sh
echo "export PROJECT_VERSION=$(PROJECT_VERSION)" >> set_env.sh
echo "export IMAGE_COORDINATES=$(IMAGE_COORDINATES)" >> set_env.sh
echo "export IMAGE_PORT=$(IMAGE_PORT)" >> set_env.sh
echo "export IMAGE_MEMORY_RESERVATION=$(IMAGE_MEMORY_RESERVATION)" >> set_env.sh
echo "export SPRING_PROFILES_ACTIVE=$(SPRING_PROFILES_ACTIVE)" >> set_env.sh

chmod 755 set_env.sh
cat set_env.sh

echo "finished $0"
