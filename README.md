<h1 align="center">Microserviços de avaliação de criação de líder - Empresa de Software</h1>
<p align="center"><i>Repositório responsável por criar os líderes das squads.</i></p>

##  Sobre esse projeto
Este é um projeto que deve ser rodado após o Eureka Server estar rodando, para que se registre no discovery server.


## Indíce
<!--ts-->
   * [Como usar?](#como-usar)
   * [Endpoints](#endpoints)
   * [Creditos](#creditos)
<!--te-->
  
<h1>Como usar?</h1>
<p>O Eureka Server deve estar rodando, acesse-o <a href="https://github.com/ValterGabriell/bank-system-eureka-server">aqui</a>.</br>
<p>Clone ou baixe o repositório e start ele através de sua IDE de preferência rodando o método main da classe principal na pasta raíz da aplicação, feito isso, basta começar a usar :). O ideal é startar todos os outros microserviços antes de testar a aplicação.</p>
<p>Além disso, é fundamental ter um container do RabbitMQ no Docker rodando com usuario e senha padrao (guest, guest) para o microserviço poder enviar o código para a fila.</p>

1 -> <a href="https://github.com/ValterGabriell/bank-system-msaccount">Microserviço responsável por criar contas bancárias dos usuários</a></br>
2 -> <a href="https://github.com/ValterGabriell/bank-system-mscards">Microserviço responsável por criar cartões para os usuários</a></br>
3 -> <a href="https://github.com/ValterGabriell/bank-system-mscreditappraiser">Microserviço responsável verificar o crédito que o usuário terá e solicitar a emissão de cartão</a></br>
4 -> <a href="https://github.com/ValterGabriell/software-company-mslead">Microserviço responsável pela criação dos líderes das squads</a></br>
5 -> <a href="https://github.com/ValterGabriell/software-company-mscolaborators">Microserviço responsável pela criação dos colaboradores das squads</a></br>
6 -> <a href="https://github.com/ValterGabriell/software-company-msjobs">Microserviço responsável pela criação dos trabalhos dos colaboradores</a></br>
7 -> <a href="https://github.com/ValterGabriell/bank-system-gateway">Gateway para fazer o loadbalancer dos microserviços</a></br>
8 -> <a href="https://github.com/ValterGabriell/software-company-msshopping">Micro serviço responsável por gerenciar as compras dos clientes.</a></br>

  
<h1>Endpoints</h1>
<h3>BASE URL</h3>

```bash
http://localhost:8080/lead/
``` 
<h1>POST</h1></br>

<h2>Criar conta</h2>

<table>
  <tr>
    <th>Request</th>

 
   
  </tr>
  <tr>
    <td>/request</td>


  
 
  </tr>
</table>


<h3>Request esperada</h3>

```bash

{
	"id":93856573232123,
	"name":"Valter Gabriel",
	"email":"email@hotmail.com",
	"company":"Dev Cast",
	"phone":"+5596123547895",
	"gender":0,
	"bornDay":"2000-06-06",
	"password":"123",
	"income":5000
}

```

<h3>Resposta esperada</h3></br>

```bash
{
	"id": 93856573232123,
	"name": "Valter Gabriel",
	"email": "email@hotmail.com",
	"phone": "+5596123547895",
	"active": true,
	"gender": "MALE",
	"bornDay": "2000-06-06",
	"creationDate": "2023-05-08",
	"password": "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3",
	"type": "JURY_PERSON",
	"uri": "http://localhost:9090/account?cpf=93856573232123"
}
```

<h1>GET</h1></br>


<h2>Recuperar um líder por ID</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/find-by-id</td>
    <td>cnpj</td>
  </tr>
</table>



<h3>Resposta esperada</h3></br>

```bash
{
	"data": {
		"id": 93856573232123,
		"name": "Valter Gabriel",
		"email": "email@hotmail.com",
		"phone": "+5596123547895",
		"gender": "MALE",
		"bornDay": "2000-06-06",
		"type": "JURY_PERSON",
		"accountCreationDate": "2023-05-03",
		"password": "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3",
		"active": true
	},
	"message": "Sucesso!"
}
```


<h2>Recuperar todos os colaboradores associados a um lider</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/find-all-colaborators</td>
    <td>cnpj</td>
  </tr>
</table>



<h3>Resposta esperada</h3></br>

```bash
Lista de colaboradores
```



<h1>PUT</h1>
<h2>Atualizar um líder</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/find-all-colaborators</td>
    <td>cnpj</td>
  </tr>
</table>



<h3>Request esperada</h3>

```bash

{
	"name":"Lucas Gabriel",
	"email":"email2@hotmail.com",
	"company":"Dev Cast",
	"phone":"65121321",
	"password":"1sssssss233333"
}

```

<h3>Resposta esperada</h3></br>

```bash
{
	"id": 93856573232123,
	"name": "Lucas Gabriel",
	"email": "email2@hotmail.com",
	"phone": "65121321",
	"isActive": null,
	"gender": "MALE",
	"bornDay": "2000-06-06",
	"type": "JURY_PERSON",
	"accountCreationDate": "2023-04-24",
	"password": "EDD9BD2DFEDF7EAE75F1F46F8DFCC020CE662A05BFF0B6F56448CA739926C7FF"
}
```


<h1>DELETE</h1>
<h2>Deletar um líder</h2>
<table>
  <tr>
    <th>Request</th>  
    <th>Query</th>
  </tr>
  <tr>
    <td>/delete</td>  
    <td>cnpj</td>
  </tr>
</table>


<h1>Créditos</h1>

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

