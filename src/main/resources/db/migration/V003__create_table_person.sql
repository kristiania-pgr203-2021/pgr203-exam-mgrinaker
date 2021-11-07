create table person
(
    person_id serial primary key,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    email varchar(20) not null,
    profession_id integer references profession (profession_id),
    workplace_id integer references workplace (workplace_id)
);

Insert into person (first_name, last_name, email, profession_id, workplace_id)
VALUES ('Siri', 'Wert', 'Sirir@hotmail.no', '1', '1');
Insert into person (first_name, last_name, email, profession_id, workplace_id)
VALUES ('Samuel', 'Porter', 'SPorter@hotmail.no', '3', '1');
Insert into person (first_name, last_name, email, profession_id, workplace_id)
VALUES ('Sander', 'Monsen', 'SanderM@hotmail.no', '2', '2');
Insert into person (first_name, last_name, email, profession_id, workplace_id)
VALUES ('Kassandra', 'Simonsen', 'SimonsenK@hotmail.no', '1', '2');
Insert into person (first_name, last_name, email, profession_id, workplace_id)
VALUES ('Monika', 'Thomson', 'MT@hotmail.no', '3', '1');

