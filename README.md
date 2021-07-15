![Logo](https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Marvel_Logo.svg/1200px-Marvel_Logo.svg.png)


# MARVEL HEROES COMICS INFORMATION

API diseñada para realizar la actualización de los registros que corresponden a los colaboradores y héroes que han trabajo dentro de los comics donde aparecen los súper héroes: **Iron Man y Capitán América**


## Autor
- Erick Rodriguez Morales  <<< [@erirodri](https://github.com/erirodrie) >>>


## Documentación
La API en cuestión realiza la comunicación con APIs públicas de marvel (https://developer.marvel.com/docs) para así obtener los registros actualizados que corresponden a los siguientes rubros:
1. Escritores, dibujantes y editores que trabajaron en el desarrollo de **comics** donde aparecen los súper héroes: Iron Man y Capitán América
2. Héroes que compartieron historia con los súper héroes y sus correspondientes comics.




## Notas técnicas.
Para generar un desarrollo de mejor calidad, se implementó SonarQube para realizar una revisión del código y así realizar las mejoras pertinentes para estar más apegado a buenas prácticas de desarrollo. Dicho análisis podrá ser consultado en la siguiente liga:

* [Reporte SonarQube](https://drive.google.com/file/d/1Slmqq9Vno9eEWqyfeqXUG1sMTSG_a88P/view?usp=sharing)

Cabe mencionar que existen algunos “Code Smells” que hacen referencia al uso de var (java 10) pero por el momento se omitieron esos smells ya que no hay impacto en el funcionamiento de la aplicación, al igual que algunas asignaciones de clases.



## Detalles de API

#####  ** URI para obtener el listado de Colaboradores que han trabajado en los comics que corresponden a Capitán América

```http
  GET /marvel/colaborators/capamerica
```
##### ResponseBody
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `lastSync` | `String` | Fecha y Hora de la última actualización |
| `writers` | `Array` | Listado de todos los Escritores |
| `editors` | `Array` | Listado de todos los Editores |
| `colorists` | `Array>` | Listado de todos los Dibujantes |

#####  ** URI para obtener el listado de Colaboradores que han trabajado en los comics que corresponden a Iron Man

```http
  GET /marvel/colaborators/ironman
```
##### ResponseBody
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `lastSync` | `String` | Fecha y Hora de la última actualización |
| `writers` | `Array` | Listado de todos los Escritores |
| `editors` | `Array` | Listado de todos los Editores |
| `colorists` | `Array>` | Listado de todos los Dibujantes |

#####  ** URI para obtener el listado de Héroes que han participado en los comics que corresponden a Iron Man

```http
  GET /marvel/characters/ironman
```
##### ResponseBody
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `lastSync` | `String` | Fecha y Hora de la última actualización |
| `Characters` | `Array` | Listado de todos los súper héroes que compartieron comic con Iron Man |
| `character` | `String` | Nombre de Súper Héroe |
| `comics` | `List` | Listado de comics donde comparten créditos |

#####  ** URI para obtener el listado de Héroes que han participado en los comics que corresponden a Capitán América

```http
  GET /marvel/characters/capamerica
```
##### ResponseBody
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `lastSync` | `String` | Fecha y Hora de la última actualización |
| `Characters` | `Array` | Listado de todos los súper héroes que compartieron comic con Capitán América |
| `character` | `String` | Nombre de Súper Héroe |
| `comics` | `List` | Listado de comics donde comparten créditos |


## Instalación

1. Descargar el proyecto

```bash 
  git clone https://github.com/erirodri/meli-erirodri.git
  > cd meli-erirodri
```
2. Para poder ejecutar el proyecto es necesario contar con (link para instalar):
- [Maven](https://maven.apache.org/install.html)
- [SDK11](https://www.oracle.com/mx/java/technologies/javase-jdk11-downloads.html)

3. Ejecutar el archivo **assemble.sh** para compilar el componente
```bash 
  > /assemble.sh
```

3. Una vez compilado el proyecto, es necesario ejecutar el comando **avengers.sh** para publicar el servicio en el puerto **80**
```bash 
 > /avengers.sh
```
## Ejecutar Tests

Para ejecutar los Tests unitarios, puedes ejecutar el siguiente comando desde "command line"

```bash
  mvn clean test
```


## MongoDb Atlas

Para el almacenamiento de la información que realiza esta API se hace uso de un servicio público de MongoDb llamado Atlas. Por lo cual se comparte el usuario de consulta que se definió para la aplicación.

`Conexión mongo -> mongodb+srv://albo-user:erirodri-albo-marvel@cluster0.zsjho.mongodb.net/albo-erirodri?retryWrites=true&w=majority`

`User -> albo-user`

`Password -> erirodri-albo-marvel`



## Ligas de Interés
- [API Marvel](https://developer.marvel.com/docs)
- [SonarQube](https://www.sonarqube.org/)
- [MongoDb Atlas](https://www.mongodb.com/cloud/atlas/lp/try2-aterms?utm_content=0618atermsEXP&utm_source=google&utm_campaign=gs_americas_mexico_search_core_brand_atlas_desktop&utm_term=mongodb%20atlas&utm_medium=cpc_paid_search&utm_ad=e&utm_ad_campaign_id=13563674158&gclid=CjwKCAjwiLGGBhAqEiwAgq3q_kdauI0C-ieKdy5YsUzgzZlmoOZyaingqZ9hxfA1KW4wyYcbFiWePxoCBZIQAvD_BwE)
