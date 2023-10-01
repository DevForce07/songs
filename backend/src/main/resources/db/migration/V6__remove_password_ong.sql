use songs;
DROP PROCEDURE IF EXISTS add_field_description;
DELIMITER $$

CREATE PROCEDURE add_field_description()
BEGIN
		IF  EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='ongs' AND COLUMN_NAME ='password')
		THEN
            ALTER TABLE ongs DROP COLUMN password;
        END IF;

END;
$$

DELIMITER ;

CALL add_field_description();

DROP PROCEDURE IF EXISTS add_field_description;