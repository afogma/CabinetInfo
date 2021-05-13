create table system_users (
    id int8 primary key,
    name varchar(20),
    password varchar(128),
    user_role varchar(10)
);