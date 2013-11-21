# RESTful Bookmarks Spring RESTful API

Exemplo de aplicação para salvar links, onde a interface utiliza AngularJS + Twitter Bootstrap e o lado do servidor utilizado o Spring RESTful API para disponibilizar um serviço de dados RESTful. A comunicação entre o frontend e o backend é realizada com as informações serializadas em JSON.

* [Histório / Alterações](https://github.com/erkobridee/restful-bookmarks-springrest/releases)


## Guia de Instalação

### Clone

```bash
$ git clone https://github.com/erkobridee/restful-bookmarks-springrest.git
$ cd restful-bookmarks-springrest/
```

### Montando o ambiente local para uso desse projeto

> O projeto disponibilizado no github, não possui nenhum arquivo de projeto referente ao Eclipse.

Execute os comandos a seguir dentro do diretório do projeto:

1. Execute os comandos em sequência:
	
	`mvn compile` 
	
	`mvn eclipse:eclipse`
	
2. Importe o projeto no Eclipse

	**Atenção:** (caso não esteja utilizando o plugin do Maven no Eclipse)

	```
	É necessário ter a variável M2_REPO configurada nas 
	variáveis do ClassPath, apontando para o diretório 
	do .m2/repository do Maven

	Lembre-se também de ter adicionado o Apache Tomcat 6.x
	ao Runtime Environments nas preferencias do seu Eclipse
	```

### Comandos úteis do Maven

* Gerar o .war do projeto

	`mvn clean install`

* Executar o projeto diretamente pelo Maven:

	`mvn jetty:run`

> Acesse a aplicação na URL: `http://localhost:9090`


## Licença

MIT : [erkobridee.mit-license.org](http://erkobridee.mit-license.org)


## Utilizado neste projeto

* Ambiente de desenvolvimento

	* [Maven](http://maven.apache.org/) 3.x

	* [Eclipse](http://eclipse.org/) Juno JEE

	* [Apache Tomcat](http://tomcat.apache.org/) 6.x

	* [Java](http://www.java.com/) 1.6+

* Cliente

	* [AngularJS](http://angularjs.org/) 1.2.1

	* [Twitter Bootstrap](http://getbootstrap.com/) 3.0.2

* Servidor

	* [Spring](http://spring.io/)

	* [Hibernate](http://www.hibernate.org/)

	* [HSQLDB](http://hsqldb.org/)

	* [Jetty](http://www.eclipse.org/jetty/) para testes, gerenciado pelo Maven

Quanto as versões no Servidor: `Verificar o arquivo pom.xml`
	
Segue o link do post [Rendering JSON Responses with Spring 3 Web Services](http://www.informit.com/guides/content.aspx?g=java&seqNum=604) que auxiliou para criar este projeto.


## Quanto ao RESTful do projeto

A definição do método a ser executado é definido no cabeçalho da requisição enviada para o servidor.

* **GET** - recupera 1 ou mais bookmarks
	
	* [.../rest/bookmarks/]() - lista todos os bookmarks | suporte para paginação `?page=${num}&size=${length}`

	* [.../rest/bookmarks/{id}]() - retorna o respectivo bookmark pelo seu ID

	* [.../rest/bookmarks/search/{name}]() - retorna uma lista dos bookmarks que contém o respectivo nome | suporte para paginação `?page=${num}&size=${length}`

* **POST** - insere um novo bookmark

	* [.../rest/bookmarks/]() - enviado no corpo da requisição

* **PUT** - atualiza um bookmark existente

	* [.../rest/bookmarks/{id}]() - enviado no corpo da requisição

* **DELETE** - remove 1 bookmark pelo ID

	* [.../rest/bookmarks/{id}]() 


## Archetype do Maven que gerou a estrutura inicial do projeto

```
mvn archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DarchetypeArtifactId=maven-archetype-webapp \
  -Dversion=1.0 \
  -DgroupId=com.erkobridee.restful.bookmarks.springrest \
  -DartifactId=restful-bookmarks-springrest
```

