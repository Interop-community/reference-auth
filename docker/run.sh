#!/usr/bin/env bash

profile="local"
users="users-local"

if [ $# -gt 0 ]; then
    if [ "$1" == "local" ] || [ "$1" == "test" ] || [ "$1" == "prod" ]; then
        profile=$1
    else
        echo "usage: $0 {local | test | prod}";
        exit 1;
    fi
fi

if [ "$profile" == "local" ]; then
  echo "run-docker does not support local because the host database isn't mapped.  Use another profile or use docker-compose to also start a database container.";
  exit 1;
fi

echo "using $profile profile..."

if [ $# -gt 1 ]; then
    if [ "$2" == "users-local" ] || [ "$2" == "users-firebase" ] || [ "$2" == "users-ldap" ]; then
        users=$2
    else
        echo "usage: $0 {local | test | prod} {users-local | users-firebase | users-ldap}";
        exit 1;
    fi
fi

echo "using $users users..."

export SPRING_PROFILES_ACTIVE="${profile}, ${users}"

echo "SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}"

echo "starting reference-auth-server-webapp..."

# from the environment
echo "JASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD}"

. docker/build.sh

# run the container, using the arguments from the hosting environment
docker run -i -e "SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}" -e "JASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD}" -p 8060:8060 -t hspconsortium/auth:latest
