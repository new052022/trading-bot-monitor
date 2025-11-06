FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/trading-monitor-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8012

CMD ["java", "-jar", "app.jar"]