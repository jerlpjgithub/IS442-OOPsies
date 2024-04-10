FROM openjdk:21-slim as build

RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

COPY ./pom.xml .
COPY ./src ./src

RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /app/target/server-0.0.1-SNAPSHOT.jar server.jar

ENTRYPOINT ["java","-jar","/app/server.jar"]