FROM openjdk:11-jdk

ADD reference-auth-server-webapp/target/hspc-reference-auth-server-webapp.war app.war

ADD reference-auth-server-webapp/target/dependency/jetty-runner.jar  jetty-runner.jar
#ADD reference-auth-server-webapp/src/main/resources/jetty-ssl.xml jetty-ssl.xml
ADD reference-auth-server-webapp/src/main/resources/jetty.xml jetty.xml

#ADD src/main/resources/mysql-ca/rdscacerts rdscacerts

#ADD ci/keystore-gen.sh pre-run.sh
#RUN chmod +x pre-run.sh

#ENTRYPOINT [ "sh", "-c", "./pre-run.sh && java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar jetty-runner.jar --config jetty-ssl .xml --path /auth app.war" ]
ENTRYPOINT [ "sh", "-c", "./pre-run.sh && java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar jetty-runner.jar --config jetty .xml --path /auth app.war" ]
#
#
#
#
#
#FROM openjdk:8-jdk-alpine
#
## mapped to host for filesystem mounting
#VOLUME /tmp
#
## build time arguments
#ARG WAR_FILE
#ADD ${WAR_FILE} app.war
#
#ARG JETTY_RUNNER_FILE
#ADD ${JETTY_RUNNER_FILE} jetty-runner.jar
#
#ARG JETTY_CONFIG_FILE
#ADD ${JETTY_CONFIG_FILE} jetty.xml
#
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","jetty-runner.jar","--config","jetty.xml","app.war"]
