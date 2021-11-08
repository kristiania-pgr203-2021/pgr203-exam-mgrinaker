create table answer
(
    answer_id serial primary key,
    question_id integer references question (question_id),
    person_id integer references person (person_id),
    response integer not null
);

insert into answer (question_id, person_id, response)
VALUES (1, 2, 2);

insert into answer (question_id, person_id, response)
VALUES (2, 2, 3);
