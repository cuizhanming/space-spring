# Create the my-network network
resource "google_compute_network" "my_network" {
  name = "my-network"
  project = var.project_id
  # RESOURCE properties go here
  auto_create_subnetworks = "true"
}

# Add a firewall rule to allow HTTP, SSH, RDP and ICMP traffic on mynetwork
resource "google_compute_firewall" "mynetwork-allow-http-ssh-rdp-icmp" {
  name = "my-network-allow-http-ssh-rdp-icmp"
  project = var.project_id
  # RESOURCE properties go here
  network = google_compute_network.my_network.self_link
  allow {
    protocol = "tcp"
    ports    = ["22", "80", "3389"]
  }
  allow {
    protocol = "icmp"
  }
  source_ranges = ["0.0.0.0/0"]
}

# Create the my-net-us-vm instance
module "my-net-us-vm" {
  source           = "./instance"
  instance_name    = "mynet-us-vm"
  instance_zone    = "us-east5-a"
  instance_network = google_compute_network.my_network.self_link
}
# Create the my-net-eu-vm" instance
module "my-net-eu-vm" {
  source           = "./instance"
  instance_name    = "my-net-eu-vm"
  instance_zone    = "europe-west1-d"
  instance_network = google_compute_network.my_network.self_link
}