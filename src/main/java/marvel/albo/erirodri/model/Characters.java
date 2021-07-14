package marvel.albo.erirodri.model;

import marvel.albo.erirodri.dto.Comic;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "characters")
public class Characters {
    @Id
    private ObjectId _Id;
    private String heroName;
    private String lastSync;
    private Object characters;

    public Characters() {
    }

    public Characters(String heroName, String lastSync, Object characters) {
        this.heroName = heroName;
        this.lastSync = lastSync;
        this.characters = characters;
    }

    public ObjectId get_Id() {
        return _Id;
    }

    public void set_Id(ObjectId _Id) {
        this._Id = _Id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getLastSync() {
        return lastSync;
    }

    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    public Object getCharacters() {
        return characters;
    }

    public void setCharacters(Object characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "Characters{" +
                "_Id=" + _Id +
                ", heroName='" + heroName + '\'' +
                ", lastSync='" + lastSync + '\'' +
                ", characters=" + characters +
                '}';
    }
}
