FROM openjdk:11
COPY build/jar/Fortinet.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]