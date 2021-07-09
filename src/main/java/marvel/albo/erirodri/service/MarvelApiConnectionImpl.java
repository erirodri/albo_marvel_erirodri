package marvel.albo.erirodri.service;

import marvel.albo.erirodri.configuration.EnvVariables;
import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.dto.Comic;
import marvel.albo.erirodri.model.DataResponseTemplate;
import marvel.albo.erirodri.model.MarvelApiResponseTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MarvelApiConnectionImpl implements MarvelApiConnection{

    private static final Logger log = LoggerFactory.getLogger(MarvelApiConnectionImpl.class);
    private final EnvVariables envVariables;
    private final RestTemplate restTemplate = new RestTemplate();
    private String url;

    @Autowired
    public MarvelApiConnectionImpl(EnvVariables envVariables) {
        this.envVariables = envVariables;
    }

    @Override
    public Character getCharacterInfo(String hero) {
        log.info("getCharacterInfo STARTED :::::");

        Character ironMan = new Character();

        url =  this.generateURL("characters?name=Iron Man&");
        MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
        //log.info("RESPONSE: "+ quote.toString());
        DataResponseTemplate data = jsonResponse.getData();
        //log.info("DATA: "+data.getResults().get(0).get("name"));
        ironMan.setId((Integer) data.getResults().get(0).get("id"));
        ironMan.setName((String) data.getResults().get(0).get("name"));
        Map<String,Object> comics = (Map<String, Object>) data.getResults().get(0).get("comics");
        ironMan.setComicsNumber((int) comics.get("available"));
        return ironMan;
    }

    @Override
    public List<Comic> getComicsInfo(Character hero) {
        log.info("getComicsInfo STARTED :::::");
        url = this.generateURL("characters/"+hero.getId()+"/comics?");
        if(hero.getComicsNumber()<20){
            MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
            Map<String,Object> data = (Map<String, Object>) jsonResponse.getData();
            List<Map<String,Object>> results = (List<Map<String, Object>>) data.get("results");
        }else{
            MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
            List<Map<String,Object>> results = jsonResponse.getData().getResults();
            List<Collection<Object>> ids = results.stream().filter(map -> map.containsKey("id")).map(Map::values).collect(Collectors.toList());
            log.info("ids: "+ids);
            ids.forEach(x -> log.info("ID: "+x));

        }
        log.info("getComicsInfo FINISHED :::::");
        return null;
    }

    @Override
    public List<Character> getCharactersByComic(Comic comic) {
        log.info("getCharactersByComic STARTED :::::");


        log.info("getCharactersByComic FINISHED :::::");
        return null;
    }

    @Override
    public List<Collaborator> getCollaboratorsByComic(Comic comic) {
        log.info("getCollaboratorsByComic STARTED :::::");


        log.info("getCollaboratorsByComic FINISHED :::::");
        return null;
    }

    private String generateURL(String elementToFind){
        String url=envVariables.getGateway()
                .concat(elementToFind)
                .concat(envVariables.getApiKey()).concat("&")
                .concat(envVariables.getTs()).concat("&")
                .concat(envVariables.getHash());
        return url;
    }


}
