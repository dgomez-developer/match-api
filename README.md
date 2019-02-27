# Match API

## Tools
- Java: `brew cask install java`
- Intellj (needs JAVA_HOME variable declared in the system PATH).
- MongoDB Compass to visualize the data.

## Running the API locally

```
./gradlew clean bootRun -Dspring.profiles.active=database
```

```
./gradlew clean bootRun -Dspring.profiles.active=local
```

## Running mongoDB locally

```
brew install mongodb
mkdir -p /data/db
sudo chown -R $USER /data/db
mongod
```
