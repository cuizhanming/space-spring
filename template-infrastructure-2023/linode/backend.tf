terraform {
  backend "s3" {
    skip_credentials_validation = true
    skip_region_validation      = true
    endpoint                    = "eu-central-1.linodeobjects.com"
    profile                     = "linode-zcui"
    bucket                      = "infra-tf-state-20231023"
    key                         = "infra/state.json"
    region                      = "eu-central-1"
  }
}