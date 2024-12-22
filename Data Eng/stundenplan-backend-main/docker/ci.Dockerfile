FROM eclipse-temurin:17
ARG TARGET_DIR="target"
ARG TARGET_JAR="softwareprojekt-0.0.1-SNAPSHOT.jar"
ARG PORT="8080"
ARG GIT_COMMIT

WORKDIR /app
COPY ${TARGET_DIR}/${TARGET_JAR} App.jar
COPY src/main/resources/cas.xml /app/src/main/resources/cas.xml
EXPOSE ${PORT}

LABEL git_commit="${GIT_COMMIT}"

ENTRYPOINT ["java", "-jar", "App.jar"]
# ENTRYPOINT java "${JAVA_OPTS}" -jar App.jar
