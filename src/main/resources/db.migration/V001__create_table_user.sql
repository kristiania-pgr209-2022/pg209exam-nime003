create table [user](
    id int identity primary key,
    username nvarchar(64) not null,
    password nvarchar(64),
);
