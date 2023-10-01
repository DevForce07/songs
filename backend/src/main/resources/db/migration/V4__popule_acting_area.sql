
use songs;

use songs;
DROP PROCEDURE IF EXISTS popule_acting_area;
DELIMITER $$

CREATE PROCEDURE popule_acting_area()
BEGIN
	IF EXISTS(SELECT count(*) FROM acting_areas)
	THEN
		INSERT INTO acting_areas(name) VALUES('saúde');
		INSERT INTO acting_areas(name) VALUES('educação');
		INSERT INTO acting_areas(name) VALUES('meio ambiente');
		INSERT INTO acting_areas(name) VALUES('direitos humanos');
		INSERT INTO acting_areas(name) VALUES('cultura');
		INSERT INTO acting_areas(name) VALUES('esportes');
		INSERT INTO acting_areas(name) VALUES('outros');
	END IF;
END;
$$

DELIMITER ;

CALL popule_acting_area();

DROP PROCEDURE IF EXISTS popule_acting_area;
  