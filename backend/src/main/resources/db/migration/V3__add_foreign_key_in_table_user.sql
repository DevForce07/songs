
use songs;
DROP PROCEDURE IF EXISTS add_new_foreign_key_user_ong;
DELIMITER $$

CREATE PROCEDURE add_new_foreign_key_user_ong()
BEGIN
		IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='ongs' AND COLUMN_NAME ='id_acting_areas')
		THEN
				ALTER TABLE ongs ADD COLUMN id_acting_areas int AFTER id;
				ALTER TABLE ongs ADD FOREIGN KEY (id_acting_areas) REFERENCES acting_areas(id);
		END IF;

END;
$$

DELIMITER ;

CALL add_new_foreign_key_user_ong();

DROP PROCEDURE IF EXISTS add_new_foreign_key_user_ong;