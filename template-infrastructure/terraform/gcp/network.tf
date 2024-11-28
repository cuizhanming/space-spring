#################################
# Create CUSTOM mode VPC network
#################################
resource "google_compute_network" "privatenet" {
  name = "privatenet"
  project = var.project_id
  auto_create_subnetworks = "false"

}

resource "google_compute_subnetwork" "privatenet-us" {
  name          = "privatenet-us"
  ip_cidr_range = "10.130.0.0/20"
  region        = var.region
  network       = google_compute_network.privatenet.id
  private_ip_google_access = true
}

resource "google_compute_firewall" "privatenet-allow-ssh" {
  name = "privatenet-allow-ssh"
  project = var.project_id
  network = google_compute_network.privatenet.self_link
  allow {
    protocol = "tcp"
    ports    = ["22"]
  }
  source_ranges = ["35.235.240.0/20"]
}

#################################
# Create VM instance in VPC network
#################################
module "vm-internal" {
  source           = "instance"
  instance_name    = "vm-internal"
  instance_type    = "n1-standard-1"
  instance_image   = "debian-cloud/debian-11"
  instance_zone    = var.zone
  instance_network = google_compute_network.privatenet.self_link
  instance_subnetwork = google_compute_subnetwork.privatenet-us.self_link
}