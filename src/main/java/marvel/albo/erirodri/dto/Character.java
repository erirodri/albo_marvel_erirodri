package marvel.albo.erirodri.dto;

import java.util.List;
import java.util.Map;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version
 * @category DTO
 *
 * Dto Character
 * --------------------------------------------------------------
 */

public class Character {

    private int id;
    private String name;
    private int comicsNumber;

    public Character() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComicsNumber() {
        return comicsNumber;
    }

    public void setComicsNumber(int comicsNumber) {
        this.comicsNumber = comicsNumber;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comicsNumber=" + comicsNumber +
                '}';
    }
}
