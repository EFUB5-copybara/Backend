#!/bin/bash

# 로그 유지용 heartbeat
(while true; do echo "create application-prod.yml..."; sleep 10; done) &
HEARTBEAT_PID=$!

cd ~/app

# .env 파일에서 환경변수 불러오기
export $(cat .env | xargs)

# application-prod.yml 자동 생성
echo "application-prod.yml 생성 중"
mkdir -p src/main/resources
cat > src/main/resources/application-prod.yml <<EOL
spring:
  application:
    name: crumble
    
  datasource:
    url: "${DB_URL}"
    username: "${DB_USERNAME}"
    password: "${DB_PASSWORD}"
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      connection-timeout: 10000
      validation-timeout: 3000
      initialization-fail-timeout: 15000
      maximum-pool-size: 10
    
  redis:
    host: "${REDIS_HOST}"
    port: "${REDIS_PORT}"
    database: ${REDIS_DATABASE}
    
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

jwt:
  secret: ${JWT_KEY}
  access-token-expiration-millis: 3600000
  refresh-token-expiration-millis: 604800000

cors:
  allow-origins:
    - ${CORS_ALLOWED_ORIGIN_1}
    - ${CORS_ALLOWED_ORIGIN_2}

openai:
  api-key: ${API_KEY}

logging:
  level:
    root: INFO
    org.springframework: DEBUG
    efub.cpbr.crumble: DEBUG
EOL

echo "설정 파일 생성 완료"

# 최신 이미지 pull
docker pull hanrann6/yourapp:latest

# 기존 컨테이너 중지 및 삭제
docker-compose down

# 새 컨테이너 실행
docker-compose up -d

echo "배포 완료"

kill $HEARTBEAT_PID

