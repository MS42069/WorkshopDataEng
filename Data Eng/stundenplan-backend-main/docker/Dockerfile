ARG GIT_COMMIT=""
ARG TARGET_DIR="target"
ARG TARGET_JAR="softwareprojekt-0.0.1-SNAPSHOT.jar"
ARG PORT="8080"

FROM docker.io/maven:3.8.5-openjdk-17 as build
WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn install -DskipTests
RUN mvn compile -DskipTests


FROM docker.io/openjdk:17-alpine
ARG TARGET_DIR
ARG TARGET_JAR
ARG PORT
ARG GIT_COMMIT


WORKDIR /app
COPY --from=build /app/${TARGET_DIR}/${TARGET_JAR} App.jar
COPY --from=build /app/src/main/resources/cas.xml /app/src/main/resources/cas.xml
EXPOSE ${PORT}

LABEL git_commit="${GIT_COMMIT}"


ENTRYPOINT [ "java", "-jar", "App.jar" ]
