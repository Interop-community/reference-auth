#!/usr/bin/env bash

profile="local"

if [ $# -gt 0 ]; then
    if [ "$1" == "local" ] || [ "$1" == "firebase" ] || [ "$1" == "ldap" ]; then
        profile=$1
    else
        echo "Invalid argument: $profile.  Use \"local\", \"firebase\", or \"ldap\""
        exit 1;
    fi
fi

echo "using $profile profile..."

echo "starting reference-auth-server-webapp..."
cd reference-auth-server-webapp

set -x

java \
  -Xms64M \
  -Xmx128M \
  -Dspring.profiles.active=local,users-${profile} \
  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5060 \
  -jar target/dependency/jetty-runner.jar \
  --config src/main/resources/jetty.xml \
  target/*.war
