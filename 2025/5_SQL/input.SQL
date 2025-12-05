CREATE TABLE ranges (
    min BIGINT,
    max BIGINT
);
CREATE TABLE range_lines (
    line TEXT
);

INSERT INTO ranges (min, max)
SELECT
    split_part(line, '-', 1)::BIGINT AS min,
    split_part(line, '-', 2)::BIGINT AS max
FROM range_lines;


CREATE TABLE IDs(
    id BIGINT
);

