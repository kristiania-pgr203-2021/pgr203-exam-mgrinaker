create table question
(
    question_id serial primary key,
    question_title varchar(50) not null,
    question_description varchar(50) not null
)