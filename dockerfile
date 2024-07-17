#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /target/InterBankingAPI-0.0.1-SNAPSHOT.jar interbankingApi.jar

# Copy the certificate file (assuming it's named interbanking.crt)
COPY interbanking.crt /usr/local/share/ca-certificates/interbanking.crt

# Update certificates in the Docker image
RUN apt-get update && apt-get install -y ca-certificates && update-ca-certificates

# Ensure the Java keystore directory exists
RUN mkdir -p /etc/ssl/certs/java/

# Add the certificate to the Java keystore (cacerts)
RUN keytool -importcert -trustcacerts -file /usr/local/share/ca-certificates/interbanking.crt -alias interbanking -keystore /etc/ssl/certs/java/cacerts -storepass changeit -noprompt

# Expose the port
EXPOSE 8080

# Set the entry point
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "interbankingApi.jar"]
