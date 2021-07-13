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

    private String name;
    private String role;

    public Collaborator() {
    }

    public void setCollaborator(String name, String role){
        this.name=name;
        this.role=role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
