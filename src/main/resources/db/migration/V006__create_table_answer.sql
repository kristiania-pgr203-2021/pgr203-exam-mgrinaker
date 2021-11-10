create table answer
(
    answer_id serial primary key,
    question_id integer references question (id),
    person_id integer references person (person_id),
    option_id integer references option (option_id)
);

insert into answer (question_id, person_id, option_id)
VALUES (1, 2, 2);

insert into answer (question_id, person_id, option_id)
VALUES (2, 2, 3);
