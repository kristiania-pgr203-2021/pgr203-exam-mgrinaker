create table answer
(
    question_id integer references question (question_id),
    person_id integer references person (person_id),
    response integer not null
)