#!/usr/bin/env bash

PROJECT_ID="$1"
REGION="$2"
ZONE="$3"
BUCKET="$4"

if [[ -z ${PROJECT_ID} ]]; then
  echo "Usage: $0 PROJECT_ID REGION ZONE BUCKET"
  exit 1
fi
export TF_VAR_project_id="${PROJECT_ID}"
echo "Setting terraform var.project_id to ${TF_VAR_project_id}"

gcloud auth application-default login
gcloud config set project "${PROJECT_ID}"

if [[ ${REGION} ]]; then
  gcloud config set compute/region "${REGION}"
  export TF_VAR_region="${REGION}"
  echo "Setting terraform var.region to ${TF_VAR_region}"
fi

if [[ ${ZONE} ]]; then
  gcloud config set compute/zone "${ZONE}"
  export TF_VAR_zone="${ZONE}"
  echo "Setting terraform var.zone to ${TF_VAR_zone}"
fi

# Enable APIs
gcloud services enable compute.googleapis.com
gcloud services enable iam.googleapis.com

# Create a storage bucket for Terraform state
if [[ ${BUCKET} ]]; then
  echo "Creating bucket for Terraform remote state"
  gcloud storage buckets create gs://${PROJECT_ID}-${BUCKET} --location=${REGION} --project=${PROJECT_ID}
fi

#echo "Creating service account for Terraform"
#SA_NAME="terraform-service-account"
#gcloud iam service-accounts create $SA_NAME --description="Used for Terraform provisioning" --display-name="Terraform"

#echo "Granting permissions to service account"
#gcloud projects add-iam-policy-binding $PROJECT_ID \
#    --member="serviceAccount:$SA_NAME@$PROJECT_ID.iam.gserviceaccount.com" \
#    --role="roles/editor"

#echo "Creating service account key file"
#mkdir -p ~/.gcp
#KEY_FILE=~/.gcp/terraform.json
#if [[ ${KEY_ID} ]]; then
#  gcloud iam service-accounts keys list --iam-account="$PROJECT_ID"@"$PROJECT_ID".iam.gserviceaccount.com
#  gcloud beta iam service-accounts keys get-public-key $KEY_ID --output-file=$KEY_FILE --iam-account="$PROJECT_ID"@"$PROJECT_ID".iam.gserviceaccount.com
#else
#  gcloud iam service-accounts keys create $KEY_FILE --iam-account="$PROJECT_ID"@"$PROJECT_ID".iam.gserviceaccount.com
#fi