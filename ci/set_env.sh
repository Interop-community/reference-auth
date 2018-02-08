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

echo "finished $0"
