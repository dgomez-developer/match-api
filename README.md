# Match API

## Workshop Exercices (Draft)

### Ejercicio 1 (Live Demo): Victor

* Descargar Projecto de SpringInitializer
* Abrir con Intellij
* Añadir Server configuration con build
* Añadir dependencia `implementation('org.springframework.boot:spring-boot-starter-web')` en `build.gradle` y hacer `build`
* Definir `GET HelloWorld`

### Ejercicio 2 (Coding)

* Hacer un endpoint que te devuelva una lista de partidos.
* Cada partido se compone de 2 participantes.
* Cada participante tiene nombre y puntos.

### Ejercicio 3 (Live Demo): Débora

* Hacer un POST que añada un nuevo partido a un array en memoria.

### Ejercicio 4 (Coding)

* Crear un Repositorio.
* Mover nuestro array de partidos al repositorio.

### Ejercicio 5 (Live Demo): Victor

* Injectar el Repositorio
* Añadir dependencias JPA
```
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
implementation("com.h2database:h2")
implementation('com.fasterxml.jackson.module:jackson-module-kotlin')
```
* Crear un Repositorio JPA

### Ejercicio 6 (Coding)

* Crear un endpoint para borrar partidos

### Ejercicio 7 (Live Demo): Débora

* Crear local profile.

En VM options poner: `-Dspring.profiles.active=local`

### Ejercicio 8 (Coding)

* Crear un mongo repository usando un profile `database`.

`implementation group: 'org.springframework.boot', name: 'spring-boot-starter-data-mongodb', version: '2.1.0.RELEASE'`

* Install mongoDB on mac:

```
brew install mongodb
mkdir -p /data/db
sudo chown -R $USER /data/db
mongod
```

### Ejercicio 8 (Live Coding): Débora

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
