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

## Logging into Simple

Simple Dashboards are exposed on port 3000 localy at the following url
- http://localhost:3000/

During initialisation, an admin dashboard account is automatically created:
```
username: admin@simple.org
password: XXXXXXXXXXXXXXXX
```

## Checking Simple API docs

Simple API documentation is embeded int he application and can be reached with the following URL
- http://localhost:3000/api-docs/index.html


## Checking the data with Metabase.

Metabase is the canonical tool to access Simple Database. This Simple Standalone System comes with a Metabase instance. User provisionning is can't be done as part of the docker initialisation with the Community Version of Metabase so we first have to run a script to create the default user:

```
docker exec simple-standalone-docker-metabase-1  bash /app/metabase_init.sh
```

Once this is done, we can log  in with the following credentials:
- http://localhost:12345
```
username: user@test.test
password: MySecretPassword1
```

We can then have a look at the test data:



