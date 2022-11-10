create table message(
    id          int identity primary key,
    sender_id   int references [user](id),
    group_id    int references [group](id),
    message     nvarchar(160),
    time_sent   datetime not null default (CURRENT_TIMESTAMP)
                    )