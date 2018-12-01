# Match API

### Running mongoDB locally

```
brew install mongodb
mkdir -p /data/db
sudo chown -R $USER /data/db
mongod
```

### Running MongoDB in Docker

* Crear los docker containers:

```bash
docker network create matchapimongo
docker run -d --name matchapimongodb --network=matchapimongo -v ~/matchapidb:/data/db mongo
docker exec -it CONTAINER_ID bash
```

```bash
docker build -t springboot-mongo:latest .
docker run -p 8080:8080 --name matchapispringbootmongo --network=matchapimongo springboot-mongo
```

* Useful commands:

```bash
docker start -ai ${CONTAINER_ID}
```
```bash
docker rm -f $(docker ps -a -q)
```
```bash
docker rmi -f $(docker images -a -q)
```
```bash
docker volume rm $(docker volume ls -q)
```
```bash
docker network rm $(docker network ls | tail -n+2 | awk '{if($2 !~ /bridge|none|host/){ print $1 }}')
```

## References

* [Hibernate with kotlin](https://kotlinexpertise.com/hibernate-with-kotlin-spring-boot/)
* [Accessing data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Dockerize a spring boot app with mong DB](https://www.linkedin.com/pulse/dockerize-spring-boot-mongodb-application-aymen-kanzari/)
