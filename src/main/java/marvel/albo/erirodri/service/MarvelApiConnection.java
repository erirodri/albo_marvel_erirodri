package marvel.albo.erirodri.service;

import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.dto.Comic;

import java.io.Serializable;
import java.util.List;

public interface MarvelApiConnection<T, ID extends Serializable> {
    Character getCharacterInfo(String hero);
    List<Comic> getComicsInfo(Character hero);
    List<Character> getCharactersByComic(Comic comic);
    List<Collaborator> getCollaboratorsByComic(Comic comic);


}
