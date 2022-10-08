#Run stage
FROM openjdk:19
ADD . /app
WORKDIR /app
RUN ./mvnw clean package
CMD [ "java", "-jar", "/app/target/quarkus-app/quarkus-run.jar" ]