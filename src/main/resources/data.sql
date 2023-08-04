create table password
(
    password_id     bigint auto_increment primary key,
    hashed_password varchar(255) not null
);

create table member_token
(
    member_token_id bigint auto_increment primary key,
    jwt_token       varchar(500)
);


create table member
(
    member_id       bigint auto_increment primary key,
    email           varchar(128) not null UNIQUE,
    password_id     bigint       not null,
    member_token_id bigint        null,
    constraint fk_password
        foreign key (password_id) references password (password_id),
    constraint fk_member_token
        foreign key (member_token_id) references member_token (member_token_id)
);


create table board
(
    board_id     bigint auto_increment primary key,
    title        varchar(100) not null,
    content      text         not null,
    created_date datetime     not null DEFAULT CURRENT_TIMESTAMP,
    deleted      tinyint(1) not null default 0,
    member_id    bigint not null,
    constraint fk_member
        foreign key (member_id) references member (member_id)
);
