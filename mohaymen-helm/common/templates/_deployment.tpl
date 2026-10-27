{{- define "mohaymen.deployment" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Values.deploymentName | default .Values.appLabel }}
  namespace: {{ include "mohaymen.namespace" . }}
  labels:
    {{- include "mohaymen.labels" (dict "ctx" . "name" .Values.appLabel) | nindent 4 }}
spec:
  replicas: {{ .Values.replicaCount | default 1 }}
  selector:
    matchLabels:
      app: {{ .Values.appLabel }}
  template:
    metadata:
      labels:
        {{- include "mohaymen.podLabels" (dict "ctx" . "name" .Values.appLabel) | nindent 8 }}
    spec:
      containers:
        - name: {{ .Values.appLabel }}
          image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
          imagePullPolicy: {{ .Values.image.pullPolicy | default "IfNotPresent" }}
          {{- if .Values.containerPort }}
          ports:
            - name: http
              containerPort: {{ .Values.containerPort }}
              {{- if .Values.hostPort }}
              hostPort: {{ .Values.hostPort }}
              {{- end }}
          {{- end }}
          env:
            {{- include "mohaymen.env" . | nindent 12 }}
          {{- with .Values.resources }}
          resources:
            {{- toYaml . | nindent 12 }}
          {{- end }}
          {{- $probes := .Values.probes | default dict -}}
          {{- if and .Values.containerPort ($probes.enabled | default true) }}
          startupProbe:
            httpGet:
              path: {{ $probes.path | default "/actuator/health" }}
              port: http
            failureThreshold: {{ $probes.startupFailureThreshold | default 30 }}
            periodSeconds: {{ $probes.startupPeriodSeconds | default 5 }}
          readinessProbe:
            httpGet:
              path: {{ $probes.path | default "/actuator/health" }}
              port: http
            periodSeconds: {{ $probes.readinessPeriodSeconds | default 10 }}
          livenessProbe:
            httpGet:
              path: {{ $probes.path | default "/actuator/health" }}
              port: http
            periodSeconds: {{ $probes.livenessPeriodSeconds | default 15 }}
          {{- end }}
{{- end -}}
