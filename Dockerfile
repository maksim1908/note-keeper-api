FROM maven:3-eclipse-temurin-22-alpine AS builder
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

FROM maven:3-eclipse-temurin-22-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]