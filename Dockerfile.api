FROM maven:3.6.3-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:8-jdk-jammy
COPY --from=build /home/app/target/pidev-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
