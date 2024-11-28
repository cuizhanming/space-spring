terraform {
  cloud {
    organization = "cuizhanming-com"
    workspaces {
      tags = ["terraform-state-linode-dev"]
    }
  }
}