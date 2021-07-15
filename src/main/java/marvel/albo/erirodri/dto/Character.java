package marvel.albo.erirodri.dto;


/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
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
        // Constructor ...
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

}
