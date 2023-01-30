CREATE TABLE `properties`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `name`         varchar(200) NOT NULL,
    `topic`        varchar(255) NOT NULL,
    `concurrency`  int          NOT NULL,
    `group_id`      varchar(255) NOT NULL,
    `auto_startup` tinyint(1) NOT NULL,
    `created_at`   timestamp    NOT NULL DEFAULT current_timestamp(),
    `updated_at`   timestamp    NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp (),
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_idx_properties_name_type_env` (`name`)
)