#!/usr/bin/env bash

mvn -V -B -f ../pom.xml -s ../settings.xml deploy -P DEPLOY-HSPC,hspc-nexus
rc=$?
if [[ ${rc} -ne 0 ]] ; then
  echo 'could not perform tests'; exit $rc
fi

rm ../reference-auth-server-webapp/target/*-sources.jar
rm ../reference-auth-server-webapp/target/*-javadoc.jar
