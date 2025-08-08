
<div align="center">
  <h1>🍪Qrumble - Backend</h1>
  <p>EFUB 5기 SWS 2팀 Copybara의 "Qrumble" 서비스 백엔드 레포지토리입니다.</p>
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/bf043f5c-a624-43e4-b01c-f0df541044dc" />

</div>

## 🗓️ 개발 기간
- 2025.07.07 ~ 2025-08.08

## 💡 주요 기능

## 🔧 서버 아키텍처



## 🔨 기술 스택
**Develop**

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/MYSQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/REDIS-FF4438?style=for-the-badge&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"><img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"> <img src="https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white">

**Deploy**

<img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> ![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white) <img src="https://img.shields.io/badge/github%20actions-181717?style=for-the-badge&logo=github%20actions&logoColor=white">

## 📑 ERD
<img width="3598" height="2160" alt="image" src="https://github.com/user-attachments/assets/6aebe888-a115-462a-b1eb-7804a9104df2" />


## 👩‍💻 팀원
<table>
  <tr>
    <td align="center"><img src="https://github.com/kimeunsom.png" width="100" /></td>
    <td align="center"><img src="https://github.com/oooooming.png" width="100" /></td>
    <td align="center"><img src="https://github.com/Hanrann6.png" width="100" /></td>
    <td align="center"><img src="https://github.com/sohyu-na.png" width="100" /></td>
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/kimeunsom"><strong>@kimeunsom</strong></a></td>
    <td align="center"><a href="https://github.com/oooooming"><strong>@oooooming</strong></a></td>
    <td align="center"><a href="https://github.com/Hanrann6"><strong>@Hanrann6</strong></a></td>
    <td align="center"><a href="https://github.com/sohyu-na"><strong>@sohyu-na</strong></a></td>
  </tr>
  <tr>
    <td align="center">김은솜</td>
    <td align="center">오민지</td>
    <td align="center">육란</td>
    <td align="center">소현아</td>
  </tr>
  <tr>
    <td align="center"></td>
    <td align="center"></td>
    <td align="center"></td>
    <td align="center"></td>
  </tr>
</table>



## 📁 프로젝트 구조
```
📂
├── .github/
│   ├── ISSUE_TEMPLATE
|   │   ├── bug_report.md
|   │   ├── chore-issue-template.md
|   │   └── feature_request.md
│   ├── workflows / deploy.yml
│   └── PULL_REQUEST_TEMPLATE.md
│
├── deploy.sh
├── docker-compose.yml
│
├── src/main/
│   ├── java/efub/cpbr/crumble/
│   │   ├── MavveApplication.java
│   │
│   │   ├── auth/                     # 🔐 인증 도메인
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── dto/
│   │   │   └── entity/
│   │   │
│   │   │   └── global/
│   │   │       ├── config/
│   │   │       ├── convertor/                          
│   │   │       ├── exception/           
│   │   │       ├── response/             
│   │   │       └── handler/                 
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       └── data/
│   └── test/
│       └── java/efub/cpbr/crumble
│           └── ...                      
└── README.md

```
