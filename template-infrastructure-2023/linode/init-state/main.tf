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

provider "linode" {
  token = var.linode_api_token
}

data "linode_object_storage_cluster" "primary" {
  id = "eu-central-1"
}

resource "linode_object_storage_bucket" "tf_state" {
  cluster = data.linode_object_storage_cluster.primary.id
  label   = "infra-tf-state"
}
