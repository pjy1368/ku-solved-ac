#!/bin/bash

rm -rf ku-boj-solved-ac/
git clone -b main https://github.com/pjy1368/ku-boj-solved-ac.git
cd ku-boj-solved-ac/
chmod +x gradlew
./gradlew clean build -x test
kill $(lsof -t -i:8080)
cd build/libs
nohup java -jar -Dspring.profiles.active=remote solvedac-0.0.1-SNAPSHOT.jar &
