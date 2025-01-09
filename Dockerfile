FROM openjdk:17-jdk

WORKDIR /demo

COPY . .

RUN ./mvnw clean package

CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
