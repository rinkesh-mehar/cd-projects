# Start with a base image containing Java runtime
FROM meisterplan/jdk-base:11

# Add Maintainer Info
LABEL maintainer="vivek.gajbhiye@cropdata.in"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
#EXPOSE 8097

# The application's jar file
ARG JAR_FILE=target/gstm-data-studio-api-1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} gstm-data-studio-api-1.0.jar



# Run the jar file 
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=8080","-Dspring.cloud.config.password=${config.password}","-Dspring.cloud.config.uri=${config.uri}","-Dspring.profiles.active=${ActiveProfile}","-Deureka.client.serviceUrl.defaultZone=${EUREKA_URI}","-jar","/DRK-1.0.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gstm-data-studio-api-1.0.jar"]
