variable "k8s_version" {
  description = "Kubernetes version"
  type        = string
  default     = null
}

variable "region" {
  description = "The region where your cluster will be located. (required)"
  type        = string
  default     = null
}

variable "label" {
  description = "The unique label to assign to this cluster. (required)"
  type        = string
  default     = null
}

variable "tags" {
  description = "Tags to apply to your cluster for organizational purposes. (optional)"
  type        = list(string)
  default     = null
}

variable "high_availability" {
  description = "Defines whether High Availability is enabled for the Control Plane Components of the cluster."
  type        = bool
  default     = null
}

variable "pools" {
  description = "The Node Pool specifications for the Kubernetes cluster. (required)"
  type        = list(object({
    type  = string
    count = number
    min   = number
    max   = number
  }))
  default = null
}


