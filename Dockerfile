FROM openjdk:8-jdk-alpine
ADD reference-auth-server-webapp/target/hspc-reference-auth-server-webapp-*.war app.war
ADD reference-auth-server-webapp/target/dependency/jetty-runner.jar jetty-runner.jar
ADD reference-auth-server-webapp/src/main/resources/jetty.xml jetty.xml
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar jetty-runner.jar --config jetty.xml app.war" ]