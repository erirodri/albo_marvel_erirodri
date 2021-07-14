package marvel.albo.erirodri.dao;


import marvel.albo.erirodri.model.Collaborators;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.1
 * @category DAO
 *
 * Dao MongoDb
 * --------------------------------------------------------------
 */
public interface MongoDAOCollaborator extends MongoRepository<Collaborators,Long> {
}
