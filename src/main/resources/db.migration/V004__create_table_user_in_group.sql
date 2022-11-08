create table user_in_group(
    /*not certain what to call this table, but this makes the most sense for us*/
    user_id int foreign key references [user](id),
    group_id int foreign key references [group](id),
)