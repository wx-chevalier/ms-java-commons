#!/usr/bin/env bash

cd `dirname $0`

DOCKER_REGISTRY_SERVER=${DOCKER_REGISTRY_SERVER:=registry.wx.com}

IMAGE=${DOCKER_REGISTRY_SERVER}/wx/udla-api

./gradlew clean test :udla-api:build

echo "[*] Finished building"

docker build --tag $IMAGE:latest -f buildscripts/Dockerfile-local .

echo "[*] Pushing $IMAGE:latest"

docker push $IMAGE:latest
