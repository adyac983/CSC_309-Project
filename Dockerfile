
FROM eclipse-temurin:20-alpine
LABEL authors="mathleap"
WORKDIR /app
COPY target/CSC_309-Project-1.0-SNAPSHOT.jar /app
CMD ["java", "-jar", "CSC_309-Project-1.0-SNAPSHOT.jar"]
