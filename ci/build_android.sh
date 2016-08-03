#!/usr/bin/env bash

# Include bash-tools
source $(dirname $0)/shtools.sh

# Build Android project
doGradleCmd "./gradlew android:build"
