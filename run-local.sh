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

echo "using $profile profile..."

if [ $# -gt 1 ]; then
    if [ "$2" == "users-local" ] || [ "$2" == "users-firebase" ] || [ "$2" == "users-keycloak" ] || [ "$2" == "users-ldap" ]; then
        users=$2
    else
        echo "usage: $0 {local | test | prod} {users-local | users-firebase | users-keycloak | users-ldap}";
        exit 1;
    fi
fi

echo "using $users users..."

export SPRING_PROFILES_ACTIVE="${profile}, ${users}"

echo "SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}"

echo "starting reference-auth-server-webapp..."

# from the environment
echo "JASYPT_ENCRYPTOR_PASSWORD=${JASYPT_ENCRYPTOR_PASSWORD}"

echo "starting reference-auth-server-webapp..."
cd reference-auth-server-webapp

set -x

java \
  -Xms128M \
  -Xmx256M \
  -Dspring.profiles.active="${SPRING_PROFILES_ACTIVE}" \
  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5060 \
  -jar target/dependency/jetty-runner.jar \
  --config src/main/resources/jetty.xml \
  target/*.war
