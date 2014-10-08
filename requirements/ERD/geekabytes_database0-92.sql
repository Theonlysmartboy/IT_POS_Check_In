-- ------------------------------------------------------------
-- create database
-- database version 0.92
-- ------------------------------------------------------------
/*
NOTES
*ready for production

TODO
*change ints to smallints that can be
*attachment table
*todo support

CHANGES 0.70
*populate data
*make address table, we serve businesses who have many tickets with same address
CHANGES 0.75
*create framework for attachment table
*fix client - computer relationship
CHANGES 0.76
*added company field to client, does not need to be a table
CHANGES 0.78
*company is a table now
CHANGES 0.80
*added support level
CHANGES 0.85
*production ready, needs attachments and todo
CHANGES 0.90
*added indexes, can add manually to exisiting database
CHANGES 0.91
*added is_on_site to work table
*added hours_worked to invoice
*added all prices
*part_order is now connected to invoice
CHANGES 0.92
*/
drop database GeekabytesLLC;

create database if not exists GeekabytesLLC;

use GeekabytesLLC;

/*
-- drop tables to create fresh database
-- comment out these staements on first run
set foreign_key_checks = 0;
drop table if exists part;
drop table if exists job_title;
drop table if exists part_order;
drop table if exists user;
drop table if exists client;
drop table if exists ticket;
drop table if exists computer;
drop table if exists invoice;
drop table if exists condition_record;
drop table if exists computer_condition;
drop table if exists work;
drop table if exists tag;
drop table if exists status;
drop table if exists bench;
-- drop table if exists timesheet;
set foreign_key_checks = 1;
*/

-- ------------------------------------------------------------
-- create all the tables
-- ------------------------------------------------------------

-- table to try and capture common parts, will have to show user history when entering in data
create table if not exists part (
partid int primary key not null auto_increment,
name varchar(45) not null,
part_description varchar(100))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- fake data, purge eventually
insert into part (partid, name, part_description) values
(1, 'A1278 Trackpad', 'Trackpad for 2008 MacBook Pro'),
(2, 'HP DV9000 Motherboard', 'HP Part Number XXXXXXX'),
(3, 'Triwing Screwdrivier', 'Screwdriver needed for 2008 MacBook Pro trackpad removal'),
(4, 'Crucial 8GB 1333MHz DDR3 RAM', 'Generic RAM'),
(5, 'Samsung 8GB 1333MHz DDR3 RAM', 'Mac compatible RAM');

create table if not exists job_title (
titleid tinyint primary key not null auto_increment,
-- technician, secretary, owner
job_name varchar(15) not null,
description varchar(60))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- real data
insert into job_title (titleid, job_name, description) values
(1, 'Owner', 'Owner of the company. Also involved in day to day management'),
(2, 'Secretary', 'Front offices. Keeps track of bills and invoices'),
(3, 'Technician', 'Main worker. Works on computers');

create table if not exists part_order (
orderid int primary key not null auto_increment,
userid int not null,
partid int not null,
date_ordered date,
date_recieved date,
order_cost decimal(10,2) not null,
-- just put Amazon, eBay, etc for now. Could be table in futre to speed up queries
order_site varchar(30))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- fake data
insert into part_order (orderid, userid, partid, date_ordered, date_recieved, order_cost, order_site) values
(1, 1, 1, 19930313, 20080628, 34.99, 'eBay'),
(2, 3, 3, 20140701, 20140704, 6.98, 'iFixit');

create table if not exists user (
userid int primary key not null auto_increment,
titleid tinyint,
username varchar(20) not null,
name varchar(30),
date_hired date)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- real data
insert into user (userid, titleid, username, name, date_hired) values
(1, 1, 'DanielDevers', 'Daniel Devers', null),
(2, 2, 'Arlene', 'Arlene Devers', null),
(3, 3, 'Kyle', 'Kyle Riedemann', null),
(4, 3, 'Marcus', 'Marcus Gabilheri', null),
(5, 3, 'Meelaud', 'Meelaud Boozary', null),
(6, 3, 'Sam', 'Sam Jackson', null),
(7, 3, 'DanielDouthit', 'Daniel Douthit', null);

