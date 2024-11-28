terraform {
  # https://releases.hashicorp.com/terraform/
  required_version = ">= 1.9.0"
  required_providers {
    linode = {
      source = "linode/linode"
      # https://registry.terraform.io/providers/linode/linode/latest
      version = "2.31.1"
    }
  }
}

provider "linode" {
  config_profile = var.linode_config_profile
  token = var.linode_api_token
}

provider "kubernetes" {
  # config_context_cluster   = "minikube"
  config_path = local_file.kubeconfig.filename
}

provider "helm" {
  kubernetes {
    config_path = local_file.kubeconfig.filename
  }
}

resource "local_file" "kubeconfig" {
  # content = base64decode("YTpiCg==")
  content  = base64decode(linode_lke_cluster.kube-cluster.kubeconfig)
  # filename = "${var.resources_label}-kubeconfig.yaml"
  # filename = pathexpand("~/.kube/${var.resources_label}-kubeconfig.yaml")
  filename = "${path.module}/${var.resources_label}-kubeconfig.yaml"
}

# module "lke" {
#   source            = "./modules/lke"
#   label             = var.resources_label
#   tags              = var.resources_tags
#   k8s_version       = var.k8s_version
#   region            = var.k8s_region
#   pools             = var.k8s_pools
#   high_availability = var.k8s_ha
# }

module "k8s" {
  source = "./modules/k8s"
}

# module "istio" {
#   source = "./modules/istio"
# }

# module "firewall" {
#   source            = "./modules/firewall"
#   label             = var.resources_label
#   tags              = var.resources_tags
#   depends_on        = [ module.k8s.k8s_nodes_ids ]
#   linode_ids        = module.k8s.k8s_nodes_ids
#   inbound_rules     = var.firewall_rules_inbound
#   outbound_rules    = var.firewall_rules_outbound
# }


# module "dns" {
#   source            = "./modules/dns"
#   tags              = var.resources_tags
#   base_domain       = var.dns_base_domain
#   domain_name       = var.dns_domain_name
#   domain_records    = var.dns_domain_records
#   soa_email         = var.dns_soa_email
# }