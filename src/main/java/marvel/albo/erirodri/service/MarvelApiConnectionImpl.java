package marvel.albo.erirodri.service;

import com.mongodb.MongoException;
import marvel.albo.erirodri.configuration.EnvVariables;
import marvel.albo.erirodri.dao.MongoDAOCharacter;
import marvel.albo.erirodri.dao.MongoDAOCollaborator;
import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.dto.Comic;
import marvel.albo.erirodri.model.Characters;
import marvel.albo.erirodri.model.Collaborators;
import marvel.albo.erirodri.model.DataResponseTemplate;
import marvel.albo.erirodri.model.MarvelApiResponseTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * ---------------------------------------------------------------
 * @author Erick Rodriguez Morales
 * @version 1.0.0
 * @category Service
 *
 * Service to develop to business logical
 * --------------------------------------------------------------
 */
@Service
public class MarvelApiConnectionImpl implements MarvelApiConnection{

    private static final Logger LOG = LoggerFactory.getLogger(MarvelApiConnectionImpl.class);
    private final EnvVariables envVariables;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String LAST_SYNC="lastSync";
    private final MongoDAOCollaborator mongoDAOCollaborator;
    private final MongoDAOCharacter mongoDAOCharacter;


    private String url;

    @Autowired
    public MarvelApiConnectionImpl(EnvVariables envVariables, MongoDAOCollaborator mongoDAOCollaborator, MongoDAOCharacter mongoDAOCharacter) {
        this.envVariables = envVariables;
        this.mongoDAOCollaborator = mongoDAOCollaborator;
        this.mongoDAOCharacter = mongoDAOCharacter;
    }

    @Override
    public Character getCharacterInfo(String hero) throws HttpStatusCodeException{
        LOG.debug("getCharacterInfo STARTED :::::");

        Character heroValue = new Character();
        DataResponseTemplate data;
        url =  this.generateURL("characters?"+hero+"&");
        if(restTemplate.getForObject(url,MarvelApiResponseTemplate.class)!= null){
            MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
            data = jsonResponse.getData();
            heroValue.setId((Integer) data.getResults().get(0).get("id"));
            heroValue.setName((String) data.getResults().get(0).get("name"));
            Map<String,Object> comics = (Map<String, Object>) data.getResults().get(0).get("comics");
            heroValue.setComicsNumber((int) comics.get("available"));
        }

        LOG.debug("getCharacterInfo FINISHED :::::");
        return heroValue;

    }

    @Override
    public List<Collaborator>  getCollaboratorsByCharacter(Character hero) throws HttpStatusCodeException{
        LOG.debug("getComicsInfo STARTED :::::");
        List<Collaborator> collaboratorsFiltered = new ArrayList<>();
        List<Collaborator> collaboratorList = new ArrayList<>();
        int restantes=hero.getComicsNumber();
        int offset = 0;
        do{
            DataResponseTemplate data = this.getComicsByPage(hero.getId(), offset);
            collaboratorList.addAll(this.getInfoCreatorsByComic(data));
            collaboratorsFiltered.addAll(collaboratorList.stream().filter(distinctByKey(dto -> dto.getName() + "" + dto.getRole())).collect(Collectors.toList()));
            offset += 50;
            restantes -=50;
        }while(restantes>0);
        LOG.debug("getComicsInfo FINISHED :::::");
        return collaboratorsFiltered;
    }

    @Override
    public LinkedHashMap<String,Object> getCharactersByComic(Character hero) {
        LOG.debug("getCharactersByComic STARTED :::::");
        LinkedHashMap<String,Object> result = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String lastSyncFormat = FORMATTER.format(now);
        List<Comic> comicsList;
        List<Comic> comicListTotal=  new ArrayList<>();
        int restantes=hero.getComicsNumber();
        int offset = 0;
        do{
            DataResponseTemplate data = this.getComicsByPage(hero.getId(), offset);
            comicsList= this.getInfoCharactersByComic(data,hero.getName());
            if(offset==0){
                comicListTotal.addAll(comicsList);
            }else{
                for (Comic comic:comicsList ) {
                    if(comicListTotal.stream().filter(val -> val.getCharacter().equalsIgnoreCase(comic.getCharacter())).findFirst().isPresent()){
                        Comic comicExist = comicListTotal.stream().filter(val -> val.getCharacter().equalsIgnoreCase(comic.getCharacter())).findFirst().get();
                        int i = comicListTotal.indexOf(comicExist);
                        List<String> comic1 = new ArrayList<>(comicListTotal.get(i).getComics());
                        comic1.addAll(comic.getComics());
                        comic.setComics(comic1);
                        comicListTotal.set(i,comic);
                    }else{
                        comicListTotal.add(comic);
                    }
                }
            }
            offset += 50;
            restantes -=50;
        }while (restantes>0);

        result.put(LAST_SYNC, lastSyncFormat);
        result.put("Characters",comicListTotal);
        LOG.debug("getCharactersByComic FINISHED :::::");
        return result;
    }

    @Override
    public void sendCollaboratorsResultToDataBase(LinkedHashMap<String,Object> infoToSave, String heroName) throws MongoException {
        LOG.debug(":::: sendCollaboratorsResultToDataBase STARTED ::::");
        List<Collaborators> collaboratorsExist;
        Collaborators collaborators = new Collaborators(
                heroName,
                (String) infoToSave.get(LAST_SYNC),
                infoToSave.get("writers"),
                infoToSave.get("editors"),
                infoToSave.get("colorists"));
        collaboratorsExist=mongoDAOCollaborator.findAll();
        if(!(collaboratorsExist.isEmpty())){
            for (Collaborators collab: collaboratorsExist) {
                if(collab.getHeroNameCollab().equalsIgnoreCase(heroName)){
                    mongoDAOCollaborator.delete(collab);
                    collaborators.setId(collab.getId());
                    mongoDAOCollaborator.save(collaborators);
                }
            }
        }
        if(collaboratorsExist.size()<2){
            mongoDAOCollaborator.save(collaborators);
        }

        LOG.debug(":::: sendCollaboratorsResultToDataBase FINISHED ::::");
    }

