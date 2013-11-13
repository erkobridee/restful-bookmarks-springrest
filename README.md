# RESTful Bookmarks Spring RESTful API

Exemplo de aplicação para salvar links, onde a interface utiliza AngularJS + Twitter Bootstrap e o lado do servidor utilizado o Spring RESTful API para disponibilizar um serviço de dados RESTful, que aceita uma comunicação JSON ou XML.

* [Histório / Alterações](https://github.com/erkobridee/restful-bookmarks-springrest/releases)


## Utilizado neste projeto

* Ambiente de desenvolvimento

	* [Maven](http://maven.apache.org/) 3.x

	* [Eclipse](http://eclipse.org/) Juno JEE

	* [Apache Tomcat](http://tomcat.apache.org/) 6.x

	* [Java](http://www.java.com/) 1.6+

* Cliente

	* [AngularJS](http://angularjs.org/) 1.1.5

	* [Twitter Bootstrap](http://getbootstrap.com/) 3.x

* Servidor

	* [Spring](http://spring.io/) 3.1.1.RELEASE

	* [Hibernate](http://www.hibernate.org/) 3.3.2.GA

	* [HSQLDB](http://hsqldb.org/) 2.2.6

	* [Apache Tomcat](https://tomcat.apache.org/) 6.x | [Jetty](http://www.eclipse.org/jetty/) para testes, gerenciado pelo Maven

Quanto as versões no Servidor:

`Verificar o arquivo pom.xml`
	
Segue o link do post [Rendering JSON Responses with Spring 3 Web Services](http://www.informit.com/guides/content.aspx?g=java&seqNum=604) que auxiliou para criar este projeto.


## Montando o ambiente local para uso desse projeto

O projeto disponibilizado no github, não possui nenhum arquivo para incluí-lo diretamente no Eclipse. Então após baixar o projeto no seu computador local.

Conforme especificado anteriormente, os itens citados do ambiente de desenvolvimento serão necessários.

Feito o download/clone do projeto para a sua máquina local, realize o respectivos passos a seguir:

1. Vá até o diretório do projeto
2. Execute os comandos em sequência:
	
	`mvn compile` 
	
	`mvn eclipse:eclipse`
	
3. Importe o projeto no Eclipse

	**Atenção:**

	```
	É necessário ter a variável M2_REPO configurada nas 
	variáveis do ClassPath, apontando para o diretório 
	do .m2/repository do Maven

	Lembre-se também de ter adicionado o Apache Tomcat 6.x
	ao Runtime Environments nas preferencias do seu Eclipse
	```

4. Feito isso, o projeto está pronto para ser executado no Eclipse. Caso queira gerar o .war do projeto utilize o comando

	`mvn clean install`


## Quanto ao RESTful do projeto

A definição do método a ser executado é definido no cabeçalho da requisição enviada para o servidor.

* **GET** - recupera 1 ou mais bookmarks
	* [.../rest/bookmarks/]() - lista todos os bookmarks | suporte para paginação `?page=${num}&size=${length}`

	* [.../rest/bookmarks/{id}]() - retorna o respectivo bookmark pelo seu ID

	* [.../rest/bookmarks/search/{name}]() - retorna uma lista dos bookmarks que contém o respectivo nome | suporte para paginação `?page=${num}&size=${length}`

* **POST** - insere um novo

	* [.../rest/bookmarks/]() - enviado via post

* **PUT** - atualiza um existente

	* [.../rest/bookmarks/{id}]() - enviado via post 

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

## Licença

MIT : [erkobridee.mit-license.org](http://erkobridee.mit-license.org)
