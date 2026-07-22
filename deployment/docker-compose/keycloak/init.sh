#!/bin/sh

echo "Waiting for Keycloak..."

until /opt/keycloak/bin/kcadm.sh config credentials \
    --server http://localhost:9191 \
    --realm master \
    --user admin \
    --password admin123 >/dev/null 2>&1
do
    sleep 2
done

echo "Keycloak is ready."

/opt/keycloak/bin/kcadm.sh update realms/master \
    -s sslRequired=NONE

/opt/keycloak/bin/kcadm.sh update realms/bookstore \
    -s sslRequired=NONE

echo "SSL updated."

wait