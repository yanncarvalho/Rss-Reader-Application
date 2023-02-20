INSERT INTO 
     ${flyway:defaultSchema}.users (id, username, name, password, role) 
VALUES 
      (unhex(replace(uuid(),'-','')), 'ROOT', 'ADMIN', '$2a$10$jSnnE4XUMIsUG9xrF1PuEOMkPG5ZE1le1SCzI.GGNFLSuZlJ3nrDO', 'ADMIN');
