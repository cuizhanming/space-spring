label = "prod-eu"
region = "eu-west"
tags = ["prod"]
k8s_version = "1.25"
pools = [
  {
    # https://api.linode.com/v4/linode/types
    type : "g6-standard-2"
    count : 3
    max : 5
  }
]