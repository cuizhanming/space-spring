
resource "kubernetes_namespace" "service-mesh" {
  metadata {
    name = "service-mesh"
  }
}