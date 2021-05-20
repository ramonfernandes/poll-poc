CREATE TABLE poll_table (
   poll_id uuid PRIMARY key,
   title varchar(20),
   description varchar(255),
   is_open boolean
);

CREATE TABLE user_table (
   user_id uuid PRIMARY key,
   name varchar(20)
);

CREATE TABLE voting_table (
    user_id uuid,
    poll_id uuid,
    is_yes boolean,
	FOREIGN KEY (poll_id) REFERENCES poll_table (poll_id),
	FOREIGN KEY (user_id) REFERENCES user_table (user_id)
)