FROM maven:3.9.9-amazoncorretto-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean install -P prod

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/jira-1.0.jar /app/app.jar
COPY ./resources /app/resources
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]