package marvel.albo.erirodri.api;

import com.mongodb.MongoException;
import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
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
    private static final Logger LOG = LoggerFactory.getLogger(MarvelAlboController.class);
    private static final String IRON_MAN="Iron Man";
    private static final String CAP_AMER="Captain America";
    private static final String IRON_MAN_URL="name=".concat(IRON_MAN);
    private static final String CAP_AMER_URL="name=".concat(CAP_AMER);
    private LinkedHashMap lhm = new LinkedHashMap();


    private final MarvelApiConnection marvelApiConnection;

    public MarvelAlboController(MarvelApiConnection marvelApiConnection) {
        this.marvelApiConnection = marvelApiConnection;
    }

    @GetMapping("/colaborators/ironman")
    public ResponseEntity<LinkedHashMap> getCollaboratorsIronMan(){
        LOG.info("*:*:* GET_COLLABORATORS_IRON_MAN -- STARTED *:*:* ");

        Character ironMan = marvelApiConnection.getCharacterInfo(IRON_MAN_URL);
        List<Collaborator> collaboratorList = marvelApiConnection.getCollaboratorsByCharacter(ironMan);
        lhm = marvelApiConnection.orderCollaboratorsByRole(collaboratorList);
        try{
            marvelApiConnection.sendCollaboratorsResultToDataBase(lhm,IRON_MAN);
        }catch (MongoException ex){
            lhm.put("ERROR MESSAGE: ", ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOG.info("*:*:* GET_COLLABORATORS_IRON_MAN -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

    @GetMapping("/colaborators/capamerica")
    public ResponseEntity<LinkedHashMap> getCollaboratorsCapAmerica(){
        LOG.info("*:*:* GET_COLLABORATORS_CAP_AME -- STARTED *:*:* ");

        Character ironMan = marvelApiConnection.getCharacterInfo(CAP_AMER_URL);
        List<Collaborator> collaboratorList = marvelApiConnection.getCollaboratorsByCharacter(ironMan);
        lhm = marvelApiConnection.orderCollaboratorsByRole(collaboratorList);
        try{
            marvelApiConnection.sendCollaboratorsResultToDataBase(lhm,CAP_AMER);
        }catch (MongoException ex){
            lhm.put("ERROR MESSAGE: ", ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOG.info("*:*:* GET_COLLABORATORS_CAP_AME -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

    @GetMapping("/characters/ironman")
    public ResponseEntity<LinkedHashMap> getCharactersIronMan(){
        LOG.info("*:*:* GET_CHARACTERS_IRON_MAN -- STARTED *:*:* ");

        Character ironMan = marvelApiConnection.getCharacterInfo(IRON_MAN_URL);
        lhm = marvelApiConnection.getCharactersByComic(ironMan);
        try{
            marvelApiConnection.sendCharactersResultToDataBase(lhm,IRON_MAN);
        }catch (MongoException ex){
            lhm.put("ERROR MESSAGE: ", ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOG.info("*:*:* GET_CHARACTERS_IRON_MAN -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

    @GetMapping("/characters/capamerica")
    public ResponseEntity<LinkedHashMap> getCharactersCamAmerica(){
        LOG.info("*:*:* GET_CHARACTERS_CAP_AME -- STARTED *:*:* ");

        Character capAmerica = marvelApiConnection.getCharacterInfo(CAP_AMER_URL);
        lhm = marvelApiConnection.getCharactersByComic(capAmerica);
        try{
            marvelApiConnection.sendCharactersResultToDataBase(lhm,CAP_AMER);
        }catch (MongoException ex){
            lhm.put("ERROR MESSAGE: ", ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOG.info("*:*:* GET_CHARACTERS_CAP_AME -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

}
