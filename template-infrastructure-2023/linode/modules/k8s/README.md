# Terraform Module: Linode Kubernetes Engine (LKE) Cluster

This Terraform module provisions a Linode Kubernetes Engine (LKE) cluster in a specified region and allows for various
configuration options.

## Resources Created

- `linode_lke_cluster.kube-cluster`: This resource creates a Linode Kubernetes Engine cluster with the specified
  configurations, including the label, Kubernetes version, region, tags, and high availability settings.

## Module Inputs

- `label`: The label to identify the Kubernetes cluster.

- `k8s_version`: The version of Kubernetes to be used for the cluster.

- `region`: The Linode region where the Kubernetes cluster will be created.

- `tags`: The tags to be applied to the Kubernetes cluster.

- `high_availability`: A boolean value to enable high availability for the master plan.

## Kubernetes Node Pools

The module supports creating multiple Kubernetes node pools based on the `pools` input variable.

- `pools`: A map variable defining the Kubernetes node pools, where each pool has the following attributes:

- `type`: The Linode instance type to be used for the node pool.

- `count`: The number of nodes to create in the pool.

- `min`: The minimum number of nodes for autoscaling (optional).

- `max`: The maximum number of nodes for autoscaling (optional).

## Outputs

- `k8s_ips`: The private IP addresses of the instances in the Kubernetes cluster.

- `k8s_nodes_ids`: The Linode instance ID of the instances in the Kubernetes cluster.

