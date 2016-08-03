#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Build Desktop project
doGradleCmd "./gradlew desktop:build"
