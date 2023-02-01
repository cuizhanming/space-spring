terraform {
  backend "s3" {
    endpoint                    = "eu-central-1.linodeobjects.com"
    profile                     = "linode-s3"
    skip_credentials_validation = true
    bucket                      = "infra-tf-state"
    key                         = "infra/state.json"
    region                      = "eu-central-1"
  }
}