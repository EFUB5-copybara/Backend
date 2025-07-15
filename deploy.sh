#!/bin/bash

cd ~/app

# .env 파일에서 환경변수 불러오기
export $(cat .env | xargs)

# build (테스트 생략)
./gradlew build -x test

# 기존 실행 중인 프로세스 종료
pkill -f 'java -jar' || true

# 애플리케이션 실행 (환경변수 주입된 상태로)
nohup java -jar build/libs/*.jar --spring.profiles.active=prod > app.log 2>&1 &
