create schema order_processing_system;

create table Publisher(
Publisher_name varchar(100) primary key);

create table Publisher_Address(
Publisher_name varchar(100),
Address varchar(100),
constraint primary key (Publisher_name, Address),
constraint foreign key (Publisher_name) references Publisher(Publisher_name)
);

create table Publisher_Phones(
Publisher_name varchar(100),
Phone varchar(14),
constraint primary key (Publisher_name, Phone),
constraint foreign key (Publisher_name) references Publisher(Publisher_name)
);
create table book(
ISBN_number char(10) primary key,
title varchar(100) not null,
publisher_name varchar(100) not null,
publication_year year not null,
selling_price double not null,
category enum('Science','Art','Religion','History','Geography') not null,
available_copies int not null,
threshold int unsigned not null,
order_quantity int unsigned not null,
constraint foreign key (Publisher_name) references Publisher(Publisher_name));


create table Book_Authors(
ISBN_number char(10),
Author varchar(100),
constraint primary key (ISBN_number, Author),
constraint foreign key (ISBN_number) references Book(ISBN_number));

create table Orders(
id int unsigned primary key auto_increment,
ISBN_number char(10),
order_date datetime,
quantity int unsigned,
constraint foreign key (ISBN_number) references Book(ISBN_number));

create table Users(
username varchar(20) primary key,
first_name varchar(20),
second_name varchar(20),
user_password char(40),
user_phone varchar(14),
user_address varchar(100),
user_email varchar(30),
user_privilege enum('customer', 'manager') not null);

create table Sales(
id int unsigned primary key auto_increment,
username varchar(20),
ISBN_number char(14),
quantity int unsigned default 1,
price double,
checkout_date date,
constraint foreign key (ISBN_number) references Book(ISBN_number),
constraint foreign key (username) references Users(username)); 

DELIMITER $$
create trigger negative_copies_check before update on Book
for each row
begin
	if new.available_copies < 0 then
		signal sqlstate '45000' set message_text = 'Out of coppies';
    end if;
end;
DELIMITER;

DELIMITER $$
create trigger place_order after update on Book
for each row 
begin
	if new.available_copies < new.threshold then
		insert into Orders(ISBN_number, order_date, quantity) values(new.ISBN_number, NOW(), new.order_quantity);
    end if;
end;
DELIMITER;

DELIMITER $$
create trigger confirm_orders before delete on Orders
for each row
begin
	update Book set available_copies = available_copies + old.quantity 
    where Book.ISBN_number = old.ISBN_number;
end;
DELIMITER;

create index title_index on Book(title);
create index category_index on Book(category);
create index author_index on Book_authors(Author);

select * from Book_Authors;
SET GLOBAL log_bin_trust_function_creators = 1;
select sum(price) as total from sales;

insert into Users values('emanrafik', 'eman', 'rafik', SHA1('123456'), null, null, null, 'manager');
insert into Publisher values('Pub');
insert into Book values('0123456789','title','Pub','2014',50, 'Science', 60, 20, 40);
insert into Book values('1123456789','title','Pub','2014',100, 'Science', 30, 20, 40);
