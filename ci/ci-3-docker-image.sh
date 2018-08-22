#!/usr/bin/env bash

docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}
cd ../docker; ./build.sh $DOCKER_IMAGE_COORDINATES
cd ../ci
echo "docker push..."
docker push "$DOCKER_IMAGE_COORDINATES"
