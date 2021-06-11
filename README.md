# ku-solved-ac
- 건국대학교 학생들의 Solved.ac 통계를 보여주는 프로그램입니다.

## 0. 목적
- 백준 플랫폼을 통해 단순히 건국대 유저가 누가 있는지, 그리고 랭킹만 알 수 있다는 점이 아쉬웠다.
- 

## 1. 기술 스택
- Main: Spring with Java, MySQL
- Infra: Linux, Docker, Nginx

## 2. 기능
- 전체 문제 조회
  - 티어 별로 조회하는 필터링 기능
- 건국대 개개인의 유저가 해결한 문제 조회
- 건국대 그룹이 해결한 문제 조회
- 건국대 그룹이 해결하지 못한 문제 조회

## 3. 실행 방법
- MySQL이 설치되어 있어야 합니다.
- MySQL 테이블 정보는 [이곳](https://github.com/pjy1368/ku-boj-solved-ac/tree/main/src/main/resources)에 있는 'schema.sql' 파일을 사용하시면 됩니다.
- 'cmd.sh' 스크립트 파일을 실행해 주시면 됩니다.
```
chmod +x cmd.sh
./cmd.sh
```
