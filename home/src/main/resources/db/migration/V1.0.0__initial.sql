create sequence BOOKING_ENTITY_SEQ start WITH 1 increment 50;

create table booking_entity (
        id integer not null,
        name varchar not null,
        description varchar,
        type varchar not null,
        is_archived boolean not null,
        added_on date not null,
        console_type varchar,
        primary key (id)
);