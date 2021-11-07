create table profession
(
    profession_id serial primary key,
    profession_title varchar(20)
);

insert into profession ( profession_title)
values ('Utvikler');
values ('Danser');
values ('Konsulent');