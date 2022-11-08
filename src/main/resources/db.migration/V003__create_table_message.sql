create table messages(
    id int identity primary key,
    sender_id int foreign key references [user](id),
    group_id int foreign key references [group](id),
    message nvarchar(160),
    date_sent datetime not null default (CURRENT_TIMESTAMP),
)