DROP SEQUENCE MDBOARDSEQ;
DROP TABLE MDBOARD;

CREATE SEQUENCE MDBOARDSEQ;

CREATE TABLE MDBOARD(
	SEQ NUMBER PRIMARY KEY,
	WRITER VARCHAR2(100) NOT NULL,
	TITLE VARCHAR2(1000) NOT NULL,
	CONTENT VARCHAR2(4000) NOT NULL,
	REGDATE DATE NOT NULL
);

INSERT INTO MDBOARD
VALUES(MDBOARDSEQ.NEXTVAL, '관리자', '멀티딜리트 테스트','시발', SYSDATE);

INSERT INTO MDBOARD
VALUES(MDBOARDSEQ.NEXTVAL, '앵그리버드', '화가 난다', '정말 화가난다.', SYSDATE);

SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE
FROM MDBOARD
ORDER BY SEQ DESC;

SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE 
FROM MDBOARD
ORDER BY SEQ DESC;