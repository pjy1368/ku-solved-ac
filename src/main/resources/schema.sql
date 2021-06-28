drop table if exists USER_PROBLEM_MAP;
drop table if exists USER;
drop table if exists PROBLEM;
drop table if exists TEMP_USER_PROBLEM_MAP;
drop table if exists TEMP_USER;
drop table if exists TEMP_PROBLEM;

create table if not exists PROBLEM
(
    id           bigint       not null unique,
    level        int          not null,
    title        varchar(255) not null,
    solved_count int          not null,
    primary key (id)
);

create table if not exists USER
(
    id       varchar(255) not null unique,
    group_id bigint,
    primary key (id)
);

create table if not exists USER_PROBLEM_MAP
(
    user_id    varchar(255) not null,
    group_id   bigint,
    problem_id bigint       not null,
    foreign key (user_id) references USER (id),
    foreign key (problem_id) references PROBLEM (id)
);

create table if not exists TEMP_PROBLEM
(
    id           bigint       not null unique,
    level        int          not null,
    title        varchar(255) not null,
    solved_count int          not null,
    primary key (id)
);

create table if not exists TEMP_USER
(
    id       varchar(255) not null unique,
    group_id bigint,
    primary key (id)
);

create table if not exists TEMP_USER_PROBLEM_MAP
(
    user_id    varchar(255) not null,
    group_id   bigint,
    problem_id bigint       not null,
    foreign key (user_id) references TEMP_USER (id),
    foreign key (problem_id) references TEMP_PROBLEM (id)
);