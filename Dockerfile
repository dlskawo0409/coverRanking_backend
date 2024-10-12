FROM openjdk:17-jdk-alpine
WORKDIR /app

# 필요한 리소스를 복사합니다.
COPY src/main/resources/application.properties src/main/resources/application.properties
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
