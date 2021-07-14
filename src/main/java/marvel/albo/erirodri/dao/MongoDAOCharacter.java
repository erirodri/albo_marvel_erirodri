package marvel.albo.erirodri.dao;

import marvel.albo.erirodri.model.Characters;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDAOCharacter extends MongoRepository<Characters,Long> {
}
