variable "linode_api_token" {
  description = "Linode API Personal Access Token. (required) Can load from ENV or config file (~/.config/linode)"
}

variable "k8s_version" {
  default = "1.25"
  description = "Kubernetes version"
}

variable "label" {
  description = "The unique label to assign to this cluster. (required)"
  default = "default-lke-cluster"
}

variable "region" {
  description = "The region where your cluster will be located. (required)"
  default = "eu-west"
}

variable "tags" {
  description = "Tags to apply to your cluster for organizational purposes. (optional)"
  type = list(string)
  default = ["dev"]
}

variable "pools" {
  description = "The Node Pool specifications for the Kubernetes cluster. (required)"
  type = list(object({
    type = string
    count = number
    max = number
  }))
  default = [
    {
      # https://api.linode.com/v4/linode/types
      type = "g6-standard-1"
      count = 2
      max = 3
    },
    {
      type = "g6-standard-2"
      count = 1
      max = 3
    }
  ]
}