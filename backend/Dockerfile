FROM eclipse-temurin:17
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw
CMD ["./mvnw", "spring-boot:run"]

