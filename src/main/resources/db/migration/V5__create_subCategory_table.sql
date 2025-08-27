CREATE TABLE sub_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,
    status INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_sub_category_category FOREIGN KEY (category_id) REFERENCES category(id)
);


INSERT INTO sub_category (category_id, name, status , created_at ,updated_at)
VALUES
( 1, 'DRAGON BALL', 1,NOW(), NOW()),
( 1, 'ATTACK ON TITAN', 1,NOW(), NOW()),
( 1, 'NARUTO', 1,NOW(), NOW()),
( 1, 'ONE PEACE ', 1,NOW(), NOW()),
( 1, 'ONE PUNCH MAN', 1,NOW(), NOW()),
( 1, 'JUJUTSU KAISEN', 1,NOW(), NOW()),
( 2, 'RESIDENT EVIL', 2,NOW(), NOW()),
( 2, 'SUPERMAN', 2,NOW(), NOW()),
( 2, 'SPIDER-MAN ', 2,NOW(), NOW()),
( 2, 'AQUAMAN', 2,NOW(), NOW()),
( 2, 'IT', 2,NOW(), NOW()),
( 2, 'CAPTAIN MAVEL', 2,NOW(), NOW()),
( 3, 'FATE GRAND ORDER', 3,NOW(), NOW()),
( 3, 'LEAGUE OF LEGION', 3,NOW(), NOW()),
( 3, 'HONKAI STAR RAIL', 3,NOW(), NOW()),
( 3, 'GENSHIN IMPACT ', 3,NOW(), NOW()),
( 3, 'VANLORANT', 3,NOW(), NOW()),
( 4, 'HALLOWEEN', 4,NOW(), NOW()),
( 4, 'MERRY CHRISTMAS', 4,NOW(), NOW()),
( 1, 'OSHI NO KO', 1,NOW(), NOW()),
( 1, 'SPY x FAMILY', 1,NOW(), NOW());