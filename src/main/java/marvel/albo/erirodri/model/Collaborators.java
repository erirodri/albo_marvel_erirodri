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
 * Model to collect info related to Collaborators
 * --------------------------------------------------------------
 */
@Document(collection = "collaborators")
public class Collaborators {
    @Id
    private ObjectId _Id;
    private String heroNameCollab;
    private String lastSyncCollab;
    private Object writers;
    private Object editors;
    private Object colorists;

    public Collaborators() {
        // Constructor ...
    }

    public Collaborators(String heroName,String lastSync, Object writers, Object editors, Object colorists) {
        this.heroNameCollab =heroName;
        this.lastSyncCollab = lastSync;
        this.writers = writers;
        this.editors = editors;
        this.colorists = colorists;
    }

    public ObjectId getId() {
        return _Id;
    }

    public void setId(ObjectId _Id) {
        this._Id = _Id;
    }

    public String getHeroNameCollab() {
        return heroNameCollab;
    }

    public void setHeroNameCollab(String heroNameCollab) {
        this.heroNameCollab = heroNameCollab;
    }

    public String getLastSyncCollab() {
        return lastSyncCollab;
    }

    public void setLastSyncCollab(String lastSyncCollab) {
        this.lastSyncCollab = lastSyncCollab;
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
                ", lastSync='" + lastSyncCollab + '\'' +
                ", writers=" + writers +
                ", editors=" + editors +
                ", colorists=" + colorists +
                '}';
    }
}
