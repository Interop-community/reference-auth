#!/usr/bin/env bash

mvn -V -B -f ../pom.xml -s settings.xml deploy -P DEPLOY-HSPC,hspc-nexus
rm ../reference-auth-server-webapp/target/*-sources.jar
rm ../reference-auth-server-webapp/target/*-javadoc.jar
