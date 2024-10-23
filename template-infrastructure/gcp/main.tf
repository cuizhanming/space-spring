terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.51.0"
    }
  }
}

provider "google" {
  project     = var.project_id
  region      = var.region
  zone        = var.zone
}

#terraform {
#  backend "gcs" {
#    bucket  = "virtual-dogfish-387208-tfstate"
#    prefix  = "terraform/state"
#  }
#}