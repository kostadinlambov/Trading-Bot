create table roles
(
    id        varchar(255) not null,
    authority varchar(255) not null,
    primary key (id)
);

create table users
(
    id                         varchar(255)         not null,
    is_account_non_expired     BOOLEAN DEFAULT TRUE not null,
    is_account_non_locked      BOOLEAN DEFAULT TRUE not null,
    is_credentials_non_expired BOOLEAN DEFAULT TRUE not null,
    email                      VARCHAR(50) BINARY   not null,
    is_enabled                 BOOLEAN DEFAULT TRUE not null,
    first_name                 varchar(255)         not null,
    last_name                  varchar(255)         not null,
    password                   varchar(255)         not null,
    profile_pic_url            TEXT,
    username                   VARCHAR(50) BINARY   not null,
    primary key (id)
);

create table users_roles
(
    user_id varchar(255) not null,
    role_id varchar(255) not null,
    primary key (user_id, role_id)
);

alter table users
    add constraint uq_user_email unique (email);
alter table users
    add constraint uq_user_username unique (username);
alter table users_roles
    add constraint fk_role_id foreign key (role_id) references roles (id);
alter table users_roles
    add constraint fk_user_id foreign key (user_id) references users (id);