    @Override
    public void sendCharactersResultToDataBase(LinkedHashMap<String,Object> infoToSave, String heroName) throws MongoException {
        LOG.debug(":::: sendCharactersResultToDataBase STARTED ::::");
        List<Characters> charactersExist;
        Characters characters = new Characters(
                heroName,
                (String) infoToSave.get(LAST_SYNC),
                infoToSave.get("Characters")
        );
        charactersExist=mongoDAOCharacter.findAll();
        if(!charactersExist.isEmpty()){
            for (Characters charac: charactersExist) {
                if(charac.getHeroNameCharact().equalsIgnoreCase(heroName)){
                    mongoDAOCharacter.delete(charac);
                    characters.setId(charac.getId());
                    mongoDAOCharacter.save(characters);
                }
            }
        }
        if(charactersExist.size()<2){
            mongoDAOCharacter.save(characters);
        }

        LOG.debug(":::: sendCharactersResultToDataBase FINISHED ::::");
    }

    @Override
    public LinkedHashMap<String,Object> orderCollaboratorsByRole(List list) {
        LOG.debug(":::: orderCollaboratorsByRole STARTED ::::");
        List<Collaborator> collaboratorList = list;
        LinkedHashMap<String,Object> result = new LinkedHashMap<>();
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
                default:
                    break;
            }
        }

        LocalDateTime now = LocalDateTime.now();
        String lastSyncFormat = FORMATTER.format(now);
        result.put(LAST_SYNC, lastSyncFormat);
        result.put("writers",writers);
        result.put("editors",editors);
        result.put("colorists",drawers);

        LOG.debug(":::: orderCollaboratorsByRole FINISHED ::::");
        return result;
    }


    private DataResponseTemplate getComicsByPage(int idHero, int offset) throws HttpStatusCodeException{
        url = this.generateURL("characters/"+idHero+"/comics?offset="+offset+"&limit=50&");
        DataResponseTemplate data = new DataResponseTemplate();
        if (restTemplate.getForObject(url,MarvelApiResponseTemplate.class) != null) {
            MarvelApiResponseTemplate jsonResponse = restTemplate.getForObject(url,MarvelApiResponseTemplate.class);
            data = jsonResponse.getData();
        }



        return data;
    }

    private List<Collaborator> getInfoCreatorsByComic(DataResponseTemplate data){
        List<Collaborator> collaboratorList = new ArrayList<>();
        for(int resultNumb=0;resultNumb<data.getResults().size();resultNumb++){
            Map<String,Object> creators = (Map<String, Object>) data.getResults().get(resultNumb).get("creators");
            List<Map<String,Object>> collaboratorToSearch = (List<Map<String,Object>>) creators.get("items");
            for (Map<String,Object> collaborator : collaboratorToSearch) {
                Collaborator collaboratorDto = new Collaborator();
                collaboratorDto.setCollaborator(collaborator.get("name").toString(),collaborator.get("role").toString());
                collaboratorList.add(collaboratorDto);
            }
        }
        return collaboratorList;
    }

    private List<Comic> getInfoCharactersByComic(DataResponseTemplate data, String heroName){
        List<Comic> charactersList = new ArrayList<>();
        for(int resultNumb=0;resultNumb<data.getResults().size();resultNumb++){
            String comicName = (String) data.getResults().get(resultNumb).get("title");
            Map<String,Object> creators = (Map<String, Object>) data.getResults().get(resultNumb).get("characters");
            List<Map<String,Object>> characterToSearch = (List<Map<String,Object>>) creators.get("items");

            for (Map<String,Object> character: characterToSearch) {
                String nameHero = (String) character.get("name");
                if(!nameHero.equals(heroName)){
                    if(charactersList.isEmpty()){
                        Comic charactersComic = new Comic();
                        charactersComic.setCharacter(nameHero);
                        charactersComic.setComics(Collections.singletonList(comicName));

                        charactersList.add(charactersComic);
                    }else{
                        if(charactersList.stream().filter(value -> value.getCharacter().equalsIgnoreCase(nameHero)).findFirst().isPresent()){
                            Comic comicExist = charactersList.stream().filter(value -> value.getCharacter().equalsIgnoreCase(nameHero)).findFirst().get();
                            int i = charactersList.indexOf(comicExist);
                            List<String> value = new ArrayList<>(comicExist.getComics());
                            value.add(comicName);
                            comicExist.setComics(value);
                            charactersList.set(i,comicExist);
                        }else{
                            Comic charactersComic = new Comic();
                            charactersComic.setCharacter(nameHero);
                            charactersComic.setComics(Collections.singletonList(comicName));
                            charactersList.add(charactersComic);
                        }
                    }
                }
            }
        }

        return charactersList;
    }





    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
        return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }



    private String generateURL(String elementToFind){
        return envVariables.getGateway()
                .concat(elementToFind)
                .concat(envVariables.getApiKey()).concat("&")
                .concat(envVariables.getTs()).concat("&")
                .concat(envVariables.getHash());
    }


}
