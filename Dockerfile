FROM openjdk:11-jdk

ARG WAR_FILE

ADD $WAR_FILE app.war

ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -jar /app.war
