terraform {
  required_version = ">= 1.3.7"
  required_providers {
    linode = {
      source = "linode/linode"
      # https://registry.terraform.io/providers/linode/linode/latest
      version = "1.29.4"
    }
  }
}

//Use the Linode Provider
provider "linode" {
  token = var.linode_api_token
}

//Use the linode_lke_cluster resource to create
//a Kubernetes cluster
resource "linode_lke_cluster" "linode-k8s-cluster" {
  k8s_version = var.k8s_version
  label       = var.label
  region      = var.region
  tags        = var.tags

  dynamic "pool" {
    for_each = var.pools
    content {
      type  = pool.value["type"]
      count = pool.value["count"]

      autoscaler {
        min = pool.value["count"]
        max = pool.value["max"]
      }
    }
  }

  # Prevent the count field from overriding autoscaler-created nodes
  lifecycle {
    ignore_changes = [
      pool.0.count
    ]
  }
}

//Export this cluster's attributes
output "kubeconfig" {
  value = linode_lke_cluster.linode-k8s-cluster.kubeconfig
  sensitive = true
}

output "api_endpoints" {
  value = linode_lke_cluster.linode-k8s-cluster.api_endpoints
}

output "status" {
  value = linode_lke_cluster.linode-k8s-cluster.status
}

output "id" {
  value = linode_lke_cluster.linode-k8s-cluster.id
}

output "pool" {
  value = linode_lke_cluster.linode-k8s-cluster.pool
}

output "dashboard" {
  value = linode_lke_cluster.linode-k8s-cluster.dashboard_url
}


