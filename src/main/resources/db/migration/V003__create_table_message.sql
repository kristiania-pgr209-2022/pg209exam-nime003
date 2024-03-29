create table message(
    id          bigint identity primary key,
    time_sent   datetime2(0) null default (CURRENT_TIMESTAMP),
    user_id     bigint references [user](id),
    group_id    bigint references [group](id),
    title       nvarchar(50),
    message     nvarchar(512)
);