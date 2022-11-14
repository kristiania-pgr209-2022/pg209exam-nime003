create table message(
    id          bigint identity primary key,
    time_sent   smalldatetime not null default (CURRENT_TIMESTAMP),
    user_id     bigint references [user](id),
    group_id    bigint references [group](id),
    message     nvarchar(160)
);