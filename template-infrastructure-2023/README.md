# Terraform Provider Authentication

### GCP Service Account Key

```shell
PROJECT_ID=soy-envelope-386316
REGION=us-central1
ZONE=us-central1-a
gcloud config set project $PROJECT_ID
gcloud config set compute/region $REGION
gcloud config set compute/zone $ZONE

# Login to GCP
gcloud auth login

# List login, set default account
gcloud auth list
gcloud config set account `LOGIN_ACCOUNT`

# Create service account
SA_NAME=terraform-$PROJECT_ID
gcloud iam service-accounts create $SA_NAME --description="Used for Terraform provisioning" --display-name="Terraform"

# Grant service account role/editor
gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member="serviceAccount:$SA_NAME@$PROJECT_ID.iam.gserviceaccount.com" \
    --role="roles/editor"
    
# Create service account key
mkdir -p ~/.gcp
KEY_FILE=~/.gcp/terraform-gcp.json
gcloud iam service-accounts keys create $KEY_FILE --iam-account=$SA_NAME@$PROJECT_ID.iam.gserviceaccount.com"

```

### Linode Personal access token

```shell
export TF_VAR_linode_api_token=04565497fcdb6a0a5349a7d530c65d6a49ec3c3cf19bc79f0cdb8a291dc4a802
```

# Terraform Plan

### GCP

- [Terraform Provider - GCP](https://registry.terraform.io/providers/hashicorp/google/latest/docs/guides/getting_started)
- [GCP Example](https://developer.hashicorp.com/terraform/tutorials/gcp-get-started/google-cloud-platform-build)

### Linode

- [Terraform Provider - Linode](https://registry.terraform.io/providers/linode/linode/latest/docs)
- [Using LKS storage as Terraform backend](https://dev.to/itmecho/setting-up-linode-object-storage-as-a-terraform-backend-1ocb)

```shell
terraform plan -var-file="terraform.tfvars" --parallelism=1
terraform apply -var-file="terraform.tfvars" --parallelism=1
```

# Terraform Backend

### TF Backend .tfstate store
Since Linode storage is seamless api as AWS S3, we are configuring it as `S3` resource in `backend.tf` file. 
The Linode object storage for this state, can be created manually in advance. 
Or through TF too which is setup in `init-state` folder, to be separated from the main project folder.

# Terraform State

### LKS output

```shell
export KUBE_VAR=`terraform output kubeconfig` && echo $KUBE_VAR | base64 -di > lke-cluster-config.yaml
```