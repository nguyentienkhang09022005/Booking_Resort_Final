# Build stage (dùng Maven để build project)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage (chạy jar file)
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/Booking_Resort-0.0.1-SNAPSHOT.jar BookingResort.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "BookingResort.jar"]
