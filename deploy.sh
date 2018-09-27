#!/bin/bash

TAG=`git rev-parse --short HEAD`

docker run --rm \
  --volume $TRAVIS_BUILD_DIR:/tmp/ \
  --env G_USER=deployer@ipni-1867.iam.gserviceaccount.com \
  --env G_PROJECT=ipni-1867 \
  --env G_CLUSTER=ipni-beta \
  --env G_REGION=europe-west1-d \
  --env G_ZONE=europe-west1-d \
  --env G_SERVICE_ACCOUNT=/tmp/secrets/ipni-deployer-sa.json \
  rbgkew/gke-helm-deployer:0.1.4 deploy /tmp/helm $TRAVIS_BRANCH $TAG -f /tmp/secrets/tls.yaml --set-string image.tag=$TAG
