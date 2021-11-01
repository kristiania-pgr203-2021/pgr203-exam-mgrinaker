create table person
(
    person_id serial primary key,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    email varchar(20) not null,
    profession_id integer references profession (profession_id),
    workplace_id integer references workplace (workplace_id)
);

