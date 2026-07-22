#!/bin/sh
set -e

/opt/keycloak/bin/kc.sh start-dev --import-realm --http-port=9191 &

/opt/keycloak/init.sh

wait