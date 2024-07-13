FROM maven:3.8.5-openjdk-17

#EXPOSE 8080

#WORKDIR /app
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run
COPY target/test-app-join-back-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"]