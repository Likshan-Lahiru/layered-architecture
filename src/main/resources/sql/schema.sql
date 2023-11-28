drop database if exists EshenEnterprise;

create database if not exists EshenEnterprise;

use EshenEnterprise;


create table Supplier(
                         supplier_id varchar (35) primary key ,
                         supplier_name varchar (155) not null ,
                         NIC varchar(14)not null ,
                         address text not null ,
                         contact_number varchar (15) not null
);

create table tool(
                     tool_id varchar (35) primary key ,
                     tool_name varchar(20) not null ,
                     qty_on_hand int not null,
                     image longblob,
                     rent_per_day_price double
);

create table supplier_tool(
                              supplier_id varchar(35),
                              supplier_name varchar(155),
                              tool_id varchar(35),
                              tool_name varchar(20),
                              supplied_date varchar(20),
                              quantity_supplied int not null,
                              constraint foreign key (supplier_id) references Supplier (supplier_id) on delete cascade on UPDATE cascade,
                              constraint foreign key (tool_id) references tool (tool_id) on delete  cascade on UPDATE cascade
);
create table stock_list(
                tool_id varchar(35),
                tool_name varchar(20),
                qty_on_hand int not null,
                waste_tool int,
                constraint foreign key (tool_id) references tool (tool_id) on delete  cascade on UPDATE cascade
);

create table customer(
                         customer_id varchar(35) primary key,
                         customer_name varchar(50)not null ,
                         address text not null,
                         NIC varchar(14) not null,
                         contact_number varchar(11) not null
);

create table employee(
                         employee_id varchar(35) primary key,
                         employee_name varchar(50) not null,
                         NIC varchar(14) not null ,
                         address text not null
);

create table employee_attandance(
                                    employee_id varchar(35),
                                    date date not null ,
                                    in_time time not null ,
                                    out_time time not null ,
                                    constraint foreign key (employee_id) references employee (employee_id) on DELETE cascade on UPDATE cascade
);

create table user(
                     user_name varchar(35) primary key ,
                     user_first_name varchar(20) not null ,
                     user_sconde_name varchar(20) not null ,
                     email  varchar(50) not null ,
                     password varchar(30) not null

);

create table orders(
                       customer_id varchar(35) ,
                       order_id varchar(35) primary key ,
                       order_date date,
                       description text ,
                       constraint foreign key (customer_id) references customer (customer_id) on DELETE cascade on UPDATE cascade

);

create table order_detail(
                             tool_id varchar (35) ,
                             order_id varchar(35) ,
                             qty int not null ,
                             unit_price double not null,
                             order_date varchar(20),
                             status varchar(20) not null ,
                             constraint foreign key (tool_id) references tool (tool_id) on delete cascade on UPDATE cascade ,
                             constraint foreign key (order_id) references orders (order_id) on delete cascade on UPDATE cascade
);

create table vehical(
                        vehical_id varchar(35) primary key ,
                        status text not null ,
                        last_service_date varchar(20) not null,
                        number_plate_no varchar(20)
);

create table delivery(
                         order_id varchar(35) ,
                         vehical_id varchar(35) ,
                         description text ,
                         location text not null ,
                         constraint foreign key (order_id) references orders (order_id) on DELETE cascade on UPDATE cascade ,
                         constraint foreign key (vehical_id) references vehical (vehical_id) on DELETE cascade on UPDATE cascade
);