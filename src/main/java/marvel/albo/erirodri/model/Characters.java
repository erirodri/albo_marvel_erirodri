package marvel.albo.erirodri.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.1
 * @category model
 *
 * Model to collect info related to Characters
 * --------------------------------------------------------------
 */
@Document(collection = "characters")
public class Characters {
    @Id
    private ObjectId _Id;
    private String heroNameCharact;
    private String lastSyncCharact;
    private Object charactersInfo;

    public Characters() {
        // Constructor ...
    }

    public Characters(String heroName, String lastSync, Object charactersInfo) {
        this.heroNameCharact = heroName;
        this.lastSyncCharact = lastSync;
        this.charactersInfo = charactersInfo;
    }

    public ObjectId getId() {
        return _Id;
    }

    public void setId(ObjectId _Id) {
        this._Id = _Id;
    }

    public String getHeroNameCharact() {
        return heroNameCharact;
    }

    public void setHeroNameCharact(String heroNameCharact) {
        this.heroNameCharact = heroNameCharact;
    }

    public String getLastSyncCharact() {
        return lastSyncCharact;
    }

    public void setLastSyncCharact(String lastSyncCharact) {
        this.lastSyncCharact = lastSyncCharact;
    }

    public Object getCharacters() {
        return charactersInfo;
    }

    public void setCharacters(Object characters) {
        this.charactersInfo = characters;
    }

    @Override
    public String toString() {
        return "Characters{" +
                "_Id=" + _Id +
                ", heroName='" + heroNameCharact + '\'' +
                ", lastSync='" + lastSyncCharact + '\'' +
                ", characters=" + charactersInfo +
                '}';
    }
}
