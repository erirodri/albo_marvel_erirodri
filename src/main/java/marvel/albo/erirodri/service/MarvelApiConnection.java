package marvel.albo.erirodri.service;

import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category Interface
 *
 * Interface to access to business logical
 * --------------------------------------------------------------
 */
public interface MarvelApiConnection {
    Character getCharacterInfo(String hero);
    List<Collaborator> getCollaboratorsByCharacter(Character hero);
    LinkedHashMap<String,Object> orderCollaboratorsByRole(List<Collaborator> collaboratorList);
    LinkedHashMap<String,Object> getCharactersByComic(Character hero);
    void sendCollaboratorsResultToDataBase(LinkedHashMap<String,Object> infoToSave, String heroName);
    void sendCharactersResultToDataBase(LinkedHashMap<String,Object> infoToSave, String heroName);
}
