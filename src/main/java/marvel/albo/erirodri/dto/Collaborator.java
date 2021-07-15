package marvel.albo.erirodri.dto;


/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category DTO
 *
 * Dto Collaborator
 * --------------------------------------------------------------
 */

public class Collaborator {

    private String name;
    private String role;

    public Collaborator() {
        // Constructor ...
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
