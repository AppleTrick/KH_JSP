DROP SEQUENCE BOARDNOSEQ1;
DROP SEQUENCE GROUPNOSEQ1;
DROP TABLE ANSWERBOARD1;

CREATE SEQUENCE BOARDNOSEQ1;
CREATE SEQUENCE GROUPNOSEQ1;

-- 글번호 , 그룹 번호 , 그룹 순서 , 제목 공백, 삭제 여부 (Y , N )
-- 제목 내용 작성자 작성일
CREATE TABLE ANSWERBOARD1(
	BOARDNO NUMBER NOT NULL,
	GROUPNO NUMBER NOT NULL,
	GROUPSEQ NUMBER NOT NULL,
	TITLETAB NUMBER NOT NULL,
	DEFLAG VARCHAR(2) NOT NULL,
	TITLE VARCHAR2(1000) NOT NULL,
	CONTENT VARCHAR2(4000) NOT NULL,
	WRITER VARCHAR2(500) NOT NULL,
	REGDATE DATE NOT NULL,
	-- 제약 조건 1 : BOARD 에  ANSWER_BOARDNO_PK 라는 이름으로 PK 걸자
	CONSTRAINT ANSWER_BOARDNO_PK PRIMARY KEY (BOARDNO),
	-- 제약 조건 2 : DEFLAG 에 ANSWER_DEFLAG_CK 라는 이름으로 CHECK 걸자 ('Y','N')
	CONSTRAINT ANSWER_DEFLAG_CK CHECK (DEFLAG IN ('Y','N'))
);

SELECT BOARDNO, GROUPNO, GROUPSEQ, TITLETAB, DEFLAG, TITLE, CONTENT, WRITER, REGDATE
FROM ANSWERBOARD1
ORDER BY GROUPNO DESC, GROUPSEQ;