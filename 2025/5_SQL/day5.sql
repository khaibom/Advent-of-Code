WITH p1 AS (
    SELECT *
    FROM ids
    CROSS JOIN ranges
)
SELECT COUNT(DISTINCT(id))
FROM p1
WHERE id<= max AND id>=min;
--811

