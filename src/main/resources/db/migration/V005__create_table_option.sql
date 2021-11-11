create table option
(
    id serial primary key,
    option_name varchar(20) not null,
    question_id integer references question (id)
);

insert into option (option_name, question_id) VALUES ('Meget bra', 1);
insert into option (option_name, question_id) VALUES ('Bra', 1);
insert into option (option_name, question_id) VALUES ('Vet ikke', 1);
insert into option (option_name, question_id) VALUES ('Kjedelig', 1);
insert into option (option_name, question_id) VALUES ('Meget Kjedelig', 1);
