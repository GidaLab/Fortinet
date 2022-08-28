# Fortinet Home Assignment

## General Notes

* Gradle was used as the build framework
* Spring-Boot was used as the Java and web-server framework
* More tests should be added for each module - web clients, controller, reputation services
* Cache is a local version meant to run in-process (not distributed)

## Build & Run

  * Building and running the app can be done directly from the IDE
  * From terminal - run the following commands (for Linux replace with `.\gradlew`)
    ```
    .\gradlew.bat clean build
    
    java -jar build/libs/Fortinet-0.0.1-SNAPSHOT.jar
    ```
  * From Docker - assuming gradle build was executed to produce artifact jar
    run from main project directory
    ```
    docker build -t fortinet .
    
    docker run -p 8080:8080 fortinet
    ```