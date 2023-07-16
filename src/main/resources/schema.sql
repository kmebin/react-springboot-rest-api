drop table if exists product;
create table product
(
    product_id      varchar(50) primary key,
    product_name    varchar(20) not null,
    category        varchar(50) not null,
    price           bigint not null,
    description     varchar(500) default null,
    created_at      datetime(6) not null,
    updated_at      datetime(6) default null
);
