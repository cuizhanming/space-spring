variable "base_domain" {
  description = "This is base domain name which will created by user at the time of linode account setup."
  type        = string
  validation {
    condition     = var.base_domain != null
    error_message = "The base_domain can not be null."
  }
}

variable "domain_name" {
  description = "The domain name record. by default it will be branch name. For example for branch environment/acme, dns name is acme"
  type        = string
}

variable "soa_email" {
  description = "Start of Authority email address. This is required for master Domains."
  type        = string
  validation {
    condition     = !can(regex("test|admin|xyz", var.soa_email))
    error_message = "The soa_email should not contain 'test', 'admin', or 'xyz'. It should be a genuine email id."
  }
}

variable "tags" {
  type    = list(string)
  default = [
    "dev"
  ]
}

variable "domain_type" {
  description = "(Required) If this Domain represents the authoritative source of information for the domain it describes, or if it is a read-only copy of a master (also called a slave)"
  type        = string
  default     = "master"
}

variable "domain_master_ips" {
  description = "(Required for type=slave) The IP addresses representing the master DNS for this Domain."
  type        = list(string)
  default     = null
}

variable "domain_records" {
  description = "This can be used to create, modify, and delete Linodes Domain Records."
  type        = list(object({
    name        = string
    record_type = string
    target      = string
    ttl_sec     = optional(number)
    priority    = optional(number)
    protocol    = optional(string)
    service     = optional(string)
    tag         = optional(string)
    port        = optional(number)
    weight      = optional(number)
  }))
  default = null
}