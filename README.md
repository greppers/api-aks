# Network

Create the network:

```
docker network create greppers-network
```

# Dockerfiles

## MySQL

Build the image:

```
docker build --network=greppers-network -t greppers/holus-db:latest -f Dockerfile.db .
```

Start the container:

```
docker run --name=greppers-db --network=greppers-network -d greppers/holus-db:latest
```

## Spring

Build the image:

```
cd Backend/Backend
docker build --network=greppers-network -t greppers/holus-spring:latest -f Dockerfile .
```

Start the container:

```
docker run --name=greppers-api --network=greppers-network -d greppers/holus-spring:latest
```
