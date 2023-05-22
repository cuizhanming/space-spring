### GCP Storage Bucket to store Terraform state ###
#resource "google_storage_bucket" "terraform-state" {
#  project                   = var.project_id
#  name                      = "${var.project_id}-tfstate"
#  location                  = "EU"
#  public_access_prevention  = "enforced"
#  versioning {
#    enabled = true
#  }
#}