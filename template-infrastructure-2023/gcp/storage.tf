resource "google_storage_bucket" "multi-regional-bucket" {
  project                   = var.project_id
  name                      = "${var.project_id}-bucket"
  location                  = "EU"
  storage_class             = "MULTI_REGIONAL"
  public_access_prevention  = "enforced"
  versioning {
    enabled = true
  }
}