-- we're suppose to handle clocking in and out, can do math later with query results
create table if not exists timesheet (
timeid int primary key not null auto_increment,
userid int not null,
clock_in datetime,
clock_out datetime)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- fake data
-- datetime format YYYY-MM-DD HH:MM:SS
insert into timesheet (timeid, userid, clock_in, clock_out) values
(1, 1, '2014-07-01 10:00:00', '2014-07-01 18:00:00'),
(2, 3, '2014-07-01 12:00:00', '2014-07-01 18:00:00'),
(3, 3, '2014-07-02 12:00:00', '2014-07-02 18:00:00'),
(4, 1, '2014-07-02 10:00:00', '2014-07-02 19:00:00'),
(5, 6, '2014-07-02 11:00:00', '2014-07-02 16:00:00');

create table if not exists client (
clientid int primary key not null auto_increment,
addressid int,
name varchar(35) not null,
-- large for phone, but we need to account for extensions
phone varchar(15) not null,
phone2 varchar(15),
email varchar(30),
-- just differentiate between phone and email, default should be call
predered_contact boolean)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- some real, some fake, for testing
insert into client (clientid, addressid, name, phone, phone2, email, predered_contact) values
(1, 1, 'Daniel Devers', '4055331715', null, 'daniel@geekabytes.com', true),
(2, 1, 'Daniel Devers', '4055331715', null, 'daniel@geekabytes.com', true),
(3, 2, 'Marina Yereshenko', '4055332037', null, null, false),
(4, 3, 'Kyle Riedemann', '4054713895', '4053599818', 'kyle@riedemann.info', true);	

create table if not exists address (
addressid int primary key not null auto_increment,
companyid int,
address_line1 varchar(30),
address_line2 varchar(30),
address_line3 varchar(30),
address_city varchar(30),
address_zip varchar(10),
address_country varchar(30))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- some real, some fake, for testing
insert into address (addressid, companyid, address_line1, address_line2, address_line3, address_city, address_zip, address_country) values
(1, 2, 'PO Box 1215', null, null, 'Stillwater', '74076', 'United States'),
(2, 1, '314 S. Lowry', 'Apt #4', null, 'Stillwater', '74074', 'United States'),
(3, 1, '2903 N Perkins Rd', 'Apt C205', null, 'Stillwater', '74075', 'United States');	

create table if not exists company (
companyid int primary key not null auto_increment,
supportid tinyint,
company_name varchar(50) not null)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into company (companyid, supportid, company_name) values
(1, null, 'Unaffiliated'),
(2, 4, 'GeekabytesLLC');

create table if not exists support_level (
supportid tinyint primary key not null auto_increment,
support_package varchar(30),
support_description text)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- we can run a query every day that says if a company has hit their service limit or not
insert into support_level (supportid, support_package, support_description) values
-- preventative maintinence
(1, 'Tier 1', 'Maintinence on up to 5 PCs, every 6 months'),
-- grand lodge tier
(2, 'Tier 2', 'Maintinence on up to 6 PCs, every 6 months. 1 on-site service calls per month'),
-- small business
(3, 'Tier 3', 'Maintinence on up to 6 PCs, every 6 months. 3 on-site service calls per month'),
-- mid-sized
(4, 'Tier 4', 'Maintinence on up to 10 PCs, every 4 months. 6 on-sites service calls per month');

create table if not exists computer (
computerid int primary key not null auto_increment,
clientid int not null,
-- maker of the computer, custom is possible
make varchar(15),
serial_number varchar(30),
processor varchar(30),
-- just value in GB
ram varchar(10),
hard_drive varchar(10),
operating_system varchar(20),
windows_product_key varchar(30),
computer_notes text,
benchid tinyint)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- fake data
insert into computer (computerid, clientid, make, serial_number, processor, ram, hard_drive, operating_system, windows_product_key, computer_notes, benchid) values
(1, 4, 'System76', null, 'i7-4750', '12 GB', '120 GB, 500 GB', 'Ubuntu 14.04', null, 'This is Kyle''s computer. It''s probably better than yours.', 10),
(2, 1, 'Apple', null, 'i7', '8 GB', '500 GB', 'OS X Mavericks', null, 'This is a Mac', 10),
(3, 4, 'Custom', null, 'i7', '8 GB', '500 GB', 'Windows 8.1', null, 'example data', 10);

