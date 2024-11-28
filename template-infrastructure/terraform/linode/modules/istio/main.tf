resource "helm_release" "istio" {
  namespace = "service-mesh"
  create_namespace = true
  repository = "https://istio-release.storage.googleapis.com/charts"
  chart      = "base"
  name       = "istio-base"
  # https://github.com/istio/istio/releases
  version    = "1.24.1"

  values = [
    file("${path.module}/values.yaml")
  ]
}

output "istio_status" {
  value = helm_release.istio.status
}