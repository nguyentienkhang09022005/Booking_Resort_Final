# Sử dụng image Java chính thức
FROM openjdk:17-jdk-slim

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy file .jar đã build vào container
COPY target/Booking_Resort-0.0.1-SNAPSHOT.jar BookingResort.jar

# Mở port 8080 (hoặc port app của bạn)
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "BookingResort.jar"]