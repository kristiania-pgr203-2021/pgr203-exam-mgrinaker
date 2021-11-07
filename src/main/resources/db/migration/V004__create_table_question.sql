create table question
(
    question_id serial primary key,
    question_title varchar(50) not null,
    question_description varchar(50)
);

Insert into question (question_title, question_description)
values ('Hvordan trives du på jobb?', 'Dette er viktig for oss å vite');
Insert into question (question_title, question_description)
values ('Har du noen venner', 'Viktig');
Insert into question (question_title, question_description)
values ('Er du lykkelig', 'Hva føler du');
Insert into question (question_title, question_description)
values ('Hvem er du?', 'Vi ønsker å bli kjent med deg');
Insert into question (question_title, question_description)
values ('Spiser du lunsj alene', 'Viktig at ingen spiser alene');
;
