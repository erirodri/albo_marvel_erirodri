package marvel.albo.erirodri.dto;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version
 * @category DTO
 *
 * Dto Comic
 * --------------------------------------------------------------
 */
public class Comic {

    private int idComicDto;
    private String nameComicDto;

    public Comic() {
    }

    public int getIdComicDto() {
        return idComicDto;
    }

    public void setIdComicDto(int idComicDto) {
        this.idComicDto = idComicDto;
    }

    public String getNameComicDto() {
        return nameComicDto;
    }

    public void setNameComicDto(String nameComicDto) {
        this.nameComicDto = nameComicDto;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "idComicDto=" + idComicDto +
                ", nameComicDto='" + nameComicDto + '\'' +
                '}';
    }
}
