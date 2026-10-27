{{- define "mohaymen.secret" -}}
{{- $database := .Values.database | default dict -}}
{{- if or $database.enabled .Values.secretEnv }}
apiVersion: v1
kind: Secret
metadata:
  name: {{ include "mohaymen.secretName" . }}
  namespace: {{ include "mohaymen.namespace" . }}
  labels:
    {{- include "mohaymen.labels" (dict "ctx" . "name" .Values.appLabel) | nindent 4 }}
type: Opaque
stringData:
  {{- if $database.enabled }}
  DB_PASSWORD: {{ $database.password | default "123" | quote }}
  {{- end }}
  {{- range $name, $value := .Values.secretEnv }}
  {{ $name }}: {{ $value | quote }}
  {{- end }}
{{- end }}
{{- end -}}
