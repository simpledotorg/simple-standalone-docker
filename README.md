# Prerequisite

The following software are required to start this system:
- docker


# Starting the standalone Simple System
To start the system, simply run the following command lines:

```shell
docker compose up --no-start
docker compose start
```

This should create the following containers:
- simple-standalone-docker-postgres-1
- simple-standalone-docker-redis-1
- simple-standalone-docker-server-1
- simple-standalone-docker-webdb-1


Note: This will take several minutes to initialise the system on the first run. 
This is because this system initialises a lot of things during the first run, including database test data 
and reporting views.

It will be much faster in after the init is done. (Only a few seconds)

# Checking the system

## Checking that Simple started and is working

Simple Dashboards are exposed on port 3000 localy at the following url
- http://localhost:3000/

During initialisation, an admin dashboard account is automatically created:
```
username: admin@simple.org
password: XXXXXXXXXXXXXXXX
```

