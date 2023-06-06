#################################
# Create AUTO mode VPC network - mynetwork
#################################
resource "google_compute_network" "mynetwork" {
  name = "mynetwork"
  project = var.project_id
  auto_create_subnetworks = "true"
}

resource "google_compute_firewall" "mynetwork-allow-http-ssh-rdp-icmp" {
  name = "mynetwork-allow-http-ssh-rdp-icmp"
  project = var.project_id
  network = google_compute_network.mynetwork.self_link
  allow {
    protocol = "tcp"
    ports    = ["22", "80", "3389"]
  }
  allow {
    protocol = "icmp"
  }
  source_ranges = ["0.0.0.0/0"]
}

module "mynetwork-us-vm" {
  source           = "./instance"
  instance_type    = "e2-micro"
  instance_image   = "debian-cloud/debian-11"
  instance_name    = "mynetwork-us-vm"
  instance_zone    = var.zone
  instance_network = google_compute_network.mynetwork.self_link
  instance_subnetwork = "mynetwork"
}
module "mynetwork-eu-vm" {
  source           = "./instance"
  instance_type    = "e2-micro"
  instance_image   = "debian-cloud/debian-11"
  instance_name    = "mynetwork-eu-vm"
  instance_zone    = "europe-west1-c"
  instance_network = google_compute_network.mynetwork.self_link
  instance_subnetwork = "mynetwork"
}