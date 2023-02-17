FROM maven:3.8.7-eclipse-temurin-17-alpine
WORKDIR .
COPY . .
RUN mvn clean install
EXPOSE 8081
CMD ["mvn", "spring-boot:run"]