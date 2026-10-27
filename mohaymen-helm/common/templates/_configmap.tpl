{{/*
Shared non-secret settings. Render this once from the platform chart.
Service charts read the same .Values.global fields directly.
*/}}
{{- define "mohaymen.configmap" -}}
{{- $global := .Values.global | default dict -}}
apiVersion: v1
kind: ConfigMap
metadata:
  name: {{ $global.configMapName | default "mohaymen-config" }}
  namespace: {{ include "mohaymen.namespace" . }}
  labels:
    {{- include "mohaymen.commonLabels" . | nindent 4 }}
data:
  SPRING_PROFILES_ACTIVE: {{ $global.springProfilesActive | default "dev" | quote }}
  REDIS_HOST: {{ $global.redisHost | default "redis" | quote }}
  REDIS_PORT: {{ $global.redisPort | default "6379" | quote }}
  SPRING_KAFKA_BOOTSTRAP_SERVERS: {{ $global.kafkaBroker | default "kafka:9092" | quote }}
  DATABASE_HOST: {{ $global.databaseHost | default "store-db" | quote }}
  OAUTH2_SERVER_URL: {{ $global.keycloakServerUrl | default "http://keycloak:9191" | quote }}
  OAUTH2_BROWSER_URL: {{ $global.keycloakBrowserUrl | default "http://localhost:9192" | quote }}
  SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI: {{ $global.issuerUri | default "http://localhost:9192/realms/store" | quote }}
  SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI: {{ $global.jwkSetUri | default "http://keycloak:9191/realms/store/protocol/openid-connect/certs" | quote }}
  MAIL_HOST: {{ $global.mailHost | default "mailhog" | quote }}
  MAIL_PORT: {{ $global.mailPort | default "1025" | quote }}
  GATEWAY_URL: {{ $global.gatewayUrl | default "http://api-gateway:8989" | quote }}
{{- end -}}
