drop database if exists restaurant;
create database restaurant;

use restaurant;

drop table if exists orders;
drop table if exists menu;


create table orders (
                        id int unsigned not null auto_increment,
                        item1_qty varchar(25) not null,
                        item2_qty varchar(25) not null,
                        item3_qty varchar(25) not null,
                        primary key (id)

);

create table menu (
                      item_id int unsigned  not null auto_increment,
                      name varchar(50) not null,
                      price decimal(5, 2) not null,
                      primary key (item_id)
);

insert into menu (name, price) values ('Cheeseburger', '2.00');
insert into menu (name, price) values ('Pepperoni Pizza', '3.00');
insert into menu (name, price) values ('Nachos', '5.00');


