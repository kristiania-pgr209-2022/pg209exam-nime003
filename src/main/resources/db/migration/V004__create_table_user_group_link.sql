create table user_group_link(
    /* not certain what to call this table, but this makes the most sense for us */
    user_id bigint not null references [user](id),
    group_id bigint not null references [group](id)
);