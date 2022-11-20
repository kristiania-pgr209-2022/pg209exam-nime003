create table [user](
    id bigint identity primary key,
    username nvarchar(64) not null,
    email nvarchar(64),
    password nvarchar(64)
);
