provider "google" {
  credentials = file(var.gcp_credential)
  project     = var.project_id
  region      = var.region
  zone        = var.zone
}