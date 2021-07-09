package marvel.albo.erirodri.model;

import marvel.albo.erirodri.dto.Collaborator;

import java.util.List;

public class ResultsResponseTemplate {

    private int id;
    private String title;
    private List<CreatorTemplate> creators;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CreatorTemplate> getCreators() {
        return creators;
    }

    public void setCreators(List<CreatorTemplate> creators) {
        this.creators = creators;
    }

    private class CreatorTemplate {
        private List<Collaborator> items;

        public List<Collaborator> getItems() {
            return items;
        }

        public void setItems(List<Collaborator> items) {
            this.items = items;
        }
    }

}
