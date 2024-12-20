CREATE TABLE usr
(
    user_id  BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255)        NOT NULL,
    username VARCHAR(35) UNIQUE  NOT NULL,
    password VARCHAR(128)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE note
(
    note_id    BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id    BIGINT       NOT NULL,
    group_id   BIGINT    DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES usr (user_id) ON DELETE CASCADE
);

CREATE TABLE _group
(
    group_id BIGSERIAL PRIMARY KEY,
    title    VARCHAR(255) NOT NULL,
    user_id  BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usr (user_id) ON DELETE CASCADE
);