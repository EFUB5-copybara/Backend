#!/bin/bash

# 로그 유지용 heartbeat
(while true; do echo "building..."; sleep 10; done) &
HEARTBEAT_PID=$!

cd ~/app

# .env 파일에서 환경변수 불러오기
export $(cat .env | xargs)

# application-prod.yml 자동 생성
echo "application-prod.yml 생성 중"
mkdir -p src/main/resources
cat > src/main/resources/application-prod.yml <<EOL
spring:
  datasource:
    url: "${DB_URL}"
    username: "${DB_USERNAME}"
    password: "${DB_PASSWORD}"
    driver-class-name: com.mysql.cj.jdbc.Driver
    
  redis:
    host: "${REDIS_HOST}"
    port: "${REDIS_PORT}"
    
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
EOL

echo "Docker Compose 빌드 및 실행"
docker-compose down
docker-compose up -d --build

kill $HEARTBEAT_PID

# 기존 실행 중인 프로세스 종료
pkill -f 'java -jar' || true

# 애플리케이션 실행 (환경변수 주입된 상태로)
nohup java -jar build/libs/crumble-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > app.log 2>&1 &
