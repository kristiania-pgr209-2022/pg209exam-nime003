create table user_in_group(
    /*not certain what to call this table, but this makes the most sense for us*/
    user_id bigint not null references [user](id),
    group_id bigint not null references [group](id)
);