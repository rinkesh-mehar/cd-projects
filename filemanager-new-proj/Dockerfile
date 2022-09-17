# Start with a base image containing Java runtime
FROM meisterplan/jdk-base:11

# Add Maintainer Info
LABEL maintainer="siddhant.rangari@cropdata.in"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 7074

# The application's jar file
ARG JAR_FILE=target/FileManager-1.0.jar

# Add the application's jar to the container
ADD ${JAR_FILE} FileManager-1.0.jar

#Declare env variable to get config password

#ENV config.uri uri
#ENV ActiveProfile default
#ENV EUREKA_URI http://localhost:8761/eureka

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/FileManager-1.0.jar"]
