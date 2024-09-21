--test sql.txt에 있는 sql부터 실행 후
--BeansPaM 계정을 만들고 넘어가서 실행해주세요.

--member 테이블 sql
CREATE table member(
m_no number(5) primary key,
m_id varchar2(20) unique,	
m_pw varchar2(20),	
m_name varchar2(30) not null,
m_day date,
m_position varchar2(15) check (m_position IN ('인턴', '사원', '대리', '과장', '차장', '부장', '팀장', '전무', '이사')),
m_phone varchar2(15),
m_leave number(2),
m_salary number(10),
m_dept varchar2(15),
m_email varchar2(30)
);

--salary 테이블 sql
create table salary(
m_no number(5) not null,
sal_date date,
sal_salary number(8),
CONSTRAINT fk_salary_m_no FOREIGN KEY (m_no) REFERENCES member(m_no)
);	

--attendance 테이블 sql
CREATE TABLE attendance (
m_no number(5) PRIMARY KEY,          
a_workdate DATE,       
a_checkin DATE,        
a_checkout DATE,       
a_issue VARCHAR2(30),    
CONSTRAINT fk_m_no FOREIGN KEY (m_no) REFERENCES member(m_no)  
);

--document 테이블 sql
CREATE table document(
d_no number(5) primary key,
d_title	varchar2(60),
d_class varchar2(12) check (d_class in('출장서','품의서','휴가계','지출서')),
m_no number(5),
d_m_no number(5),	
d_content varchar2(1000),
d_request date,
d_response date,
CONSTRAINT fk_document_m_no FOREIGN KEY(m_no) REFERENCES member(m_no),
CONSTRAINT fk_document_d_m_no FOREIGN key(d_m_no) REFERENCES member(m_no)
);

--location 테이블 sql
CREATE table location(
l_code varchar2(10) primary key,
l_name varchar2(30),
l_continent varchar2(15) check (l_continent IN('asia', 'africa', 'america'))
);

--product 테이블 sql
create table product(
p_no varchar2(10) primary key,
p_name varchar2(50),
p_corr varchar2(20),
l_code varchar2(10),
constraint fk_product foreign key(l_code) references location(l_code)
);

--warehouse 테이블 sql
CREATE table warehouse(
w_no number(8) primary key,
w_loc varchar2(30),
m_no number(5),
w_temp number(5),
w_humi number(4),
CONSTRAINT fk_warehouse_m_no FOREIGN key(m_no) REFERENCES member(m_no)		
);	
	
--stock 테이블 sql 
CREATE table stock(
s_incom varchar2(8) primary key,
s_no number(8),	
s_date date ,
m_no number(5),
p_no varchar2(10),
w_no number(8),
s_amount number(8),
CONSTRAINT fk_stock_p_no foreign key(p_no) REFERENCES product(p_no),
CONSTRAINT fk_stock_w_no FOREIGN key(w_no)REFERENCES warehouse(w_no),
constraint fk_stock_m_no foreign key(m_no) references member(m_no)
);

--transaction 테이블 sql
create table transaction(
t_no number(8) primary key,
t_inout	varchar2(6) check(t_inout in('수입','수출')),
t_date date,
t_value	number(8),
s_incom varchar2(8),
t_corr varchar2(24),
m_no number(5),
CONSTRAINT fk_transaction FOREIGN KEY (m_no) REFERENCES member(m_no),
CONSTRAINT fk_transaction_sno FOREIGN KEY (s_incom) REFERENCES stock(s_incom)
);

--board 테이블 sql
-- create table board(
-- b_class	varchar2(15) check(b_class in('qna','notice')),
-- b_no number(8),
-- b_title varchar2(90),	
-- b_writer varchar2(20),
-- b_right varchar2(12) check(b_right in('security','normal')),
-- b_date date,
-- b_views number(8),
-- b_content varchar2(4000),
-- b_filepath varchar2(100),
-- constraint pk_board PRIMARY KEY (b_class, b_no)
-- );

--comments 테이블 sql
CREATE table comments(
b_class	varchar2(15) not null,
b_no number(8) not null,
c_writer varchar2(20),	
c_date date,
c_content varchar2(1500),
CONSTRAINT fk_comment FOREIGN key(b_class,b_no) REFERENCES board(b_class,b_no)
);

--notice 테이블 sql(설보라)
create table notice(
n_no number(8),
n_title varchar2(90) not null,
n_content varchar2(4000),	
n_views number(8) default 0,
n_delete_yn char(2) default 'N' -- 삭제여부
n_c_date	date default sysdate,  -- 작성일
n_r_date	date default sysdate,    -- 수정일
n_c_writer varchar2(10) not null, -- 작성자
n_r_writer varchar2(10)not null, -- 수정자
);


--시퀀스들
--m_no 시퀀스
CREATE SEQUENCE seq_m_no 
start WITH 1
MINVALUE 1
NOCACHE 
;

--qna 시퀀스
CREATE sequence seq_qna
start WITH 1
MINVALUE 1
NOCACHE ;

--notice 시퀀스
CREATE sequence seq_notice
start WITH 1
MINVALUE 1
NOCACHE ;

--document 시퀀스 sql
create SEQUENCE seq_d_no 
start with 1
minvalue 1
NOCACHE ;

--transaction  시퀀스 sql
create SEQUENCE seq_t_no 
START WITH 1
MINVALUE 1
NOCACHE ;

--s_code 시퀀스 sql
CREATE sequence seq_s_code 
start WITH 1
MINVALUE 1
NOCACHE ;
