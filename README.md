# 🍳 냉털쿡 — 냉장고 재료 기반 레시피 추천 서비스 백엔드 레포지토리

## 프로젝트 소개

**2025 멋쟁이사자처럼 운영진톤 출품 프로젝트**입니다.

냉장고에 있는 재료를 선택하면 GPT가 해당 재료들로 만들 수 있는 **맞춤 레시피를 자동 생성해주는 요리 추천 서비스**입니다.
사용자는 원하는 재료를 선택하고, 레시피를 받아보고, 자신만의 요리 커스텀 경험을 즐길 수 있습니다.

![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS%20S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

---

# 💡 담당한 개발 내용
<br>

> Docker 기반 CI/CD, GPT API 연동, 재료 데이터 처리, 회원 기능 등 **냉털쿡 서비스의 핵심 백엔드 기능을 전반적으로 구현했습니다.**


## 1. Docker + GitHub Actions 기반 CI/CD 파이프라인 구축
- `openjdk:17-jdk-slim` 이미지를 사용하여 실행 환경 구성  
- 애플리케이션 빌드 후 **Docker Hub 자동 업로드**  
- `main` 브랜치 머지 시 **EC2 서버 자동 배포** 자동화  
<br><br>

## 2. GPT API 기반 레시피 생성
- 사용자가 입력한 재료와 원하는 요리명을 기준으로 **프롬프트 구성 로직 구현**
- OpenAI GPT API 연동 및 레시피 텍스트 응답 수신
- GPT 응답을 파싱하여 **레시피명, 요리 과정, 재료 리스트 등 구조화된 데이터**로 변환  
<br><br>

## 3. 재료 선택 기능 및 S3 이미지 제공
- 식재료 카테고리(채소/육류/해산물/곡물 등) 별 **이미지 로드 API 개발**
- 재료 이미지를 **AWS S3에 업로드**하고 URL 형태로 프론트에 전달
- 사용자가 선택한 재료를 **JPA 쿼리로 DB에 저장**
<br><br>

## 4. 회원 기능 개발
- 회원가입 (닉네임, 아이디, 비밀번호, 나이대, 가구 유형 등)
- UserId 기반으로 JPA를 통해 유저 조회 및 개인정보 저장
<br><br>

## 5. API 문서화
- **Swagger**를 이용해 전체 API 자동 문서화  
<br><br>

---

## 개발 기간

2025.01 ~ 2025.02 

---

## 팀 구성 및 역할
<br>

| 이름 | 역할 |
|------|------|
| **김나영** | 백엔드 |
| 김도경 | 백엔드 |
| 강승현 | 프론트엔드 |
| 차현우 | 프론트엔드 |
| 신애진 | 기획/디자인 |
