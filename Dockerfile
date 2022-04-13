# Build app from sources
FROM gradle:jdk11 as builder
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle bootJar -x test

## Build image from jar
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=builder /home/gradle/src/build/libs/person-data-app-0.0.1-SNAPSHOT.jar /app/person-data-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/person-data-app-0.0.1-SNAPSHOT.jar", "-Xmx", "1024m"]