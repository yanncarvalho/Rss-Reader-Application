CREATE TABLE IF NOT EXISTS rssreader_auth.roles (
	id  BINARY(16) NOT NULL,
	name VARCHAR(255) NOT NULL,
	flag_active TINYINT(1) NOT NULL DEFAULT 1,
	PRIMARY KEY(id)
);
