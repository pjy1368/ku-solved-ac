drop table if exists PROBLEM;
drop table if exists USER;
drop table if exists USER_PROBLEM_MAP;

create table if not exists PROBLEM
(
    id    bigint       not null unique,
    level int          not null,
    title varchar(255) not null
);

create table if not exists USER
(
    id       varchar(255) not null unique,
    group_id bigint
);

create table if not exists USER_PROBLEM_MAP
(
    user_id    varchar(255) not null,
    group_id   bigint,
    problem_id bigint       not null
);