CREATE SCHEMA custom_connector_app;
USE custom_connector_app;
DROP TABLE IF EXISTS identity;
CREATE TABLE `custom_connector_app`.`identity` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `image` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`));
INSERT INTO identity(username, firstname, lastname, image)
VALUES ("testidentity","Test","Identity","https://upload.wikimedia.org/wikipedia/commons/a/a5/Red_Kitten_01.jpg"),
("larryHelpski4","Larry","Helpski",""), ("austinReeves2","Austin","Reeves","");
SELECT * FROM identity;