# BakeFlow - Sistema de Gestão para Confeitarias

BakeFlow é um sistema de gestão local que auxilia no controle de pedidos, estoque, finanças e recursos utilizados em confeitarias.

## 📋 Sobre o Projeto

O BakeFlow é uma solução de gestão desenvolvida para substituir processos manuais (cadernos e planilhas) por uma plataforma mais organizada, confiável e intuitiva.

### Motivação

A Bassani's Bakery enfrenta desafios como:
- Erros frequentes no controle de estoque e vendas
- Falta de monitoramento levando ao desperdício de ingredientes
- Dificuldade para calcular lucro e tomar decisões estratégicas
- Processo manual pouco eficiente

## 🎯 Objetivo

Entregar um sistema de gestão local que substitua o CRUD simples por uma interface fluida e amigável, garantindo integridade e confiabilidade das informações essenciais para o negócio.

## ✨ Funcionalidades

- ✅ Gestão de pedidos
- ✅ Controle de estoque atualizado
- ✅ Organização de receitas e ingredientes
- ✅ Cálculos financeiros básicos
- ✅ Interface intuitiva e simplificada
- ✅ Integração com serviço de fidelização
- ✅ Notificações de eventos importantes

## 🛠 Tecnologias Utilizadas

- **Backend**: Java 17 com Spring Boot 3.5.7
- **Frontend**: Thymeleaf, HTML5, CSS3, Bootstrap
- **Banco de Dados**: MySQL
- **Build Tool**: Maven
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+
- Git

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/nathdourado/fidelizacao-service.git
cd fidelizacao-service
```

2. Configure as variáveis de ambiente:
```bash
cp .env.example .env
# Edite o arquivo .env com suas configurações
```

3. Ou configure via `application.properties`:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edite o arquivo com suas credenciais do banco de dados
```

4. Compile o projeto:
```bash
./mvnw clean package
```

5. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Variáveis de Ambiente Necessárias

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DB_URL` | URL do banco de dados MySQL | `jdbc:mysql://localhost:3306/bakeflow` |
| `DB_USERNAME` | Usuário do banco de dados | `root` |
| `DB_PASSWORD` | Senha do banco de dados | - |
| `FIDELIZACAO_SERVICE_URL` | URL do serviço de fidelização | `http://localhost:8081` |
| `NOTIFICACAO_SERVICE_URL` | URL do serviço de notificações | `http://localhost:8082` |

## 📚 Estrutura do Projeto

```
src/main/java/br/com/bakeflow/bakeflow/
├── controller/        # Controllers MVC e REST
├── service/          # Lógica de negócio
├── repository/       # Acesso a dados (JPA)
├── model/           # Entidades JPA
├── client/          # Clientes HTTP para serviços externos
├── event/           # Publicadores e listeners de eventos
├── dto/             # Data Transfer Objects
└── application/     # Configurações da aplicação

src/main/resources/
├── templates/       # Templates Thymeleaf
├── static/         # CSS, JavaScript, imagens
└── application.properties  # Configuração da aplicação
```

## 🔌 API REST

A API está documentada usando Swagger/OpenAPI. Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

### Endpoints Principais

- **Produtos**: `/api/produtos/*`
- **Pedidos**: `/api/pedidos/*`
- **Estoque**: `/api/estoque/*`
- **Clientes**: `/clientes/*`

## 👥 Equipe

- **Gabriela de Alencar Marin**
- **Lucas Bispo de Paula**
- **Nathalia Dourado Luz**

Curso: BSI – 3º Semestre  
Projeto: Profissional – Melhoria de Processos

## 🏪 Comunidade Atendida

- **Empreendedora**: Marina Bassani
- **Local**: Ilhabela, São Paulo
- **Negócio**: Bassani's Bakery – Confeitaria artesanal

## 📊 Alinhamento com ODS

O projeto contribui para os Objetivos de Desenvolvimento Sustentável:

- ODS 11 – Cidades e comunidades sustentáveis
- ODS 12 – Consumo e produção responsáveis
- ODS 13 – Ação contra mudança do clima
- ODS 15 – Vida terrestre
- ODS 16 – Paz, justiça e instituições eficazes
- ODS 17 – Parcerias e meios de implementação

## 📝 Licença

Desenvolvido como parte do programa SENAC para fins educacionais.

## 📞 Suporte

Para dúvidas ou sugestões, abra uma issue no repositório.

---

**Desenvolvido com ❤️ para a Bassani's Bakery**