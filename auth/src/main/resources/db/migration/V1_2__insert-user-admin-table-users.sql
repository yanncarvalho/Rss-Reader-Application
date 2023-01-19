INSERT INTO 
     ${flyway:defaultSchema}.users (id, username, name, password, role) 
VALUES 
      (unhex(replace(uuid(),'-','')), 'ROOT', 'ADMIN', '$2a$10$Lk8NZ9leuMFgC45/dGJkvuJ7QuPP8XryN2XU7q1FeNPDCrQ4xdH42', 'ADMIN');
