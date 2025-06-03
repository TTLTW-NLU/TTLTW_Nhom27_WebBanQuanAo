# ================================
# Stage 1: Build WAR file with Maven
# ================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Tạo thư mục làm việc
WORKDIR /app

# Copy toàn bộ source code vào container
COPY . .

# Chạy Maven để build WAR file (bỏ qua test để tránh lỗi nếu chưa config test)
RUN mvn clean package -DskipTests


# ================================
# Stage 2: Deploy WAR vào Tomcat 10.1 + Java 21
# ================================
FROM tomcat:10.1.36-jdk21-temurin

# Xoá ứng dụng mặc định của Tomcat để tránh xung đột
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR từ builder stage sang thư mục webapps của Tomcat
COPY --from=builder /app/target/WebBanQuanAo-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Ghi đè server.xml để tắt cổng shutdown
COPY tomcat-server.xml /usr/local/tomcat/conf/server.xml

# Mở cổng 8080
EXPOSE 8080

# Lệnh chạy mặc định
CMD ["catalina.sh", "run"]
