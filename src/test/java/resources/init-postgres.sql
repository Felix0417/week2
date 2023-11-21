CREATE TABLE IF NOT EXISTS hospitals
(
    id        serial primary key,
    name      varchar(255) not null,
    address   varchar(255),
    estimated timestamp default now()
);

CREATE TABLE IF NOT EXISTS doctors
(
    id          serial primary key,
    name        varchar(255) not null,
    salary      integer,
    hospital_id integer
);

CREATE TABLE IF NOT EXISTS patients
(
    id      serial primary key,
    name    varchar(255) not null,
    age     integer,
    address varchar(255)
);


CREATE TABLE IF NOT EXISTS doctor_patient
(
    doctor_id  integer not null references doctors on delete cascade,
    patient_id integer not null references patients on delete cascade,
    primary key (doctor_id, patient_id)
);



INSERT INTO hospitals (name, address, estimated)
VALUES ('Bolnitza', 'address 3', '2023-11-14 20:46:41.113103'),
       ('Blokhina', 'address 2', '2023-11-15 03:42:21.961245'),
       ('Sechenov', 'address 1', '2023-11-14 20:46:41.113103');

INSERT INTO doctors (id, name, salary, hospital_id)
VALUES (1, 'Ivanov', 100, 1),
       (2, 'Petrov', 120, 2),
       (3, 'Pirogov', 90, 1),
       (4, 'Hirurgov', 90, 2),
       (5, 'Svetlov', 100, 3),
       (6, 'Terapevtov', 90, 1),
       (7, 'Terapevtova', 90, 1);

INSERT INTO patients (id, name, age, address)
VALUES (1, 'Vasya', 30, 'address-1-1'),
       (2, 'Natasha', 35, 'address-1-4'),
       (3, 'Nikita', 45, 'address-1-5'),
       (4, 'Yulia', 23, 'address-1-6'),
       (5, 'Misha', 30, 'address-1-7'),
       (6, 'Kolya', 19, 'address-1-8'),
       (7, 'Felix', 30, 'address101');


INSERT INTO public.doctor_patient (doctor_id, patient_id)
VALUES (1, 2),
       (2, 1),
       (2, 4),
       (2, 2),
       (4, 6),
       (5, 7),
       (5, 6);
