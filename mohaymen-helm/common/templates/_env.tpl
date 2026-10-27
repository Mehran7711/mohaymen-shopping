{{- define "mohaymen.env" -}}
{{- $global := .Values.global | default dict -}}
{{- $profile := .Values.profile | default dict -}}
{{- $redis := .Values.redis | default dict -}}
{{- $kafka := .Values.kafka | default dict -}}
{{- $database := .Values.database | default dict -}}
{{- $oauth := .Values.oauth | default dict -}}
{{- $jwt := .Values.jwt | default dict -}}
{{- $mail := .Values.mail | default dict -}}
{{- $gateway := .Values.gateway | default dict -}}
{{- $paymentClient := .Values.paymentClient | default dict -}}
{{- if not (eq $profile.enabled false) }}
- name: SPRING_PROFILES_ACTIVE
  value: {{ $global.springProfilesActive | default "dev" | quote }}
{{- end }}
{{- if $redis.enabled }}
- name: REDIS_HOST
  value: {{ $global.redisHost | default "redis" | quote }}
- name: REDIS_PORT
  value: {{ $global.redisPort | default "6379" | quote }}
{{- end }}
{{- if $kafka.enabled }}
- name: SPRING_KAFKA_BOOTSTRAP_SERVERS
  value: {{ $global.kafkaBroker | default "kafka:9092" | quote }}
{{- end }}
{{- if $database.enabled }}
- name: DB_URL
  value: {{ $database.url | default (printf "jdbc:oracle:thin:@//%s:1521/XE" ($global.databaseHost | default "store-db")) | quote }}
- name: DB_USERNAME
  value: {{ $database.username | quote }}
- name: DB_PASSWORD
  valueFrom:
    secretKeyRef:
      name: {{ include "mohaymen.secretName" . }}
      key: DB_PASSWORD
{{- end }}
{{- if $oauth.enabled }}
- name: OAUTH2_SERVER_URL
  value: {{ $global.keycloakServerUrl | default "http://keycloak:9191" | quote }}
- name: OAUTH2_BROWSER_URL
  value: {{ $global.keycloakBrowserUrl | default "http://localhost:9192" | quote }}
{{- end }}
{{- if $jwt.enabled }}
- name: SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI
  value: {{ $global.issuerUri | default "http://localhost:9192/realms/store" | quote }}
- name: SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI
  value: {{ $global.jwkSetUri | default "http://keycloak:9191/realms/store/protocol/openid-connect/certs" | quote }}
{{- end }}
{{- if $mail.enabled }}
- name: MAIL_HOST
  value: {{ $global.mailHost | default $mail.host | default "mailhog" | quote }}
- name: MAIL_PORT
  value: {{ $global.mailPort | default $mail.port | default "1025" | quote }}
{{- end }}
{{- if $gateway.enabled }}
- name: GATEWAY_URL
  value: {{ $global.gatewayUrl | default "http://api-gateway:8989" | quote }}
- name: SERVER_FORWARD_HEADERS_STRATEGY
  value: {{ $gateway.forwardHeadersStrategy | default "framework" | quote }}
{{- end }}
{{- if $paymentClient.enabled }}
- name: STORE_PAYMENT_SERVICE_URL
  value: {{ $paymentClient.url | default (printf "%s/payment" ($global.gatewayUrl | default "http://api-gateway:8989")) | quote }}
- name: KEYCLOAK_ADMIN_URL
  value: {{ $global.keycloakServerUrl | default "http://keycloak:9191" | quote }}
{{- end }}
{{- range $name, $value := .Values.env }}
- name: {{ $name }}
  value: {{ $value | quote }}
{{- end }}
{{- end -}}
