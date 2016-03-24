DROP TABLE iowniwant.user;
DROP TABLE iowniwant.goals;

CREATE TABLE iowniwant.user (
	user_id					bigserial NOT NULL,
	first_name			varchar(60) NOT NULL,
	last_name				varchar(60) NOT NULL,
	nick_name				varchar(60) NOT NULL UNIQUE,
	user_password		varchar(60) NOT NULL,
	email						varchar(60),
	month_salary		real DEFAULT 0,
CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);

CREATE TABLE iowniwant.goals (
	goals_id 			bigserial  NOT NULL,
	title 				varchar(255) NOT NULL,
	cost 					real DEFAULT 0,
	description 	varchar(255),
	pubdate 			varchar(255),
	notes 				varchar(255),
CONSTRAINT goals_id_pk PRIMARY KEY(goals_id)
);

INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes)
values ('PC Computer',164.69,'THIS TEXT REPRESENTS AN ARTICLE FOR WHATEVEN YOU DID ADD','22-13-62'
	,'Gently my mind escapes into the relaxing mode of pleasure A pleasure that will take my mind off the reality of life ');
INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes)
values ('Waschmachine',500.56,'PC TO WORK WHILE YOU RELAX SO WILL IT WORK THIS IS RAP','23-13-62'
	,'My past life Life as I know it know And whatever may come it slowly disappears');
INSERT INTO iowniwant.goals (title, cost, description, pubdate, notes)
values ('Vacuum Triode',5124.78,'PC TO WORK WHILE YOU RELAX SO WILL IT WORK THIS IS RAP','24-13-62'
	,'to somewhere in the back of my mind It will remain there until I wish to retrieve it ');