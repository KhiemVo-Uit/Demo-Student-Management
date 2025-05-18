FROM eclipse-temurin:17-jdk

RUN echo "✅ Docker is using correct Dockerfile"

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Tải dependency trước (tăng tốc build lần sau)
RUN ./mvnw dependency:go-offline

# Copy toàn bộ source code
COPY src ./src

# Không build jar ở đây nếu muốn hot reload, hoặc build nhanh với skip tests
# RUN ./mvnw clean install -DskipTests

# Chạy spring boot trực tiếp
CMD ["./mvnw", "spring-boot:run"]

