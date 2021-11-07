create table question
(
    question_id serial primary key,
    question_title varchar(50) not null,
    question_description varchar(50)
);

Insert into question (question_title, question_description)
values ('Hvordan trives du på jobb?', 'Dette er viktig for oss å vite');
values ('Har du noen venner');
values ('Er du lykkelig', 'Hva føler du');
values ('Hvem er du?', 'Vi ønsker å bli kjent med deg');
values ('Spiser du lunsj alene', 'Viktig at ingen spiser alene');
;
