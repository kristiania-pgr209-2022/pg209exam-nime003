create table [user](
    id bigint identity primary key,
    username nvarchar(64) not null,
    password nvarchar(64)
);
