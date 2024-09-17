--참고사항!!!
--외래키 제약조건이 있는 테이블 생성 sql 입니다.

--!! 이전에 테스트용 sql 유저를 생성했다면  beanspam 접속해제후 
--TEST 아이디에서
drop user beanspam cascade; 
--유저아이디 제거를한 이후에 다시 생성해주세요

--유저생성
CREATE user BeansPaM IDENTIFIED BY 1111;
--테이블 생성, 셀렉트 권한 부여 
GRANT CONNECT,RESOURCE to BeansPaM;