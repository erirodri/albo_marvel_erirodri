package marvel.albo.erirodri.dao;

import marvel.albo.erirodri.model.Characters;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category DAO
 *
 * Dao MongoDb to manage Characters
 * --------------------------------------------------------------
 */
public interface MongoDAOCharacter extends MongoRepository<Characters,Long> {
}
