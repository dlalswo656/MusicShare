# 개발 환경
1. IDE: IntelliJ IDEA Community
2. Spring Boot version '2.7.8'
3. JDK 11
4. mysql
5. Spring Data JPA
6. Thymeleaf

# MS(Music Share) 구현
1. 회원가입
2. 로그인
3. 회원정보 수정(/)
4. 로그아웃
5. 비밀번호 찾기, 임시 비밀번호 발급
6. 게시판
7. 업로드
8. Security
9. CSRF(토큰)

# User 주요기능
1. 회원가입(/Join)
2. 회원가입 유효성 검사(/js/JoinChk.js)
3. 비밀번호 암호화
4. 로그인(/Login)
5. 로그인 유효성 검사(/js/Login.js)
6. 로그아웃(/Logout)
7. 임시 비밀번호 발급(/User/sendPw)
8. 마이페이지(/User/{id})
9. 내정보 수정(/User/Update)
10. Admin, User role(역할)
11. Security
12. CSRF(토큰)

# 게시판 주요기능
1. 글쓰기(/board/Save)
2. 글목록(/board/)
3. 글조회(/board/{id})
4. 글수정(/board/Update/{id})
5. 글삭제(/board/Delete/{id})
6. 페이징 처리(/board/Paging)
7. 댓글(/board/Reply)
8. 업로드(/board/Upload)