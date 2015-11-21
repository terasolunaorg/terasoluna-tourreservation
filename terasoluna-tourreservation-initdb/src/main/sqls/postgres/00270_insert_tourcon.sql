
--  初期データ登録用のプロシージャを生成する。  --
CREATE OR REPLACE FUNCTION C_TOURCON(
    --  ツアー担当者テーブルへの登録データ  --
    IN tourConName1 VARCHAR,
    IN tourConMail1 VARCHAR,
    IN tourConName2 VARCHAR,
    IN tourConMail2 VARCHAR)
RETURNS 
    INT AS $$
DECLARE
    tourConCode CHAR(10);
    i INT;
BEGIN
    i := 1;
    
    FOR i IN 1..6099 LOOP
       --  担当者コードシーケンスから担当者コードを取得  --
      SELECT
          TO_CHAR(nextval('TOUR_CON_CODE_SEQ'), 'FM0000000999') INTO tourConCode;
      --FROM
      --  DUAL ;
      
      --  ツアー担当者情報の登録  --
      INSERT INTO TOURCON(
        TOUR_CODE,
        TOUR_CON_CODE,
        TOUR_CON_NAME,
        TOUR_CON_MAIL
      ) VALUES (
        TO_CHAR(i, 'FM0000000999'),
        tourConCode,
        tourConName1,
        tourConMail1
      );
      
      IF mod(i,11) = 0 THEN
        --  担当者コードシーケンスから担当者コードを取得  --
	    SELECT
	        TO_CHAR(nextval('TOUR_CON_CODE_SEQ'), 'FM0000000999') INTO tourConCode;
	    --FROM
	    --  DUAL ;
	      
        --  ツアー担当者情報の登録  --
        INSERT INTO TOURCON(
          TOUR_CODE,
          TOUR_CON_CODE,
          TOUR_CON_NAME,
          TOUR_CON_MAIL
        ) VALUES (
          TO_CHAR(i, 'FM0000000999'),
          tourConCode,
          tourConName2,
          tourConMail2
        );
      END IF;
      
      IF mod(i,12) = 0 THEN
        --  担当者コードシーケンスから担当者コードを取得  --
        SELECT
          TO_CHAR(nextval('TOUR_CON_CODE_SEQ'), 'FM0000000999') INTO tourConCode;
        --FROM
        --  DUAL ;
        
        --  ツアー担当者情報の登録  --
        INSERT INTO TOURCON(
          TOUR_CODE,
          TOUR_CON_CODE,
          TOUR_CON_NAME,
          TOUR_CON_MAIL
        ) VALUES (
          TO_CHAR(i, 'FM0000000999'),
          tourConCode,
          tourConName2,
          tourConMail2
        );
      END IF;
    END LOOP;
    RETURN 0;
END;
$$ LANGUAGE plpgsql;


--  初期ツアー担当者情報を生成する。  --
SELECT  C_TOURCON('エヌティティ　太郎', 'ntt1@example.com', 'エヌティティ　次郎', 'ntt2@example.com');

--  初期データ登録用のプロシージャを削除する。  --
DROP FUNCTION C_TOURCON(VARCHAR,VARCHAR,VARCHAR,VARCHAR);

COMMIT;

