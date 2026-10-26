{{/*
Labels shared by every mohaymen resource. Pass the root context (.).
Never use these in a selector - selectors are immutable and helm.sh/chart
changes on every chart version bump.
*/}}
{{- define "mohaymen.commonLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/part-of: {{ .Values.global.partOf | default "mohaymen" }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end -}}

{{/*
Labels for a component's Deployment/Service metadata.
Usage: {{ include "mohaymen.labels" (dict "ctx" $ "name" "redis") }}
*/}}
{{- define "mohaymen.labels" -}}
app: {{ .name }}
app.kubernetes.io/name: {{ .name }}
{{ include "mohaymen.commonLabels" .ctx }}
{{- end -}}

{{/*
Labels for a component's pod template. Leaves out helm.sh/chart and
managed-by so a chart version bump alone doesn't roll every pod.
Usage: {{ include "mohaymen.podLabels" (dict "ctx" $ "name" "redis") }}
*/}}
{{- define "mohaymen.podLabels" -}}
app: {{ .name }}
app.kubernetes.io/name: {{ .name }}
app.kubernetes.io/instance: {{ .ctx.Release.Name }}
app.kubernetes.io/part-of: {{ .ctx.Values.global.partOf | default "mohaymen" }}
{{- end -}}