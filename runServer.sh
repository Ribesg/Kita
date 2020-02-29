#!/bin/bash

./gradlew :modules:server:build
java -jar modules/server/build/libs/kita.jar
