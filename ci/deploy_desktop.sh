#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Deploy Desktop project
doGradleCmd "./gradlew desktop:dist"
mv desktop/build/libs/desktop-1.0.jar desktop/build/libs/terralegion-$TRAVIS_TAG.jar
