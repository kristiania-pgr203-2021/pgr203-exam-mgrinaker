create table profession
(
    profession_id serial primary key,
    profession_title varchar(20)
);

insert into profession ( profession_title)
values ('Utvikler');
insert into profession ( profession_title)
values ('Danser');
insert into profession ( profession_title)
values ('Konsulent');