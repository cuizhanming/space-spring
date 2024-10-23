#!/bin/bash

EDNPOINT="$1"
TOKEN="$2"

while IFS=' ' read -r ACCOUNT_ID OLD_SKU NEW_SKU X
do
  echo curl --location --request PATCH "$ENDPOINT" \
    --header "Authorization: Basic $TOKEN" \
    --header 'Content-Type: application/json' \
    --header "X-Asavie-Account-Id: ${ACCOUNT_ID}" \
    --data '{"'${NEW_SKU}'": "'${NEW_SKU}'"}';
  echo $(curl --location --request PATCH "$ENDPOINT" \
    --header "Authorization: Basic $TOKEN" \
    --header 'Content-Type: application/json' \
    --header "X-Asavie-Account-Id: ${ACCOUNT_ID}" \
    --data '{"'${NEW_SKU}'": "'${NEW_SKU}'"}');
done < "${3}"