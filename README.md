# CRUD Produto - API REST

Sistema de gerenciamento de produtos e categorias desenvolvido em Java, com API REST utilizando o framework Spark Java e integração com banco de dados MySQL.

## 📋 Sobre o Projeto

Este projeto implementa uma API REST completa para gerenciamento de produtos e categorias, permitindo operações CRUD (Create, Read, Update, Delete) através de endpoints HTTP. A aplicação utiliza uma arquitetura em camadas, separando responsabilidades entre modelos, acesso a dados e API.

## 🚀 Tecnologias Utilizadas

- **Java** - Linguagem de programação
- **Spark Java** - Framework web para criação da API REST
- **MySQL** - Banco de dados relacional
- **Gson** - Biblioteca para serialização/deserialização JSON
- **JDBC** - API para conexão com banco de dados

## 📁 Estrutura do Projeto

```
crud-produto/
├── src/
│   ├── api/
│   │   └── ApiProduto.java          # Endpoints da API REST
│   ├── dao/
│   │   ├── ProdutoDAO.java          # Acesso a dados de produtos
│   │   └── CategoriaDAO.java        # Acesso a dados de categorias
│   ├── model/
│   │   ├── Produto.java             # Modelo de dados Produto
│   │   └── Categoria.java           # Modelo de dados Categoria
│   ├── util/
│   │   └── ConnectionFactory.java   # Factory para conexão com BD
│   └── App.java                     # Classe principal (testes)
├── lib/                              # Bibliotecas externas
└── bin/                              # Arquivos compilados
```

## 🎯 Funcionalidades

### Produtos

- ✅ Listar todos os produtos
- ✅ Buscar produto por ID
- ✅ Criar novo produto
- ✅ Atualizar produto existente
- ✅ Deletar produto

### Categorias

- ✅ Listar todas as categorias
- ✅ Buscar categoria por ID
- ✅ Criar nova categoria
- ✅ Atualizar categoria existente
- ✅ Deletar categoria

## 🔌 Endpoints da API

### Produtos

| Método | Endpoint        | Descrição                     |
| ------ | --------------- | ----------------------------- |
| GET    | `/produtos`     | Lista todos os produtos       |
| GET    | `/produtos/:id` | Busca produto por ID          |
| POST   | `/produtos`     | Cria um novo produto          |
| PUT    | `/produtos/:id` | Atualiza um produto existente |
| DELETE | `/produtos/:id` | Deleta um produto             |

### Categorias

| Método | Endpoint          | Descrição                        |
| ------ | ----------------- | -------------------------------- |
| GET    | `/categorias`     | Lista todas as categorias        |
| GET    | `/categorias/:id` | Busca categoria por ID           |
| POST   | `/categorias`     | Cria uma nova categoria          |
| PUT    | `/categorias/:id` | Atualiza uma categoria existente |
| DELETE | `/categorias/:id` | Deleta uma categoria             |

## 🛠️ Como Executar

### Pré-requisitos

- Java JDK 8 ou superior
- MySQL instalado e configurado
- Banco de dados `aulajdbc` criado

### Configuração do Banco de Dados

1. Crie o banco de dados MySQL:

```sql
CREATE DATABASE aulajdbc;
```

2. Crie as tabelas necessárias:

```sql
CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL
);

CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);
```

3. Configure as credenciais de acesso no arquivo `ConnectionFactory.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/aulajdbc";
private static final String USER = "seu_usuario";
private static final String PASS = "sua_senha";
```

### Executando a API

1. Compile o projeto:

```bash
javac -cp "lib/*" -d bin src/**/*.java
```

2. Execute a API:

```bash
java -cp "bin:lib/*" api.ApiProduto
```

3. A API estará disponível em: `http://localhost:4567`

## 📝 Exemplos de Uso

### Criar um Produto

```bash
curl -X POST http://localhost:4567/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Notebook","preco":2500.00,"estoque":10}'
```

### Listar Todos os Produtos

```bash
curl http://localhost:4567/produtos
```

### Buscar Produto por ID

```bash
curl http://localhost:4567/produtos/1
```

### Atualizar um Produto

```bash
curl -X PUT http://localhost:4567/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Notebook Atualizado","preco":2300.00,"estoque":15}'
```

### Deletar um Produto

```bash
curl -X DELETE http://localhost:4567/produtos/1
```

## 📦 Dependências

As bibliotecas necessárias estão na pasta `lib/`:

- `spark-core-2.9.4.jar` - Framework Spark Java
- `gson-2.13.1.jar` - Serialização JSON
- `mysql-connector-j-9.4.0.jar` - Driver MySQL
- `jetty-*.jar` - Servidor web embutido
- `slf4j-*.jar` - Logging

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

- **Model**: Classes de domínio (Produto, Categoria)
- **DAO**: Camada de acesso a dados (Data Access Object)
- **API**: Camada de apresentação com endpoints REST
- **Util**: Utilitários (ConnectionFactory)


