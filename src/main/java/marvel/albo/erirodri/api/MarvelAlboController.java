package marvel.albo.erirodri.api;

import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.dto.Comic;
import marvel.albo.erirodri.service.MarvelApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version
 * @category Controller
 *
 * --------------------------------------------------------------
 */
@RestController
@RequestMapping(value ="/marvel")
public class MarvelAlboController {
    private static HttpHeaders headerHttp = new HttpHeaders();
    private static final Logger log = LoggerFactory.getLogger(MarvelAlboController.class);


    private final MarvelApiConnection marvelApiConnection;

    public MarvelAlboController(MarvelApiConnection marvelApiConnection) {
        this.marvelApiConnection = marvelApiConnection;
    }

    @GetMapping("/colaborators/ironman")
    public ResponseEntity<LinkedHashMap> getCollaboratorsIronMan(){
        Map<String,Object> response = new HashMap<>();
        LinkedHashMap lhm = new LinkedHashMap();

        Character ironMan = marvelApiConnection.getCharacterInfo("name=Iron Man");
        List<Collaborator> collaboratorList = marvelApiConnection.getCollaboratorsByCharacter(ironMan);
        lhm = marvelApiConnection.orderCollaboratorsByRole(collaboratorList);



        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);
    }

    @GetMapping("/colaborators/capamerica")
    public ResponseEntity<LinkedHashMap> getCollaboratorsCapAmerica(){
        Map<String,Object> response = new HashMap<>();
        LinkedHashMap lhm = new LinkedHashMap();

        Character ironMan = marvelApiConnection.getCharacterInfo("name=Captain America");
        List<Collaborator> collaboratorList = marvelApiConnection.getCollaboratorsByCharacter(ironMan);
        lhm = marvelApiConnection.orderCollaboratorsByRole(collaboratorList);

        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);
    }

    @GetMapping("/characters/ironman")
    public ResponseEntity<LinkedHashMap> getCharactersIronMan(){
        LinkedHashMap lhm = new LinkedHashMap();
        Character ironMan = marvelApiConnection.getCharacterInfo("name=Iron Man");
        lhm = marvelApiConnection.getCharactersByComic(ironMan);
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);
    }

    @GetMapping("/characters/capamerica")
    public ResponseEntity<LinkedHashMap> getCharactersCamAmerica(){
        LinkedHashMap lhm = new LinkedHashMap();
        Character ironMan = marvelApiConnection.getCharacterInfo("name=Captain America");
        lhm = marvelApiConnection.getCharactersByComic(ironMan);
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);
    }

}
