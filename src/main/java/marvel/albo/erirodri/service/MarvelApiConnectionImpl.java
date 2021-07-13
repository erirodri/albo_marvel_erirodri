package marvel.albo.erirodri.service;

import marvel.albo.erirodri.configuration.EnvVariables;
import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.model.DataResponseTemplate;
import marvel.albo.erirodri.model.MarvelApiResponseTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MarvelApiConnectionImpl implements MarvelApiConnection{

    private static final Logger log = LoggerFactory.getLogger(MarvelApiConnectionImpl.class);
    private final EnvVariables envVariables;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String formattedString = zonedDateTimeNow.format(formatter);

    private String url;

    @Autowired
    public MarvelApiConnectionImpl(EnvVariables envVariables) {
        this.envVariables = envVariables;
    }

    @Override
    public Character getCharacterInfo(String hero) {
        log.info("getCharacterInfo STARTED :::::");

        Character heroValue = new Character();

        url =  this.generateURL("characters?"+hero+"&");
        MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
        DataResponseTemplate data = jsonResponse.getData();
        heroValue.setId((Integer) data.getResults().get(0).get("id"));
        heroValue.setName((String) data.getResults().get(0).get("name"));
        Map<String,Object> comics = (Map<String, Object>) data.getResults().get(0).get("comics");
        heroValue.setComicsNumber((int) comics.get("available"));
        log.info("# Comics: "+heroValue.getComicsNumber());
        log.info("getCharacterInfo FINISHED :::::");
        return heroValue;
    }

    @Override
    public List<Collaborator>  getCollaboratorsByCharacter(Character hero) {
        log.info("getComicsInfo STARTED :::::");
        List<Collaborator> collaboratorsFiltered;
        List<Collaborator> collaboratorList = new ArrayList<>();
        log.info("Comics to Search: "+hero.getComicsNumber());
        int restantes=hero.getComicsNumber();
        int offset = 0;
         do{
            log.info("AL INICIO:: offset #:" + offset + " Comics Restantes: " + restantes);
            collaboratorList.addAll(this.getComicsByPage(hero.getId(), offset));
            collaboratorsFiltered = collaboratorList.stream().filter(distinctByKey(dto -> dto.getName() + "" + dto.getRole())).collect(Collectors.toList());
            offset += 50;
            restantes -=50;
            log.info("AL FINAL:: offset #:" + offset + " Comics Restantes: " + restantes);
        }while(restantes>0);
        log.info("getComicsInfo FINISHED :::::");
        return collaboratorsFiltered;
    }

    @Override
    public LinkedHashMap orderCollaboratorsByRole(List list) {
        List<Collaborator> collaboratorList = list;
        LinkedHashMap result = new LinkedHashMap();
        List<String> editors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> drawers = new ArrayList<>();
        for (Collaborator collaborator: collaboratorList) {
            switch (collaborator.getRole()){
                case "writer":
                    writers.add(collaborator.getName());
                    break;
                case "colorist":
                    drawers.add(collaborator.getName());
                    break;
                case "editor":
                    editors.add(collaborator.getName());
                    break;
            }
        }

        result.put("lastSync",formattedString);
        result.put("writers",writers);
        log.info("writers: "+writers.size());
        result.put("editors",editors);
        log.info("editors: "+editors.size());
        result.put("colorists",drawers);
        log.info("drawers: "+drawers.size());

        return result;
    }


    private List<Collaborator> getComicsByPage(int idHero, int offset){
        List<Collaborator> collaborators = new ArrayList<>();
        url = this.generateURL("characters/"+idHero+"/comics?offset="+offset+"&limit=50&");
        MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
        DataResponseTemplate data = jsonResponse.getData();

        for(int resultNumb=0;resultNumb<data.getResults().size();resultNumb++){
            Map<String,Object> creators = (Map<String, Object>) data.getResults().get(resultNumb).get("creators");
            List<Map<String,Object>> collaboratorToSearch = (List<Map<String,Object>>) creators.get("items");
            for (Map<String,Object> collaborator : collaboratorToSearch) {
                Collaborator collaboratorDto = new Collaborator();
                collaboratorDto.setCollaborator(collaborator.get("name").toString(),collaborator.get("role").toString());
                collaborators.add(collaboratorDto);
            }
        }

        return collaborators;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
        return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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
