CREATE TABLE app_user (
    id BIGSERIAL,
    nickname VARCHAR(50) NOT NULL,
    login VARCHAR(50) UNIQUE,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE realm (
    id BIGSERIAL,
    name VARCHAR(100) NOT NULL,
    app_user_id BIGINT NOT NULL REFERENCES app_user(id),
    PRIMARY KEY(id)
);

CREATE TABLE folder (
    id BIGSERIAL,
    name VARCHAR(150) NOT NULL,
    realm_id BIGINT NOT NULL REFERENCES realm(id),
    PRIMARY KEY(id)
);

CREATE TABLE link_collection (
    id BIGSERIAL,
    name VARCHAR(150) NOT NULL,
    url VARCHAR(250) NOT NULL,
    realm_id BIGINT NOT NULL REFERENCES realm(id),
    PRIMARY KEY(id)
);

CREATE TABLE bookmark (
    id BIGSERIAL,
    url VARCHAR(250) NOT NULL,
    title VARCHAR(150) NOT NULL,
    folder_id BIGINT NOT NULL REFERENCES folder(id),
    PRIMARY KEY(id)
);

CREATE TABLE recurrence (
    id BIGSERIAL,
    url VARCHAR(250) NOT NULL,
    title VARCHAR(150) NOT NULL,
    started BOOLEAN,
    last_start TIMESTAMP,
    last_finish TIMESTAMP,
    app_user_id BIGINT NOT NULL REFERENCES app_user(id),
    PRIMARY KEY(id)
);
