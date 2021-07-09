package marvel.albo.erirodri.dto;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version
 * @category DTO
 *
 * Dto Collaborator
 * --------------------------------------------------------------
 */
public class Collaborator {

    private int idCollaboratorDto;
    private String fullNameCollaboratorDto;

    public Collaborator() {
    }

    public int getIdCollaboratorDto() {
        return idCollaboratorDto;
    }

    public void setIdCollaboratorDto(int idCollaboratorDto) {
        this.idCollaboratorDto = idCollaboratorDto;
    }

    public String getFullNameCollaboratorDto() {
        return fullNameCollaboratorDto;
    }

    public void setFullNameCollaboratorDto(String fullNameCollaboratorDto) {
        this.fullNameCollaboratorDto = fullNameCollaboratorDto;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "idCollaboratorDto=" + idCollaboratorDto +
                ", fullNameCollaboratorDto='" + fullNameCollaboratorDto + '\'' +
                '}';
    }
}
