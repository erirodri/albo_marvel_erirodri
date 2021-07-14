package marvel.albo.erirodri.service;

import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.dto.Comic;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface MarvelApiConnection<T, ID extends Serializable> {
    Character getCharacterInfo(String hero);
    List<Collaborator> getCollaboratorsByCharacter(Character hero);
    LinkedHashMap orderCollaboratorsByRole(List<Collaborator> collaboratorList);
    LinkedHashMap getCharactersByComic(Character hero);
}
