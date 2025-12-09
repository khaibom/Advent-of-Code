WITH p1 AS (
    SELECT *
    FROM ids
    CROSS JOIN ranges
)
SELECT COUNT(DISTINCT(id))
FROM p1
WHERE id<= max AND id>=min;
--811

WITH ordered AS (
    SELECT min, max, ROW_NUMBER() OVER (ORDER BY min, max) AS rn
    FROM ranges
),
running_max AS (
    SELECT min, max, rn,
           MAX(max) OVER (ORDER BY rn ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING) AS prev_running_max
    FROM ordered
),
grouped AS (
    SELECT min, max, rn,
        CASE
            WHEN prev_running_max IS NULL OR prev_running_max < min - 1 THEN 1
            ELSE 0
        END AS is_new_group
    FROM running_max
),
numbered AS (
    SELECT min, max,
           SUM(is_new_group) OVER (ORDER BY rn) AS grp
    FROM grouped
),
merged AS (
    SELECT
        MIN(min) AS range_min,
        MAX(max) AS range_max
    FROM numbered
    GROUP BY grp
)
SELECT
    SUM(range_max - range_min + 1) AS fresh_count
FROM merged;
--338189277144473