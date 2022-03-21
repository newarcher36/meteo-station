FROM openjdk:11
EXPOSE 8090
COPY target/meteo-station-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]