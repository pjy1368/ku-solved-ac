drop table if exists PROBLEM;
drop table if exists USER;
drop table if exists USER_PROBLEM_MAP;
drop table if exists TEMP_PROBLEM;
drop table if exists TEMP_USER;
drop table if exists TEMP_USER_PROBLEM_MAP;

create table if not exists PROBLEM
(
    id bigint auto_increment not null,
    problem_id bigint not null unique,
    title varchar(255) not null
);

create table if not exists USER
(
    id bigint auto_increment not null,
    nickname varchar(255) not null unique
);

create table if not exists USER_PROBLEM_MAP
(
  user_id bigint not null,
  problem_id bigint not null
);

create table if not exists TEMP_PROBLEM
(
    id bigint auto_increment not null,
    title varchar(255) not null
);

create table if not exists TEMP_USER
(
    id bigint auto_increment not null,
    nickname varchar(255) not null unique
);

create table if not exists TEMP_USER_PROBLEM_MAP
(
    user_id bigint not null,
    problem_id bigint not null
);
