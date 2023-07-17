drop table if exists product;
create table product
(
    product_id   varchar(50) primary key,
    product_name varchar(20) not null,
    category     varchar(50) not null,
    price        bigint      not null,
    description  varchar(500) default null,
    created_at   datetime(6) not null,
    updated_at   datetime(6)  default null
);

create table `order`
(
    order_id     varchar(50) primary key,
    email        varchar(50)  not null,
    address      varchar(200) not null,
    postcode     varchar(200) not null,
    order_status varchar(50)  not null,
    created_at   datetime(6)  not null,
    updated_at   datetime(6) default null
);

create table order_item
(
    seq        bigint      not null primary key auto_increment,
    order_id   varchar(50) not null,
    product_id varchar(50) not null,
    category   varchar(50) not null,
    price      bigint      not null,
    quantity   int         not null,
    created_at datetime(6) not null,
    updated_at datetime(6) default null,
    key idx_order_id (order_id),
    constraint fk_order_item_to_order foreign key (order_id) references `order` (order_id) on delete cascade,
    constraint fk_order_item_to_product foreign key (product_id) references product (product_id) on delete cascade
);
