/* 初期データ入力のためのツアーコードを生成するシーケンス */
CREATE SEQUENCE TOUR_CODE_SEQ
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9999999999
    START WITH 1
    NOCYCLE
;

/* 北海道から「01：北海道、02：青森県、03：岩手県、04：宮城県、05：秋田県、06：山形県、
   07：福島県、08：茨城県、09：栃木県、10：群馬県」への1ヶ月間のツアー情報を生成するプロシージャ */
CREATE OR REPLACE PROCEDURE C_TOURINFO(
    tourName IN CHAR)
AS
    tourCode CHAR(10);
    i INT;
    j INT;
BEGIN
    i := 1;
    j := 1;
    FOR i IN 1..365 LOOP
      FOR j IN 1..10 LOOP
        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(日帰り)01'), '1', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '14000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(日帰り)02'), '1', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '11000', '0', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(1泊2日)01'), '2', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '19000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(1泊2日)02'), '2', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '16000', '0', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(2泊3日)01'), '3', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '23000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(2泊3日)02'), '3', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '20000', '0', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(3泊4日)01'), '4', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '30000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(3泊4日)02'), '4', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '27000', '0', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(4泊5日)01'), '5', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '37000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(4泊5日)02'), '5', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '34000', '0', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(5泊6日)'), '6', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '45000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

        SELECT
            TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
        FROM
            DUAL ;
  
        INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
        ) VALUES (
          tourCode, trunc(CURRENT_DATE), ('01'||TO_CHAR(j, 'FM09')), (tourName||'(6泊7日)'), '7', (trunc(CURRENT_DATE) + i + 5), '2147483647', '01', TO_CHAR(j, 'FM09'), '0001', '75000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
        );

      END LOOP;
    END LOOP;

END;
/

/* 北海道から「01：北海道」への日帰りツアー情報を生成するプロシージャ */
CREATE OR REPLACE PROCEDURE C_TOURINFO2(
    tourName IN CHAR)
AS
    tourCode CHAR(10);
    i INT;
BEGIN
    i := 1;
    FOR i IN 1..99 LOOP
      SELECT
        TO_CHAR(TOUR_CODE_SEQ.NEXTVAL, 'FM0000000999') INTO tourCode
      FROM
        DUAL ;
  
      INSERT INTO TOURINFO(TOUR_CODE, PLANNED_DAY, PLAN_NO, TOUR_NAME, TOUR_DAYS, DEP_DAY, AVA_REC_MAX, DEP_CODE, ARR_CODE, ACCOM_CODE, BASE_PRICE, CONDUCTOR, TOUR_ABS
      ) VALUES (
        tourCode, trunc(CURRENT_DATE), '0101', (tourName||TO_CHAR(i, 'FM09')), '1', (trunc(CURRENT_DATE) + 7), '2147483647', '01', '01', '0001', '20000', '1', 'そこは別天地、静寂と湯けむりの待つ宿へ…'||CHR(13)||CHR(10)||'詳しい情報はお取り合わせをお願い致します。'
      );
    END LOOP;
END;
/

/** 初期ツアー情報を生成する。 **/
EXECUTE C_TOURINFO('【おすすめ】Terasolunaツアー');
EXECUTE C_TOURINFO('【期間限定】スペシャルツアー');
EXECUTE C_TOURINFO2('【北海道限定】日帰り別天地ツアー');

/** 初期データ登録用のプロシージャとシーケンスを削除する。**/
DROP PROCEDURE C_TOURINFO;
DROP PROCEDURE C_TOURINFO2;
DROP SEQUENCE TOUR_CODE_SEQ;

COMMIT;

