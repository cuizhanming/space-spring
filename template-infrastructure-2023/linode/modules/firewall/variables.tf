variable "label" {
  description = "resource unique label"
  type        = string
  default     = null
}

variable "tags" {
  description = "resource tags"
  type        = list(string)
  default     = null
}

variable "linode_ids" {
  description = "A list of IDs of linode Nodes"
  type        = list(string)
  default     = null
}

variable "nodebalancer_ids" {
  description = "A list of IDs of linode NodeBalancers"
  type        = list(string)
  default     = null
}

variable "inbound_rules" {
  description = "Firewall rules for network inbound traffic"
  type        = list(object({
    action   = string
    label    = string
    protocol = string
    ports    = string
    ipv4     = list(string)
    ipv6     = list(string)
  }))
  default = null
}

variable "outbound_rules" {
  description = "Firewall rules for network outbound traffic"
  type        = list(object({
    action   = string
    label    = string
    protocol = string
    ports    = string
    ipv4     = list(string)
    ipv6     = list(string)
  }))
  default = null
}

