package marvel.albo.erirodri;

import marvel.albo.erirodri.dao.MongoDAOCharacter;
import marvel.albo.erirodri.dao.MongoDAOCollaborator;
import marvel.albo.erirodri.dto.Character;
import marvel.albo.erirodri.dto.Collaborator;
import marvel.albo.erirodri.model.Characters;
import marvel.albo.erirodri.model.Collaborators;
import marvel.albo.erirodri.model.DataResponseTemplate;
import marvel.albo.erirodri.model.MarvelApiResponseTemplate;
import marvel.albo.erirodri.service.MarvelApiConnectionImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureDataMongo
class ErirodriApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(ErirodriApplicationTests.class);

	private String url;

	@InjectMocks
	@Autowired
	private MarvelApiConnectionImpl marvelApiConnection;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpStatusCodeException httpError;

	@Autowired
	private MongoDAOCharacter mongoDAOCharacter;
	@Autowired
	private MongoDAOCollaborator mongoDAOCollaborator;

	ErirodriApplicationTests() {
	}


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getCharacterInfoTest(){
		Character character =marvelApiConnection.getCharacterInfo("name=Iron Man");
		MarvelApiResponseTemplate marvelApiResponseTemplate = new MarvelApiResponseTemplate();
		marvelApiResponseTemplate.setCode(200);
		marvelApiResponseTemplate.getCode();
		marvelApiResponseTemplate.setStatus("OK");
		marvelApiResponseTemplate.getStatus();
		marvelApiResponseTemplate.setCopyright("");
		marvelApiResponseTemplate.getCopyright();
		marvelApiResponseTemplate.setAttributionHTML("");
		marvelApiResponseTemplate.getAttributionHTML();
		marvelApiResponseTemplate.setAttributionText("");
		marvelApiResponseTemplate.getAttributionText();
		marvelApiResponseTemplate.setEtag("");
		marvelApiResponseTemplate.getEtag();
		DataResponseTemplate dataResponseTemplate = new DataResponseTemplate();
		dataResponseTemplate.setCount(10);
		dataResponseTemplate.getCount();
		dataResponseTemplate.setLimit(50);
		dataResponseTemplate.getLimit();
		dataResponseTemplate.setTotal(100);
		dataResponseTemplate.getTotal();
		dataResponseTemplate.setOffset(50);
		dataResponseTemplate.getOffset();
		dataResponseTemplate.setResults(new ArrayList<>());
		marvelApiResponseTemplate.setData(dataResponseTemplate);
		url =  this.generateURL("characters?name=Iron Man&");
		Mockito.when(restTemplate.getForObject(url, MarvelApiResponseTemplate.class)).thenReturn(marvelApiResponseTemplate);
		LOG.info(character.toString());
		Assert.assertNotNull(character);
	}

	@Test
	void getCollaboratorsByCharacterTest(){
		Character character = new Character();
		character.setName("Iron Man");
		character.setId(1009368);
		character.setComicsNumber(100);
		Assert.assertNotNull(marvelApiConnection.getCollaboratorsByCharacter(character));

	}

	@Test
	void orderCollaboratorsByRoleTest(){
		Collaborator coll1 = new Collaborator();
		coll1.setCollaborator("prueba1","writer");
		Collaborator coll2 = new Collaborator();
		coll2.setRole("colorist");
		coll2.setName("prueba2");
		Collaborator coll3 = new Collaborator();
		coll3.setCollaborator("prueba1","editor");
		List<Collaborator> listCollab = new ArrayList<>();
		listCollab.add(coll1);
		listCollab.add(coll2);
		listCollab.add(coll3);
		Assert.assertNotNull(marvelApiConnection.orderCollaboratorsByRole(listCollab));

	}

	@Test
	void getCharactersByComicTest(){
		Character hero = new Character();
		hero.setId(1009368);
		hero.setComicsNumber(150);
		hero.setName("Iron Man");
		Assert.assertNotNull(marvelApiConnection.getCharactersByComic(hero));

	}

	@Test
	void sendCollaboratorsResultToDataBaseTest(){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("lastSync","14/07/2021 14:08:00");
		linkedHashMap.put("writers","");
		linkedHashMap.put("editors","");
		linkedHashMap.put("colorists","");
		marvelApiConnection.sendCollaboratorsResultToDataBase(linkedHashMap,"Iron Man");
		Assert.assertNotNull(mongoDAOCollaborator.findAll());

	}

	@Test
	void sendCollaboratorsResultToDataBaseHeroNotEmptyTest(){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("lastSync","14/07/2021 14:08:00");
		linkedHashMap.put("writers","");
		linkedHashMap.put("editors","");
		linkedHashMap.put("colorists","");
		Collaborators collaborators = new Collaborators();
		collaborators.setHeroNameCollab("Iron Man");
		collaborators.setLastSyncCollab("14/07/21 12:00:00");
		collaborators.setEditors("");
		collaborators.setWriters("");
		collaborators.setColorists("");
		mongoDAOCollaborator.save(collaborators);
		marvelApiConnection.sendCollaboratorsResultToDataBase(linkedHashMap,"Captain America");
		Assert.assertNotNull(mongoDAOCollaborator.findAll());

	}

	@Test
	void sendCollaboratorsResultToDataBaseHeroExistTest(){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("lastSync","14/07/2021 14:08:00");
		linkedHashMap.put("writers","");
		linkedHashMap.put("editors","");
		linkedHashMap.put("colorists","");
		mongoDAOCollaborator.deleteAll();
		Collaborators collaborators = new Collaborators("Iron Man","","","","");
		mongoDAOCollaborator.save(collaborators);
		marvelApiConnection.sendCollaboratorsResultToDataBase(linkedHashMap,"Iron Man");
		Assert.assertNotNull(mongoDAOCollaborator.findAll());

	}

	@Test
	void sendCharactersResultToDataBaseTest(){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("lastSync","14/07/2021 14:08:00");
		linkedHashMap.put("Characters","");
		marvelApiConnection.sendCharactersResultToDataBase(linkedHashMap,"Iron Man");
		Assert.assertNotNull(mongoDAOCollaborator.findAll());

	}

	@Test
	void sendCharactersResultToDataBaseNotEmptyTest(){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("lastSync","14/07/2021 14:08:00");
		linkedHashMap.put("Characters","");
		Characters characters = new Characters();
		characters.setHeroNameCharact("Captain America");
		characters.setLastSyncCharact("14/07/2021 14:08:00");
		characters.setCharacters("");
		mongoDAOCharacter.save(characters);
		marvelApiConnection.sendCharactersResultToDataBase(linkedHashMap,"Iron Man");
		Assert.assertNotNull(mongoDAOCollaborator.findAll());

	}

	@Test
	void sendCharactersResultToDataBaseExistTest(){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("lastSync","14/07/2021 14:08:00");
		linkedHashMap.put("Characters","");
		mongoDAOCharacter.deleteAll();
		Characters characters = new Characters("Iron Man","14/07/2021 14:08:00","");
		mongoDAOCharacter.save(characters);
		marvelApiConnection.sendCharactersResultToDataBase(linkedHashMap,"Iron Man");
		Assert.assertNotNull(mongoDAOCollaborator.findAll());

	}

	private String generateURL(String elementToFind){
		String url="https://gateway.marvel.com/v1/public/"
				.concat(elementToFind)
				.concat("apikey=01eabd58fd6a7b196357c21ef331c5dc&")
				.concat("ts=1&")
				.concat("hash=61ab7316d382840a0c79f8e7129071c9");
		return url;
	}

}
