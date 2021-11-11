create table question
(
    id serial primary key,
    question_title varchar(50) not null,
    question_description varchar(50)
);

Insert into question (question_title, question_description)
values ('Hvordan trives du?', 'Dette er viktig for oss');
Insert into question (question_title, question_description)
values ('Har du noen venner', 'Viktig');
Insert into question (question_title, question_description)
values ('Er du lykkelig?', 'Ikke viktig');
Insert into question (question_title, question_description)
values ('Hvem er du?', 'Vi vil kjenne deg');
Insert into question (question_title, question_description)
values ('Spiser du lunsj alene', 'Viktig at ingen spiser alene');
;
