-- Drop tables
drop table if exists ERS_REIMBURSEMENT; 
drop table if exists ERS_USERS;
drop table if exists ERS_USER_ROLES;
drop table if exists ERS_REIMBURSEMENT_STATUS;
drop table if exists ERS_REIMBURSEMENT_TYPE;


-- create ERS_USER_ROLES table 
create table ERS_USER_ROLES (
	ERS_USER_ROLE_ID SERIAL primary key,
	USER_ROLE VARCHAR(10)
);

-- create ERS_USERS table 
create table ERS_USERS (
	ERS_USERS_ID SERIAL primary key,
	ERS_USERNAME VARCHAR(50) not null,
	ERS_PASSWORD VARCHAR(50) not null,
	USER_FIRST_NAME VARCHAR(100)not null,
	USER_LAST_NAME VARCHAR(100) not null,
	USER_EMAIL VARCHAR(150) not null,
	USER_ROLE_ID INTEGER not null,
	
	constraint ERS_USERS_UNv1 unique(ERS_USERNAME, USER_EMAIL),
	constraint USER_ROLES_FK foreign key(USER_ROLE_ID) references ERS_USER_ROLES(ERS_USER_ROLE_ID)  
);

-- create ERS_REIMBURSEMENT_STATUS table 
create table ERS_REIMBURSEMENT_STATUS (
	REIMB_STATUS_ID SERIAL primary key,
	REIMB_STATUS VARCHAR(10) 
);

-- create ERS_REIMBURSEMENT_TYPE table 
create table ERS_REIMBURSEMENT_TYPE (
	REIMB_TYPE_ID SERIAL primary key,
	REIMB_TYPE VARCHAR(10)
);

-- create ERS_REIMBURSEMENT table
create table ERS_REIMBURSEMENT (
	REIMB_ID SERIAL primary key,
	REIMB_AMOUNT NUMERIC(7,2) not null check (REIMB_AMOUNT > 0),
	REIMB_SUBMITTED TIMESTAMPTZ,
	REIMB_RESOLVED TIMESTAMPTZ,
	REIMB_DESCRIPTION VARCHAR(250) not null,
	REIMB_RECEIPT BYTEA ,
	REIMB_AUTHOR INTEGER not null ,
	REIMB_RESOLVER INTEGER,
	REIMB_STATUS_ID INTEGER not null default 1,
	REIMB_TYPE_ID INTEGER not null, 
	
	constraint ERS_USERS_FK_AUTH foreign key(REIMB_AUTHOR) references ERS_USERS(ERS_USERS_ID),
	constraint ERS_USERS_FK_RESLVR foreign key(REIMB_RESOLVER) references ERS_USERS(ERS_USERS_ID),
	constraint ERS_REIMBURSEMENT_STATUS_FK foreign key(REIMB_STATUS_ID) references ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID),
	constraint ERS_REIMBURSEMENT_TYPE_FK foreign key(REIMB_TYPE_ID) references ERS_REIMBURSEMENT_TYPE(REIMB_TYPE_ID)
);

-- Insert users roles
insert into ers_user_roles (user_role)
values 
	('Manager'),
	('Employee');

-- Insert reimburesment statuses: Pending, Approved, and Denied into the ers_reimbursement_status table
insert into ers_reimbursement_status (reimb_status)
values 
	('Pending'),
	('Approved'),
	('Denied');

-- Insert reimbursement types: Lodging, travel, food, and other into the ers_reimbursement_type table
insert into ers_reimbursement_type (REIMB_TYPE)
values 
	('Lodging'),
	('Travel'),
	('Food'),
	('Other');

insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
values 
('johndoe','test123', 'John', 'Doe', 'johndoe@gmail.com', 1),
('janedoe','test123','Jane', 'Doe', 'janedoe@gmail.com',2),
('darthvader', 'test123', 'Darth', 'Vader', 'darthvader@gmail.com',2);