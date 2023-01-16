INSERT INTO 
	rssreader_auth.roles (id, name, flag_active)
VALUES 
	(unhex(replace(uuid(),'-','')), "ADMIN", 1),
	(unhex(replace(uuid(),'-','')), "USER", 1);
