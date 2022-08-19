-- MAKE TABLES
create table ers_user_roles(
	ers_user_role_id int primary key,
	user_role varchar(10) not null
);

create table ers_users(
	ers_users_id serial primary key,
	ers_username varchar(50) unique not null,
	ers_password varchar(50) not null,
	user_first_name varchar(100) not null,
	user_last_name varchar(100) not null,
	user_email varchar(150) unique not null,
	user_role_id int not null references ers_user_roles(ers_user_role_id)
);
	
create table ers_reimbursement_status(
	reimb_status_id int primary key,
	reimb_status varchar(10) not null
);
	
create table ers_reimbursement_type(
	reimb_type_id int primary key,
	reimb_type varchar(10) not null
);
	
create table ers_reimbursement(
	reimb_id serial primary key,
	reimb_amount double precision not null,
	reimb_submitted timestamp not null,
	reimb_resolved timestamp,
	reimb_description varchar(250),
	reimb_receipt varchar(250),
	reimb_author int not null references ers_users(ers_users_id),
	reimb_resolver int references ers_users(ers_users_id),
	reimb_status_id int not null references ers_reimbursement_status(reimb_status_id),
	reimb_type_id int not null references ers_reimbursement_type(reimb_type_id)
);

-- POPULATE TABLES
insert into ers_user_roles (ers_user_role_id, user_role) values
	(1, 'employee'),
	(2, 'manager');

insert into ers_reimbursement_type (reimb_type_id, reimb_type) values
	(1, 'lodging'),
	(2, 'travel'),
	(3, 'food'),
	(4, 'other');

insert into ers_reimbursement_status (reimb_status_id, reimb_status) values
	(1, 'pending'),
	(2, 'approved'),
	(3, 'denied');
	
insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values 
	('jlee11', 'a123', 'Jason', 'Lee', 'jlee11@openstar.net', 2),
	('terrycup','z123', 'Terry', 'Walker', 'tcup12@openstar.net', 1),
	('amack24', 'p123', 'Allison', 'Mackerel', 'afish@openstar.net', 1),
	('big34', 'o123', 'Shaquille', 'Doreal', 'bigshaqd@openstar.net', 1),
	('swhite', 'w123', 'Sandra', 'White', 'swhite@openstar.net', 2);

insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) values 
	(1000.00, '2022-06-06 00:00:00', '2022-06-13 00:00:00', 'Chicago office travel & board', '/resources/images/terrycup_1.png', 2, 5, 2, 2),
	(250.00, '2022-07-30 00:00:00', null, 'client dinner and drinks', '/resources/images/big34_2.png', 4, null, 1, 3),
	(100.00, '2022-08-01 00:00:00', null, 'metrocard', '/resources/images/amack24_3.png', 3, null, 1, 2),
	(100.00, '2022-08-10 00:00:00', '2022-08-11 00:00:00', 'emergency printer toner', '/resources/images/jlee11_4.png', 1, 5, 2, 4),
	(140.00, '2022-08-13 00:00:00', null, 'Metro North weekly pass', '/resources/images/terrycup_5.png', 2, null, 1, 2),
	(50.00, '2022-08-14 00:00:00', null, 'Pay my phone bill', '/resources/images/swhite_6.png', 5, 1, 3, 4);
	
-- QUERIES
-- get user roles and their usernames 
select ers_username, user_role from ers_user_roles roles inner join ers_users u on roles.ers_user_role_id = u.user_role_id;

-- get reimb_type & reimb entry id per reimb entry
select reimb_id, reimb_type from ers_reimbursement_type ert inner join ers_reimbursement re on ert.reimb_type_id = re.reimb_type_id;

-- get all reimb data per username
select * from ers_reimbursement er inner join ers_users eu on er.reimb_author = eu.ers_users_id;

-- get all reimb data per status
select * from ers_reimbursement er inner join ers_reimbursement_status ers on er.reimb_status_id = ers.reimb_status_id where ers.reimb_status = 'pending';

-- get all reimb data per authorID
select * from ers_reimbursement er inner join ers_users eu on er.reimb_author = eu.ers_users_id;

-- get all reimb data per resolver
select * from ers_reimbursement er inner join ers_users eu on er.reimb_resolver = eu.ers_users_id;

-- get all users (to create User objects)
select * from ers_users;

-- get all users w/ pending reimbs (User objs)
select eu.* from ers_users eu inner join ers_reimbursement er on eu.ers_users_id = er.reimb_author where er.reimb_status_id = 1;

-- get all users w/ denied reimbs (User objs)
select eu.* from ers_users eu inner join ers_reimbursement er on eu.ers_users_id = er.reimb_author where er.reimb_status_id = 3;

-- get all users w/ approved reimbs (User objs)
select eu.* from ers_users eu inner join ers_reimbursement er on eu.ers_users_id = er.reimb_author where er.reimb_status_id = 2;

-- get all reimb data where submitted date < given date
select * from ers_reimbursement er where reimb_submitted < '2022-08-01';

-- get all reimb data where submitted date >= given date
select * from ers_reimbursement er where reimb_submitted >= '2022-08-01';

-- get all reimb data where resolved date < given date
select * from ers_reimbursement er where reimb_resolved  < '2022-08-01';

-- get all reimb data where resolved date >= given date
select * from ers_reimbursement er where reimb_resolved  >= '2022-08-01';

-- get all reimb data where submitted date < given date PER user
select * from ers_reimbursement er inner join ers_users eu on er.reimb_author = eu.ers_users_id where reimb_submitted < '2022-08-01' and ers_username = 'terrycup';

-- get all reimb data where submitted date >= given date PER user
select * from ers_reimbursement er inner join ers_users eu on er.reimb_author = eu.ers_users_id where reimb_submitted >= '2022-08-01' and ers_username = 'terrycup';

-- get all PENDING data where submitted date < given date 
select * from ers_reimbursement er where reimb_submitted < '2022-08-01' and er.reimb_status_id = 1;

-- get all PENDING data where submitted date >= given date 
select * from ers_reimbursement er where reimb_submitted >= '2022-08-01' and er.reimb_status_id = 1;

-- get all PENDING per username
select * from ers_reimbursement er inner join ers_users eu on er.reimb_author = eu.ers_users_id where eu.ers_username ='terrycup' and er.reimb_status_id = 1;

-- Employee create new request (REQUEST-OBJECT MAKE then parse to DB)
insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) values 
-- value guideline (double, now(), null, <user entered>, <user upload, system parsed>, userID, null, 1, <user_selected>)