create table if not exists computer_condition (
computer_conditionid tinyint primary key not null auto_increment,
computer_condition_name varchar(20) not null)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- real data, check with Daniel if he likes these
insert into computer_condition (computer_conditionid, computer_condition_name) values
(1, 'New'),
(2, 'Refurbished'),
(3, 'Mint'),
(4, 'Great'),
(5, 'Good'),
(6, 'Fair'),
(7, 'Poor'),
(8, 'Bad'),
(9, 'Usable Components'),
(10, 'Non Salvageable');

create table if not exists condition_record (
condition_recordid int primary key not null auto_increment,
computer_conditionid tinyint not null,
computerid int not null,
date_condition date not null,
condition_notes text not null)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- fake data
insert into condition_record (condition_recordid, computer_conditionid, computerid, date_condition, condition_notes) values
(1, 1, 1, '2014-06-01', 'Computer arrived from supplier'),
(2, 3, 1, '2014-06-02', 'Computer was opened, no signs of wear'),
(3, 4, 1, '2014-07-01', 'Computer shows slight scratches and scuffs');

create table if not exists work (
workid tinyint primary key not null auto_increment,
work_description varchar(50) not null,
standard_price decimal(10,2) not null,
is_on_site boolean not null,
is_hourly_rate boolean not null)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- real data
-- diferentaite between onsite with 'where work_description like 'On-Site%''
insert into work (workid, work_description, standard_price, is_on_site, is_hourly_rate) values
(1, 'No Charge', 00.00, false, false),
(2, 'Hourly Labor Rate', 54.00, false, true),
(3, 'Data Backup', 59.00, false, false),
(4, 'Computer Reformat/System Restore', 65.00, false, false),
(5, 'Diagnostic Fee', 29.00, false, false),
(6, 'Quick Fix', 29.00, false, false),
(7, 'Virus Removal', 69.00, false, false),
(8, 'General Computer Support', 54.00, false, true),
(9, 'Computer Tune-up', 54.00, false, false),
(10, 'Operating System Install', 60, false, false),
(11, 'Data Drive Wipe', 20.00, false, false),
(12, 'Data Recovery From Hard Drive', 100.00, false, false),
(13, 'Data Recovery From Failed Hard Drive', 500.00, false, false),
(14, 'Create Recovery DVD', 45.00, false, false),
(15, 'On-Site Computer Tune-up', 69.00, true, false),
(16, 'On-Site General Computer Support Rate', 64.00, true, true),
(17, 'On-Site Operating System Install', 80.00, true, false),
(18, 'On-Site computer Reformat/System Restore', 95.00, true, false),
(19, 'On-Site Virus Removal', 89.00, true, false),
(20, 'On-Site Data Backup', 55.00, true, false),
(21, 'On-Site Create Recovery DVD', 55.00, true, false),
(22, 'On-Site New PC Setup', 85.00, true, false),
(23, 'On-Site New PC Setup Additional Software Install', 35, true, false),
(24, 'On-Site Network Troubleshooting', 75.00, true, true),
-- we charge per foot of wire, but this is a part
(25, 'On-Site Network Drop', 90.00, true, false),
(26, 'On-Site Telephone Drop', 80.00, true, false),
(27, 'On-Site Wireless Network Install', 85.00, true, false),
(28, 'On-Site Router Replacement', 50.00, true, false),
(29, 'Website Domain Names', 9.99, false, false),
(30, 'Website Hosting Plans', 5.99, false, false),
(31, 'Custom Website Design', 299.00, false, false),
(32, 'Website Maintinence', 25.00, false, false),
(33, 'One-on-One Computer Training', 40, false, true);

-- status needs to be its own table
-- I didnt want to conect 5 tables to ticket
create table if not exists status (
statusid tinyint primary key not null auto_increment,
-- status_name should only be abandoned, on workbench, etc.
status_name varchar(20) not null)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- real data
insert into status (statusid, status_name) values
(1, 'On The Bench'),
(2, 'Waiting For Bench'),
(3, 'On Hold'),
(4, 'Waiting on Part'),
-- waiting on payment will be checked if invoice has been paid
(5, 'Abandoned'),
(6, 'Completed');

