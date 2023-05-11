variable "gcp_credential" {
  description = "GCP Credential File. (required) Can load from env variable `TF_VAR_gcp_credential` or config file (~/.config/gcp)"
  default     = "~/.gcp/terraform-gcp.json"
}

variable "project_id" {
  description = "GCP Project ID. (required) Can load from env variable `TF_VAR_project_id` or config file (~/.config/gcp)"
}

variable "region" {
  description = "GCP Region. (required) Can load from env variable `TF_VAR_region` or config file (~/.config/gcp)"
  default     = "europe-west4"
}

variable "zone" {
  description = "GCP Zone. (required) Can load from env variable `TF_VAR_zone` or config file (~/.config/gcp)"
  default     = "europe-west4-c"
}