# Projeto de mensageria com Consumer e Producer - Spring Boot com RabbitMQ e MongoDB

Este projeto é um microserviço desenvolvido com **Java 21** e **Spring Boot**, utilizando **RabbitMQ** como sistema de mensageria (Producer/Consumer) e **MongoDB** como banco de dados NoSQL.  
Todo o ambiente de dependências externas (RabbitMQ e MongoDB) é orquestrado com **Docker Compose**.

---

## Tecnologias Utilizadas

### **Java 21**

### **RabbitMQ**
- Broker de mensageria utilizado para comunicação assíncrona entre serviços.
- Este projeto implementa **producers** (para envio de mensagens) e **consumers** (para leitura das mensagens).

### **MongoDB**
- Banco de dados NoSQL utilizado para armazenamento de dados.

### **Docker Compose**
- Ferramenta para subir e gerenciar serviços externos.
- Um arquivo `docker-compose.yml` está disponível para subir o RabbitMQ e o MongoDB.

---
## Arquitetura Básica

1. O microserviço expõe endpoint REST (via Spring Boot) para consulta de resume de orders by customerId.
2. Mensagens são publicadas em filas RabbitMQ usando **producers**.
3. Mensagens são consumidas por **consumers** que processam e salvam dados no MongoDB.

---

## Pré-requisitos

- **Java 21+** instalado.
- **Docker e Docker Compose** instalados.
  
---

## Subindo os Serviços Externos

Para subir o RabbitMQ e o MongoDB localmente, execute:

```bash
docker-compose up -d

```

## RabbitMQ ficará disponível em:

Broker: amqp://localhost:5672

UI Management: http://localhost:15672

Usuário padrão: guest

Senha padrão: guest

## MongoDB ficará disponível em:

URI: mongodb://localhost:27017
