package marvel.albo.erirodri.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "collaborators")
public class Collaborators {
    @Id
    private ObjectId _Id;
    private String heroName;
    private String lastSync;
    private Object writers;
    private Object editors;
    private Object colorists;

    public Collaborators() {
    }

    public Collaborators(String heroName,String lastSync, Object writers, Object editors, Object colorists) {
        this.heroName=heroName;
        this.lastSync = lastSync;
        this.writers = writers;
        this.editors = editors;
        this.colorists = colorists;
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

    public Object getWriters() {
        return writers;
    }

    public void setWriters(Object writers) {
        this.writers = writers;
    }

    public Object getEditors() {
        return editors;
    }

    public void setEditors(Object editors) {
        this.editors = editors;
    }

    public Object getColorists() {
        return colorists;
    }

    public void setColorists(Object colorists) {
        this.colorists = colorists;
    }

    @Override
    public String toString() {
        return "Collaborators{" +
                "_Id=" + _Id +
                ", lastSync='" + lastSync + '\'' +
                ", writers=" + writers +
                ", editors=" + editors +
                ", colorists=" + colorists +
                '}';
    }
}
