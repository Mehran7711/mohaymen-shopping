FROM quay.io/keycloak/keycloak:26.7.0 AS builder

USER root

COPY keycloak/ojdbc17.jar /opt/keycloak/providers/
COPY keycloak/entrypoint.sh /opt/keycloak/entrypoint.sh
COPY keycloak/init.sh /opt/keycloak/init.sh


RUN /opt/keycloak/bin/kc.sh build --db=oracle
RUN chmod +x /opt/keycloak/entrypoint.sh \
    && chmod +x /opt/keycloak/init.sh

USER keycloak


RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT ["/opt/keycloak/entrypoint.sh"]