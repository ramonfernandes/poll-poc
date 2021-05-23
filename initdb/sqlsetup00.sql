CREATE TABLE poll_table (
   poll_id uuid PRIMARY key,
   title varchar(50),
   description varchar(255),
   is_open boolean
);

CREATE TABLE voting_table (
    vote_id uuid PRIMARY key,
    cpf varchar(11),
    poll_id uuid,
    is_yes boolean,
	FOREIGN KEY (poll_id) REFERENCES poll_table (poll_id)
)