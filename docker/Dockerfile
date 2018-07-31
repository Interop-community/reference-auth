FROM openjdk:8-jdk-alpine

# mapped to host for filesystem mounting
VOLUME /tmp

# build time arguments
ARG WAR_FILE
ADD ${WAR_FILE} app.war

ARG JETTY_RUNNER_FILE
ADD ${JETTY_RUNNER_FILE} jetty-runner.jar

ARG JETTY_CONFIG_FILE
ADD ${JETTY_CONFIG_FILE} jetty.xml

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","jetty-runner.jar","--config","jetty.xml","app.war"]
