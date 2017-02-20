create grain kladr version '1.0';
create table street (
	name varchar(40) NOT NULL,
	socr varchar(10) NOT NULL,
	code varchar(18) NOT NULL PRIMARY KEY,
	postalcode varchar(6) NOT NULL,
	gninmb varchar(4) NOT NULL,
	uno varchar(4) NOT NULL,
	ocatd varchar(11) NOT NULL
) WITH NO VERSION CHECK;

create index ix_street on street (name, code);
