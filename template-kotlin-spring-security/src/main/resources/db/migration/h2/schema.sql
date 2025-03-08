create table users (
   id UUID DEFAULT gen_random_uuid() not null,
   email varchar(100) not null UNIQUE,
   password varchar(100) not null,
   name varchar(100) not null,
   primary key (id)
);