{{/*
Shared settings. A parent chart can set .Values.global; a service chart
installed on its own falls back to the defaults in each define.
*/}}
{{- define "mohaymen.namespace" -}}
{{- $global := .Values.global | default dict -}}
{{- $global.namespace | default .Release.Namespace -}}
{{- end -}}

{{- define "mohaymen.commonLabels" -}}
{{- $global := .Values.global | default dict -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/part-of: {{ $global.partOf | default "mohaymen" }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end -}}

{{/*
Usage: include "mohaymen.labels" (dict "ctx" $ "name" .Values.appLabel)
*/}}
{{- define "mohaymen.labels" -}}
app: {{ .name }}
app.kubernetes.io/name: {{ .name }}
{{ include "mohaymen.commonLabels" .ctx }}
{{- end -}}

{{- define "mohaymen.podLabels" -}}
{{- $global := .ctx.Values.global | default dict -}}
app: {{ .name }}
app.kubernetes.io/name: {{ .name }}
app.kubernetes.io/instance: {{ .ctx.Release.Name }}
app.kubernetes.io/part-of: {{ $global.partOf | default "mohaymen" }}
{{- end -}}

{{- define "mohaymen.secretName" -}}
{{ .Values.appLabel }}-secret
{{- end -}}
