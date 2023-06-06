
resource "google_compute_instance" "vm_instance" {
  name         = var.instance_name
  zone         = var.instance_zone
  machine_type = var.instance_type
  boot_disk {
    initialize_params {
      image = var.instance_image
    }
  }
  network_interface {
    network = var.instance_network
    subnetwork = var.instance_subnetwork
    access_config {
      # Allocate a one-to-one NAT IP to the instance
    }
  }
}