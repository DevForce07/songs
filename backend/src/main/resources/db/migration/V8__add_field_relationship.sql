use songs;
DROP PROCEDURE IF EXISTS add_field_relationship;
DELIMITER $$

CREATE PROCEDURE add_field_relationship()
BEGIN
		IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='ongs' AND COLUMN_NAME ='id_perfil')
		THEN
            ALTER TABLE ongs ADD COLUMN id_perfil int;
            ALTER TABLE ongs  ADD FOREIGN KEY (id_perfil) REFERENCES Perfil(id);
        END IF;

END;
$$

DELIMITER ;

CALL add_field_relationship();

DROP PROCEDURE IF EXISTS add_field_relationship;