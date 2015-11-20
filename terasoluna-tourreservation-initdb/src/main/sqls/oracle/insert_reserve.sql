/** 初期データ登録用のプロシージャを生成する。 **/
CREATE OR REPLACE PROCEDURE C_RESERVE(
    /* 予約テーブルへの登録データ */
    tourCode IN CHAR,
    adultCount IN INT,
    childCount IN INT,
    customerCode IN CHAR,
    transfer IN CHAR,
    remarks IN VARCHAR)
AS
    reserveCode CHAR(8);
    adultRate INT;
    childRate INT;
    basePrice INT;
BEGIN
    adultRate := 0;
    childRate := 0;
    basePrice := 0;
    
     /* 予約コードシーケンスから予約コードを取得 */
    SELECT
        TO_CHAR(RESERVE_NO_SEQ.NEXTVAL, 'FM09999999') INTO reserveCode
    FROM
      DUAL ;
    
     /* ツアーテーブルから基本料金を取得 */
    SELECT
        BASE_PRICE INTO basePrice
    FROM
      TOURINFO
    WHERE
      TOUR_CODE = tourCode;

     /* 年令区分テーブルから大人の年令区分別割引率を取得 */
    SELECT
        AGE_RATE INTO adultRate
    FROM
      AGE
    WHERE
      AGE_CODE = '0';

     /* 年令区分テーブルから小人の年令区分別割引率を取得 */
    SELECT
        AGE_RATE INTO childRate
    FROM
      AGE
    WHERE
      AGE_CODE = '1';
      
    /* 予約情報の登録 */
    INSERT INTO RESERVE(
      RESERVE_NO,
      TOUR_CODE,
      RESERVED_DAY,
      ADULT_COUNT,
      CHILD_COUNT,
      CUSTOMER_CODE,
      TRANSFER,
      SUM_PRICE,
      REMARKS
    ) VALUES (
      reserveCode,
      tourCode,
      trunc(CURRENT_DATE) - 7,
      adultCount,
      childCount,
      customerCode,
      transfer,
      (basePrice * adultRate / 100 * adultCount) + (basePrice * childRate / 100 * childCount),
      remarks
    );

END;
/

/** 初期予約情報を生成する。 **/
EXECUTE C_RESERVE('0000003012', '5', '3', '00000001', '1', '特になし');
EXECUTE C_RESERVE('0000003012', '4', '0', '00000002', '0', '');
EXECUTE C_RESERVE('0000003012', '1', '3', '00000003', '0', '特になし');
EXECUTE C_RESERVE('0000003012', '2', '5', '00000004', '0', '特になし');
EXECUTE C_RESERVE('0000003012', '2', '0', '00000005', '0', '特になし');
EXECUTE C_RESERVE('0000003012', '2', '2', '00000006', '0', '特になし');
EXECUTE C_RESERVE('0000000002', '5', '3', '00000001', '1', '特になし');
EXECUTE C_RESERVE('0000000003', '4', '0', '00000002', '0', '');
EXECUTE C_RESERVE('0000000003', '1', '3', '00000003', '0', '特になし');
EXECUTE C_RESERVE('0000000005', '2', '2', '00000004', '0', '特になし');
EXECUTE C_RESERVE('0000000005', '2', '1', '00000005', '0', '特になし');
EXECUTE C_RESERVE('0000000008', '2', '0', '00000006', '0', '特になし');
EXECUTE C_RESERVE('0000000008', '2', '0', '00000007', '0', '特になし');
EXECUTE C_RESERVE('0000000008', '2', '0', '00000008', '0', '特になし');
EXECUTE C_RESERVE('0000000008', '4', '0', '00000001', '0', '特になし');

/** 初期データ登録用のプロシージャを削除する。 **/
DROP PROCEDURE C_RESERVE;

COMMIT;