create table if not exists bench (
benchid tinyint primary key not null auto_increment,
-- bench number is 1-4
bench_name varchar(20) not null)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- real data
insert into bench (benchid, bench_name) values
(1, 'Workbench #1'),
(2, 'Workbench #2'),
(3, 'Workbench #3'),
(4, 'Workbench #4'),
(5, 'Laptop Workbench'),
(6, 'Daniel''s Desk'),
(7, 'Dustin''s Desk'),
(8, 'Mac Workbench'),
(9, 'Workroom Shelf'),
(10, 'Fininshed Room');

-- tickets are the current values, price can change
create table if not exists ticket (
ticketid int primary key not null auto_increment,
clientid int not null,
userid int,
statusid tinyint not null,
date_enter date not null,
-- date picked up and left building
date_exit date,
ticket_notes text)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into ticket (ticketid, clientid, userid, statusid, date_enter, date_exit, ticket_notes) values
(1, 1, 3, 1, '2014-07-01', null, 'Installing a superior operating sysytem on MacBook Pro'),
(2, 4, 1, 6, '2014-07-01', '2014-07-04', 'Reinstalling Ubuntu to fix heat issues');

-- invoice will be the printed bill, prices frozen for historical data
-- freezing od price is achieved with the use of an associative entity
create table if not exists invoice (
invoiceid int primary key not null auto_increment,
ticketid int not null,
workid tinyint not null,
orderid int,
hours_worked decimal(4,2),
-- date work was completed and client was called
date_finalized date,
client_price decimal(10,2),
-- if date paid is null, we can assume the bill is outstanding
date_paid date)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into invoice (invoiceid, ticketid, workid, orderid, hours_worked, date_finalized, client_price, date_paid) values
(1, 1, 1, null, 0, null, 00.00, null),
(2, 2, 1, 2, 0, '2014-07-03', 00.00, '2014-07-06');

/*
BASIC FRAMEWORK FOR ATTACHMENT TABLE
We need to store files on the server that are attached to tickets/invoices
These should be pictures/repair guides/etc

create table if not exists attachment (
attachmentid int primary key not null auto_increment,
invoiceid int,
filepath text,
upload_date datetime,
modified_date datetime);

alter table invoice
	add constraint fk_attachmendid_invoice foreign key (attachmentid) references attachment (attachmentid);
*/

-- ------------------------------------------------------------
-- alter statements
-- ------------------------------------------------------------

alter table user
    add constraint fk_titleid_user foreign key (titleid) references job_title (titleid);

alter table part_order
    add constraint fk_userid_order foreign key (userid) references user (userid);
alter table part_order
	add constraint fk_partid_order foreign key (partid) references part (partid);

alter table client
	add constraint fk_addressid_client foreign key (addressid) references address (addressid);

alter table ticket
    add constraint fk_clientid_ticket foreign key (clientid) references client (clientid);
alter table ticket
    add constraint fk_userid_ticket foreign key (userid) references user (userid);
alter table ticket
	add constraint fk_statusid_ticket foreign key (statusid) references status (statusid);

alter table condition_record
    add constraint fk_computer_conditionid_condition_record foreign key (computer_conditionid) references computer_condition (computer_conditionid);
alter table condition_record
    add constraint fk_computerid_condition_record foreign key (computerid) references computer (computerid);

alter table invoice
    add constraint fk_ticketid_invoice foreign key (ticketid) references ticket (ticketid);
alter table invoice
    add constraint fk_workid_invoice foreign key (workid) references work (workid);
alter table invoice
	add constraint fk_orderid_invoice foreign key (orderid) references part_order (orderid);

alter table computer
	add constraint fk_benchid_computer foreign key (benchid) references bench (benchid);
alter table computer
	add constraint fk_clientid_computer foreign key (clientid) references client (clientid);

alter table timesheet
	add constraint fk_userid_timesheet foreign key (userid) references user (userid);

alter table address
	add constraint fk_companyid_address foreign key (companyid) references company (companyid);

alter table company
	add constraint fk_supportid_company foreign key (supportid) references support_level (supportid);

-- ------------------------------------------------------------
-- alter statements
-- ------------------------------------------------------------

ALTER TABLE job_title ADD INDEX(titleid, job_name, description);

alter table user add index(userid, titleid, username, name, date_hired);

alter table bench add index(benchid, bench_name);

alter table computer_condition add index(computer_conditionid, computer_condition_name);

alter table support_level add index(supportid, support_package);

alter table status add index(statusid, status_name);