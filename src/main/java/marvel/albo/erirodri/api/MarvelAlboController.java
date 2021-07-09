package marvel.albo.erirodri.api;

import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.service.MarvelApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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
    public ResponseEntity<Map<String,Object>> getCollaboratorsIronMan(){
        Map<String,Object> response = new HashMap<>();
        Character ironMan = marvelApiConnection.getCharacterInfo("name=Iron Man");
        marvelApiConnection.getComicsInfo(ironMan);
        return new ResponseEntity<>(response,headerHttp, HttpStatus.OK);
    }

    @GetMapping("/colaborators/capamerica")
    public ResponseEntity<Map<String,Object>> getCollaboratorsCapAmerica(){
        Map<String,Object> response = new HashMap<>();

        return new ResponseEntity<>(response,headerHttp, HttpStatus.OK);
    }

}
