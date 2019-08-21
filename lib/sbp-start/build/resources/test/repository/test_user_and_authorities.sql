INSERT
    INTO
        authority(name)
    VALUES("ROLE_ADMIN");

INSERT
    INTO
        USER(
            id,
            name
        )
    VALUES(
        UUID(),
        "admin"
    );

INSERT
    INTO
        user_authority(
            user_id,
            authority_name
        ) SELECT
            id AS user_id,
            "ROLE_ADMIN"
        FROM
            USER
        WHERE
            name = "admin";

INSERT
    INTO
        authority(name)
    VALUES("ROLE_CLIENT");

INSERT
    INTO
        USER(
            id,
            name
        )
    VALUES(
        UUID(),
        "client"
    );

INSERT
    INTO
        user_authority(
            user_id,
            authority_name
        ) SELECT
            id AS user_id,
            "ROLE_CLIENT"
        FROM
            USER
        WHERE
            name = "client";
