# poll-poc
A Application to describe a poll API

## Escolhas de Modelagem e Arquitetura 
- Inicialmente foi pensado que a aplicação poderia se beneficiar da utilização de um banco NoSQL, mas foi escolhido o uso do postgres por familiaridade com a tecnologia;
- A escolha de salvar os CPFs em uma tabela separada e contabilizar para gerar a notificação foi feita pensando em evitar concorrência dos dados, assim como, manter o tracking de quem já havia votado de maneira simples
- SpringBoot foi escolhido pela familiariadade com o ambiente de desnvolvimento bem como facilidade de uso
- RabbitMQ foi escolhido pois o delay-message se encaixava muito bem com a proposta de eventos que ocorrerão de maneira automátizada após x tempo

## Implementações por serem realizadas:
- Testes de integração com RestAssured
- Adicionar API no docker-compose para facilitar um deploy na nuvem
- Aumentar cobertura de teste
- Melhorar logs e tratamentos de erro
- Melhorar documentação via Swagger

### TODO:

- [x] Create DB scripts
- [x] Create User endpoints
- [x] Create Poll endpoints
- [x] Create Voting "vote"
- [x] Create verify voting result
- [x] Add rabbit to docker
- [x] Setup queues on rabbit via application
- [x] Create Sender and Receiver beans
- [x] Create message implementation
- [x] Create consumer implementation 
- [x] Add UnitTest and any other, it seems necessary.
- [ ] Add application on docker-compose  
- [ ] Deploy on heroku

## To Run the Application: 

- Start the containers via docker-compose (make sure that you have docker installed)

```docker-compose up```

- Start the API 

``java -jar ./poll-app-0.0.1-SNAPSHOT.jar``

- You can check the available endpoints at:

``http://localhost:8080//swagger-ui.html#/``
