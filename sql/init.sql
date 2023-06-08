create database datasource1;
use datasource1;
create table user
(
    id   decimal(20) not null
        primary key,
    name varchar(50) null
);


create database datasource2;
use datasource2;
create table user
(
    id           decimal(20) not null
        primary key,
    name         varchar(50) null,
    created_time datetime    null,
    update_time  datetime    null
);

