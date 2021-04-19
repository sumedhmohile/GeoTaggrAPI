FROM openjdk:11

COPY target/GeoTaggrApi-0.0.1-SNAPSHOT.jar GeoTaggrApi-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/GeoTaggrApi-0.0.1-SNAPSHOT.jar"]