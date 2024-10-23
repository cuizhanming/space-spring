resource "linode_lke_cluster" "kube-cluster" {
  label       = "cluster-${var.label}"
  tags        = var.tags
  k8s_version = var.k8s_version
  region      = var.region

  control_plane {
    high_availability = var.high_availability
  }

  dynamic "pool" {
    for_each = var.pools
    content {
      type  = pool.value["type"]
      count = pool.value["count"]
      autoscaler {
        min = pool.value["min"]
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

data "linode_instances" "k8s_nodes" {
  filter {
    name   = "id"
    values = flatten([for pool in linode_lke_cluster.kube-cluster.pool : pool.nodes[*].instance_id])
  }
}

//Export this cluster's attributes

output "k8s_ips" {
  value = data.linode_instances.k8s_nodes.instances.*.private_ip_address
}

output "k8s_nodes_ids" {
  value = data.linode_instances.k8s_nodes.instances.*.id
}

output "kubeconfig" {
  value     = linode_lke_cluster.kube-cluster.kubeconfig
  sensitive = true
}

output "api_endpoints" {
  value = linode_lke_cluster.kube-cluster.api_endpoints
}

output "status" {
  value = linode_lke_cluster.kube-cluster.status
}

output "id" {
  value = linode_lke_cluster.kube-cluster.id
}

output "pool" {
  value = linode_lke_cluster.kube-cluster.pool
}

output "dashboard" {
  value = linode_lke_cluster.kube-cluster.dashboard_url
}
