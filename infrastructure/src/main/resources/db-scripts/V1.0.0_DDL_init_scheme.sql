CREATE TABLE restemailbridge.mail
(
    id       BIGSERIAL PRIMARY KEY,
    body     VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    sender   VARCHAR(255) NOT NULL,
    subject  VARCHAR(255) NOT NULL
);