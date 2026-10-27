{{- define "mohaymen.service" -}}
apiVersion: v1
kind: Service
metadata:
  name: {{ .Values.serviceName | default .Values.appLabel }}
  namespace: {{ include "mohaymen.namespace" . }}
  labels:
    {{- include "mohaymen.labels" (dict "ctx" . "name" .Values.appLabel) | nindent 4 }}
spec:
  selector:
    app: {{ .Values.appLabel }}
  type: {{ .Values.service.type | default "ClusterIP" }}
  ports:
    - name: http
      protocol: TCP
      port: {{ .Values.service.port | default .Values.containerPort }}
      targetPort: http
      {{- if and (eq (.Values.service.type | default "ClusterIP") "NodePort") .Values.service.nodePort }}
      nodePort: {{ .Values.service.nodePort }}
      {{- end }}
{{- end -}}
