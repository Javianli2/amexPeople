DROP TABLE  IF EXISTS  People;
DROP INDEX  IF EXISTS  email_upper;

CREATE TABLE IF NOT EXISTS People (
	id integer  not null auto_increment,
	name varchar(30) not null,
	age integer not null,
	birthdate DATE not null,
	email varchar(50) not null unique,
	primary key(id, name)
)

