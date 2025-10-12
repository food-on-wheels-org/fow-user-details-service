FROM eclipse-temurin:17-jdk
WORKDIR /opt
COPY target/*.jar app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar