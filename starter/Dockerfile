# means to build our image upon java:8 image from Docker Hub.
FROM java:8
# We define that a volume named /tmp should exist
VOLUME /tmp
# We add a file from the local file system, naming it “app.jar.” The renaming isn't necessary, just an option available
ADD build/libs/match-api-server-0.0.1-SNAPSHOT.jar app.jar
# We state that we want to open port 8080 on the container﻿
EXPOSE 8080
# We run a command on the system to “touch” the file. This ensures a file modification date on the app.jar file
RUN sh -c 'touch /app.jar'
# The ENTRYPOINT command is the “what to run to ‘start'” command — we run Java, setting our Spring Mongo property and a quick additional property to speed up the Tomcat startup time, and then point it at our jar
#ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://matchapimongodb/test", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "-Dspring.profiles.active=database"]
ENTRYPOINT java -jar -Dspring.profiles.active=database app.jar