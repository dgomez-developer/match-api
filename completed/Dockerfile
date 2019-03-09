# means to build our image upon java:8 image from Docker Hub.
FROM java:8
# We define that a volume named /tmp should exist
VOLUME /tmp
# We add a file from the local file system, naming it “app.jar.” The renaming isn't necessary, just an option available
#RUN mkdir server
COPY $PWD .
#RUN sh -c 'ls ./server'
RUN ./gradlew clean build

# We state that we want to open port 8080 on the container﻿
EXPOSE 8080
# The ENTRYPOINT command is the “what to run to ‘start'” command — we run Java, setting our Spring Mongo property and a quick additional property to speed up the Tomcat startup time, and then point it at our jar
ENTRYPOINT java -jar -Dspring.profiles.active=database build/libs/match-api-server-0.0.1-SNAPSHOT.jar