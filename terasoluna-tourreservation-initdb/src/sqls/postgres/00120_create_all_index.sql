
--  ツアーテーブルのインデックス  --
CREATE INDEX TOURINFO_IDX1 ON
    TOURINFO (
      DEP_CODE
    , ARR_CODE
    , DEP_DAY
    , AVA_REC_MAX
    );

--  予約テーブルのインデックス  --
CREATE INDEX RESERVE_IDX1  ON 
    RESERVE  (
      TOUR_CODE
    );


COMMIT;
