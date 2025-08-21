# 📦 DM111 - Sistema de Promoções em Microserviços (2025)

Este repositório contém a implementação de um sistema baseado em **arquitetura de microserviços**, com foco em um ecossistema de cadastro e gerenciamento de promoções, autenticação e recomendação personalizada, com deploy na plataforma **Google App Engine**.

---

## 🧩 Microserviços da Solução

A solução completa é composta por **quatro aplicações Spring Boot**:

1. **Usuários**  
   - Cadastro de usuários  
   - Registro das **categorias preferidas** de cada usuário  
   - Exposição de uma API para consulta de **novidades** personalizadas  
   - Persistência via **Firebase**

2. **Restaurantes**  
   - Cadastro de restaurantes  
   - Associação com usuários existentes  
   - Persistência via **Firebase**

3. **Autenticação**  
   - Login de usuários  
   - Geração de **JWTs assinados com chave compartilhada**  
   - Chave JWT usada por todos os serviços para validação da autenticação

4. **Promoções**
   - Cadastro, listagem, atualização e exclusão de promoções pelo **restaurante**
   - Listagem de todas as promoções públicas
   - Listagem de promoções **relevantes para o cliente**, com base em suas categorias preferidas
   - Persistência via **Firebase**

---

## 🎯 Objetivos do Desafio Final

Implementar o microserviço de **Promoções** com os seguintes requisitos:

### 🔐 Contexto Restaurante
- Criar nova promoção
- Listar promoções próprias
- Atualizar promoção
- Excluir promoção

---

## 🔁 Integração via Pub/Sub (opcional)

Para quem optar por uma arquitetura **event-driven**, o seguinte fluxo adicional deve ser implementado:

- Ao cadastrar uma nova promoção, o serviço de promoções envia uma **mensagem no Pub/Sub**.
- O serviço de usuários consome essa mensagem e cria uma **"Novidade"** para os usuários interessados (cuja categoria preferida coincida com a da promoção).
- A aplicação de usuários deve expor uma **nova API de "novidades"**.

---

## 🛠 Tecnologias utilizadas

- Java 17+
- Spring Boot
- Firebase (Firestore)
- JWT (com chave compartilhada entre todos os microserviços)
- //Google Cloud App Engine
- //Google Cloud Pub/Sub (opcional)
- Maven
