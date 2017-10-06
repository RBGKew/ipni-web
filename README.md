# IPNI Web

# Building

The project as a whole can be built using:

```mvn install```

This will build the java code, frontend via webpack, and docker images.

To skip the webpack build, run:

```mvn install -Dskip.webpack```

# Development

When developing the frontend, it is convenient to use the webpack development server for
automatic rebuilds etc. You can run this by going into the `web/src/main/resources/`
folder and running

```npm start```

this will start a dev server running on port `8080`

## Loading data

The loader task can be run as

```docker-compose run -e BUILD_ONSTARTUP=true indexer```

Other indexer options that can be specified via ```-e``` are:

* ```IPNI_FLAT``` - url for names export file from ipni flat
* ```IPNI_AUTHORS``` - url for authors export file
* ```IPNI_PUBLICATIONS``` - url for publications export file
* ```POWO_IDS``` - url for powo ids mapping file

# Deployment

Deployments are done to Kubernetes on the Google cloud platform. There are two stages to
a deployment.

## 1: Build docker images and push to container registry

This is done via ```mvn deploy``` _if_ you have both of the following

* The ```DOCKER_REGISTRY``` environment variable set to the container registry url.
  e.g., ```DOCKER_REGISTRY=eu.gcr.io/powop-1349/```
* Authentication credentials for docker. Should be set up during ```gcloud```
  [setup](https://cloud.google.com/compute/docs/gcloud-compute)

## 2: Update deployment via Helm

Images are tagged with the short git hash of HEAD when ```mvn package``` is run. To
upgrade to a given version, change the ```tag``` key for the relevant container in
```helm/values.yaml``` (get from ```git rev-parse --short HEAD```) and run

```helm update [release name] ./helm```

from the project root.

To see what releases are available, do ```helm list```
