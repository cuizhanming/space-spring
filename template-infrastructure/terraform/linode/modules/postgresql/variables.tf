variable "label" {
  description = "Deployment label for environment information like."
  type        = string
}

variable "engine_id" {
  description = "The Managed Database engine in engine/version format"
  type        = string
  default     = "postgresql/13.8"
}

variable "region" {
  description = "The region to use for the Managed Database"
  type        = string
  default     = "eu-central"
}

variable "type" {
  description = "The Linode Instance type used for the nodes of the Managed Database instance."
  type        = string
}

variable "allow_list" {
  description = "A list of IP addresses that can access the Managed Database. Each item can be a single IP address or a range in CIDR format."
  type        = list(string)
}

variable "cluster_size" {
  description = "The number of Linode Instance nodes deployed to the Managed Database. (default 1)"
  type        = number
  default     = 1
}

variable "encrypted" {
  description = "Whether the Managed Databases is encrypted. (default false)"
  type        = bool
  default     = false
}

variable "replication_type" {
  description = "The replication method used for the Managed Database.(none, async, semi_sync; default none)"
}

variable "replication_commit_type" {
  description = "The synchronization level of the replicating server. (on, local, remote_write, remote_apply, off; default off)"
}

variable "ssl_connection" {
  description = "Whether to require SSL credentials to establish a connection to the Managed Database. (default false)"
  default     = false
}