#
# Getting jq
#
apk add jq

#
# Getting the default token
#
export SETUP_TOKEN=$(curl -s -m 5 -X GET \
    -H "Content-Type: application/json" \
    http://localhost:3000/api/session/properties \
    | jq -r '.["setup-token"]'
)

#
# CREATING THE DEFAULT USER
#
curl -s -X POST \
    -H "Content-type: application/json" \
    http://localhost:3000/api/setup \
    -d '{
    "token": "'${SETUP_TOKEN}'",
    "user": {
        "email": "user@test.test",
        "first_name": "User",
        "last_name": "Name",
        "password": "MySecretPassword1"
    },
    "prefs": {
        "allow_tracking": true,
        "site_name": "My Metabase Instance"
    }
}'

#
# Logging in and create a DB connection
#
curl -v --cookie cookie.txt --cookie-jar cookie.txt -X POST -H "Content-Type: application/json" -d '{"username": "user@test.test", "password": "MySecretPassword1"}' http://localhost:3000/api/session 
curl -v --cookie cookie.txt --cookie-jar cookie.txt -X POST \
    -H "Content-type: application/json" \
    http://localhost:3000/api/database \
    -d '{"is_on_demand":false,"is_full_sync":true,"is_sample":false,"cache_ttl":null,"refingerprint":false,"auto_run_queries":true,"schedules":{},"details":{"host":"postgres","port":5432,"dbname":"simple-server_test","user":"postgres","password":"password","schema-filters-type":"all","ssl":false,"tunnel-enabled":false,"advanced-options":false},"name":"Simple Standalone Database","engine":"postgres"}
'




