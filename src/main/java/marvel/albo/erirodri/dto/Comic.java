package marvel.albo.erirodri.dto;

import java.util.List;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category DTO
 *
 * Dto Comic
 * --------------------------------------------------------------
 */
public class Comic {

    private String character;
    private List<String> comics;


    public Comic() {
        // Constructor ...
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public List<String> getComics() {
        return comics;
    }

    public void setComics(List<String> comic) {
        this.comics = comic;
    }

    @Override
    public String toString() {
        return "character='" + character +
                ", comic=" + comics + '\'';
    }
}
