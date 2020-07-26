# Spring Actuator Sample by Kotlin

## Build Docker image

```bash
./gradlew docker
```

## Running

```bash
docker run -it mizunashi-mana/spring-actuator-demo:latest
```

And access http://localhost:8080/actuator/prometheus
