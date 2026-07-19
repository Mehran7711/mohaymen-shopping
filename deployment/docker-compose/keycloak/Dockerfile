FROM quay.io/keycloak/keycloak:26.7.0 AS builder

USER root

COPY keycloak/ojdbc17.jar /opt/keycloak/providers/

USER keycloak

RUN /opt/keycloak/bin/kc.sh build --db=oracle

FROM quay.io/keycloak/keycloak:26.7.0

COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]