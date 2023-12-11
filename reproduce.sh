#!/bin/bash

IMAGE_NAME="my-native-image"
IMAGE_TAG="latest"

docker build -f Dockerfile."${1}" -t $IMAGE_NAME:$IMAGE_TAG .

./gradlew \
 -Dorg.gradle.jvmargs=-Xmx1g \
 -x test quarkusIntTest \
 -Dquarkus.package.type=native \
 -Dquarkus.native.container-build=true \
 -Dquarkus.native.builder-image=$IMAGE_NAME:$IMAGE_TAG \
 -Dquarkus.container-image.build=true \
 -Dquarkus.container-image.push=false \
 -Dquarkus.container-image.registry=local \
 -Dquarkus.container-image.tag=undefined \
 -Dquarkus.native.builder-image.pull=missing \
 -Pversion=undefined