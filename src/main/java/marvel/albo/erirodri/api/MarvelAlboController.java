package marvel.albo.erirodri.api;

import com.mongodb.MongoException;
import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.service.MarvelApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;


/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
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
    private static final String ERROR_MESSAGE="ERROR MESSAGE";
    private static final String ERROR_CODE="ERROR CODE";
    private static final String CAUSE="CAUSE";

    private LinkedHashMap<String,Object> lhm = new LinkedHashMap<>();


    private final MarvelApiConnection marvelApiConnection;

    @Autowired
    public MarvelAlboController(MarvelApiConnection marvelApiConnection) {
        this.marvelApiConnection = marvelApiConnection;
    }

    @GetMapping("/colaborators/ironman")
    public ResponseEntity<LinkedHashMap<String,Object>> getCollaboratorsIronMan(){
        LOG.debug(" ");
        LOG.debug("*:*:* GET_COLLABORATORS_IRON_MAN -- STARTED *:*:* ");


        try{
            Character ironMan = marvelApiConnection.getCharacterInfo(IRON_MAN_URL);
            List<Collaborator> collaboratorList = marvelApiConnection.getCollaboratorsByCharacter(ironMan);
            lhm = marvelApiConnection.orderCollaboratorsByRole(collaboratorList);
            marvelApiConnection.sendCollaboratorsResultToDataBase(lhm,IRON_MAN);
        }catch (MongoException ex){
            lhm.put(ERROR_MESSAGE, ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (HttpStatusCodeException ex){
            lhm.put(ERROR_CODE, ex.getStatusCode().value());
            lhm.put(CAUSE,ex.getResponseBodyAsString());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.FAILED_DEPENDENCY);
        }
        LOG.debug("*:*:* GET_COLLABORATORS_IRON_MAN -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

    @GetMapping("/colaborators/capamerica")
    public ResponseEntity<LinkedHashMap<String,Object>> getCollaboratorsCapAmerica(){
        LOG.debug(" ");
        LOG.debug("*:*:* GET_COLLABORATORS_CAP_AME -- STARTED *:*:* ");

        try{
            Character ironMan = marvelApiConnection.getCharacterInfo(CAP_AMER_URL);
            List<Collaborator> collaboratorList = marvelApiConnection.getCollaboratorsByCharacter(ironMan);
            lhm = marvelApiConnection.orderCollaboratorsByRole(collaboratorList);
            marvelApiConnection.sendCollaboratorsResultToDataBase(lhm,CAP_AMER);
        }catch (MongoException ex){
            lhm.put(ERROR_MESSAGE, ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (HttpStatusCodeException ex){
            lhm.put(ERROR_CODE, ex.getStatusCode().value());
            lhm.put(CAUSE,ex.getResponseBodyAsString());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.FAILED_DEPENDENCY);
        }
        LOG.debug("*:*:* GET_COLLABORATORS_CAP_AME -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

    @GetMapping("/characters/ironman")
    public ResponseEntity<LinkedHashMap<String,Object>> getCharactersIronMan(){
        LOG.debug(" ");
        LOG.debug("*:*:* GET_CHARACTERS_IRON_MAN -- STARTED *:*:* ");

        try{
            Character ironMan = marvelApiConnection.getCharacterInfo(IRON_MAN_URL);
            lhm = marvelApiConnection.getCharactersByComic(ironMan);
            marvelApiConnection.sendCharactersResultToDataBase(lhm,IRON_MAN);
        }catch (MongoException ex){
            lhm.put(ERROR_MESSAGE, ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (HttpStatusCodeException ex){
            lhm.put(ERROR_CODE, ex.getStatusCode().value());
            lhm.put(CAUSE,ex.getResponseBodyAsString());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.FAILED_DEPENDENCY);
        }
        LOG.debug("*:*:* GET_CHARACTERS_IRON_MAN -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

    @GetMapping("/characters/capamerica")
    public ResponseEntity<LinkedHashMap<String,Object>> getCharactersCamAmerica(){
        LOG.debug(" ");
        LOG.debug("*:*:* GET_CHARACTERS_CAP_AME -- STARTED *:*:* ");

        try{
            Character capAmerica = marvelApiConnection.getCharacterInfo(CAP_AMER_URL);
            lhm = marvelApiConnection.getCharactersByComic(capAmerica);
            marvelApiConnection.sendCharactersResultToDataBase(lhm,CAP_AMER);
        }catch (MongoException ex){
            lhm.put(ERROR_MESSAGE, ex.getMessage());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (HttpStatusCodeException ex){
            lhm.put(ERROR_CODE, ex.getStatusCode().value());
            lhm.put(CAUSE,ex.getResponseBodyAsString());
            return new ResponseEntity<>(lhm,headerHttp, HttpStatus.FAILED_DEPENDENCY);
        }
        LOG.debug("*:*:* GET_CHARACTERS_CAP_AME -- FINISHED *:*:* ");
        return new ResponseEntity<>(lhm,headerHttp, HttpStatus.OK);

    }

}
