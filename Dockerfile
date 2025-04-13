# Base image
FROM openjdk:17-jdk-slim

# JAR 파일 위치
ARG JAR_FILE=target/SpringWebProject1-0.0.1-SNAPSHOT.jar

# JAR 파일을 컨테이너에 복사
COPY ${JAR_FILE} app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
