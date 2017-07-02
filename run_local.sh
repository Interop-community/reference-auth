#!/usr/bin/env bash

echo "starting reference-auth-server-firebase-webapp..."
cd reference-auth-server-firebase-webapp
java \
  -Xms64M \
  -Xmx128M \
  -jar target/dependency/jetty-runner.jar \
  --config src/main/resources/jetty.xml \
  target/*.war

# use this to run the war file using the maven jetty plugin
#mvn jetty:run

