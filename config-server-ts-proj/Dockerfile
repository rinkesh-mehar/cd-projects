# Start with a base image containing Java runtime
FROM meisterplan/jdk-base:11

# Add Maintainer Info
LABEL maintainer="vivek.gajbhiye@cropdata.in"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 7073 available to the world outside this container
EXPOSE 7073

# The application's jar file
ARG JAR_FILE=target/Cropdata-Config-Server-1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} Cropdata-Config-Server-1.0.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=7073","-jar","/Cropdata-Config-Server-1.0.jar"]