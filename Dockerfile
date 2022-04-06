FROM openjdk:11
EXPOSE 8090
RUN curl -fsSL https://deb.nodesource.com/setup_17.x | bash -
RUN apt-get install -y nodejs
COPY target/meteo-station-service-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]