create table workplace
(
    workplace_id serial primary key,
    workplace_name varchar(50) not null,
    workplace_address varchar(50)
);

INSERT INTO workplace (workplace_name, workplace_address)
VALUES ('Karlson AS', 'Gurivegen 23');
INSERT INTO workplace (workplace_name, workplace_address)
VALUES ('Vertmo AS', 'Karl Johans gate 45');
INSERT INTO workplace (workplace_name, workplace_address)
VALUES ('Kimmisons AS', 'Henrik Ibsens veg 91');
