#!/usr/bin/env bash

CONFIG_FILE="../aws/task-config.json"

export PROJECT_NAME="auth"

export PROJECT_FULL_NAME="${PROJECT_NAME}-${TARGET_ENV}"

export PROJECT_VERSION=$(mvn -f ../pom.xml -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)

export DOCKER_IMAGE_COORDINATES="hspconsortium/${PROJECT_NAME}:${PROJECT_VERSION}"

export SPRING_PROFILES_ACTIVE="${TARGET_ENV},users-firebase"

export TEMPLATE_FILE="../aws/task-definition.json"

export VERSION_SNAPSHOT_REGEX="^[0-9]+\.[0-9]+\.[0-9]+-SNAPSHOT$"

export VERSION_RELEASE_REGEX="^[0-9]+\.[0-9]+\.[0-9]+$"
