variable "linode_api_token" {
  description = "Linode API Personal Access Token. (required) Can load from ENV or config file (~/.config/linode)"
}

variable "resources_label" {
  description = "The unique label to assign to this cluster. (required)"
  type = string
}

variable "resources_tags" {
  description = "Tags to apply to your cluster for organizational purposes. (optional)"
  type = list(string)
}

variable "k8s_version" {
  description = "Kubernetes version"
  type = string
}

variable "k8s_region" {
  description = "The region where your cluster will be located. (required)"
  type = string
}

variable "k8s_ha" {
  description = "Defines whether High Availability is enabled for the Control Plane Components of the cluster."
  type = bool
}

variable "k8s_pools" {
  description = "The Node Pool specifications for the Kubernetes cluster. (required)"
  type = list(object({
    type = string
    count = number
    min = number
    max = number
  }))
}


variable "firewall_rules_inbound" {
  description = "Firewall rules for network inbound traffic"
  type = list(object({
    action   = string
    label    = string
    protocol = string
    ports    = optional(string)
    ipv4     = list(string)
    ipv6     = optional(list(string))
  }))
  default = null
}

variable "firewall_rules_outbound" {
  description = "Firewall rules for network outbound traffic"
  type = list(object({
    action   = string
    label    = string
    protocol = string
    ports    = string
    ipv4     = list(string)
    ipv6     = optional(list(string))
  }))
  default = null
}

variable "dns_base_domain" {
  description = "This is base domain name which will created by user at the time of linode account setup."
  validation {
    condition = var.dns_base_domain != null
    error_message = "The base_domain can not be null."
  }
}

variable "dns_domain_name" {
  description = "The domain name record. by default it will be branch name. For example for branch environment/acme, dns name is acme"
}

variable "dns_domain_records" {
  description = "This can be used to create, modify, and delete Linodes Domain Records."
  type = list(object({
    name   = optional(string)
    record_type   = string
    target        = string
    ttl_sec       = optional(number)
    priority      = optional(number)
    protocol      = optional(string)
    service       = optional(string)
    tag           = optional(string)
    port          = optional(number)
    weight        = optional(number)
  }))
  default = null
}

variable "dns_soa_email" {
  description = "Start of Authority email address. This is required for master Domains."
  validation {
    condition     = !can(regex("test|admin|xyz", var.dns_soa_email))
    error_message = "The soa_email should not contain 'test', 'admin', or 'xyz'. It should be a genuine email id."
  }
}