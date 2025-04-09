# 🛒 Ecommerce Manager API

**Gerencie usuários, produtos, pedidos, relatórios e tudo que envolve o seu ecommerce de forma simples.**

Esta aplicação foi construída com foco em segurança e produtividade, utilizando tecnologias modernas e uma arquitetura limpa e modular.

---

## 🔧 Tecnologias Utilizadas

| Tecnologia            
|---------------------
| Java 21                 
| Spring Boot 3.4.4           
| Spring Security 6+     
| Kafka         
| Elasticsearch       
| MySQL               
| Docker + Docker Compose 
| Gradle               
| Flyway (Migrations)  
| Swagger (OpenAPI)    

---

## 🚀 Como subir o projeto

### 🔁 1. **Subindo tudo com Docker Compose**

Simples, rápido e direto: **tudo já está configurado no modo '`prod`'**, com perfil e variáveis de ambiente configurados.

```bash
docker compose up --build
```

Aguarde alguns instantes ⏳... e sua aplicação estará disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

---

### 🛠️ 2. **Rodando a app localmente no modo `dev`**

Se você quer rodar a aplicação **manualmente via código**, também é possível!

1. Suba os serviços externos com Docker:

```bash
docker compose up mysql elasticsearch zookeeper kafka 
```

2. Execute a aplicação no modo `dev` com o Gradle:

```bash
./gradlew bootRun
```

✅ O modo `dev` por padrão será ativado e possui configurações específicas para rodar localmente, utilizando `localhost` para as integrações.

---

## 📬 Postman Collection

Está disponível a collection e as envs do Postman:

> **`postman/E-commerce manager api.postman_collection.json`**
> **`postman/ecommerce-dev.postman_environment.json`**

Nela você encontrará exemplos prontos de autenticação, criação de pedidos, relatórios, busca de produtos e muito mais!

📌 **Importante:**  
As envs estão configuradas, então cada request o update nas variáveis serão feitas.
---

## 📂 Migrations com Flyway

O projeto já possui migrations automáticas via Flyway

🔑 Assim que a aplicação sobe, os scripts criam:

- Todas as tabelas necessárias
- Índices otimizados
- Dados iniciais com **7 usuários cadastrados**:

| Tipo     | Usuários                        |
|----------|---------------------------------|
| Admin    | login: `admin` / senha: `admin` |
| User     | login: `user` / senha:  `user1` |
|          | login: `user2` / senha: `user2` |
|          | login: `user3` / senha: `user3` |
|          | login: `user4` / senha: `user4` |
|          | login: `user5` / senha: `user5` |
|          | login: `user6` / senha: `user6` |

🔐 As senhas são criptografadas com `BCrypt`.

---

## 🔒 Swagger com Autenticação

O Swagger está disponível no endpoint:

```
http://localhost:8080/swagger-ui/index.html
```

### Como autenticar:

1. Gere seu token na rota `/auth`
2. Clique em **Authorize** no Swagger
3. Cole seu token com o prefixo:

```
Bearer SEU_TOKEN_JWT
Por padrão o Bearer já está configurado, basta colocar o token.
```

Agora você poderá testar todas as rotas protegidas diretamente pela interface Swagger! 😎

---
## Testes Unitários - Ecommerce Manager API

Este projeto está coberto por testes unitários completos para os principais serviços da aplicação, utilizando **JUnit 5** e **Mockito**.

### 📁 Estrutura dos Testes

Todos os testes estão localizados em `src/test/java`, organizados de acordo com os pacotes da aplicação.

Além disso, o projeto utiliza uma estrutura reutilizável com:

- **BaseTest.java** → Configuração de contexto e mocks padrão.
- **Factory** → Classes auxiliares para criar objetos fictícios.

### ▶️ Como Executar os Testes

#### 💻 Via Gradle

Para rodar todos os testes unitários, basta executar:

```bash
./gradlew test
```

#### ✅ Executar Teste Específico

Para executar apenas uma classe de teste:

```bash
 ./gradlew test --tests br.com.guilherme.ecommerce_manager_api.service.AuthServiceImplTest
```

---

### 🔎 Boas Práticas Adotadas

- Nome dos métodos em inglês e descritivos: `shouldCreateNewProduct()`
- Verificação de interações com `verify(...)`

---

### 💡 Dica

> Utilize o recurso de cobertura do IntelliJ (ou outro IDE) para visualizar facilmente as linhas cobertas por teste.

---
---

## ✨ Decisões arquiteturais e melhorias

 - Não optei pelo desenvolvimento de testes de integração por questão de tempo e custo, visto que a app tem swagger e 
collection do postman para testar de fato a app, apenas testes unitários focados nos serviços foram desenvolvidos.
 - Criar um endpoint /v2/pedidos/{idPedido}/pagamento para aceitar pagamento apenas do usuário dono do pedido.
