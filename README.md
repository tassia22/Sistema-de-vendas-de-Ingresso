# Sistema de Vendas de Ingressos em Java Swing com MySQL

Este repositório contém o código-fonte de um sistema de gerenciamento de vendas de ingressos, desenvolvido em Java. A aplicação emprega uma interface gráfica baseada em Swing para interação com o usuário e utiliza o sistema de gerenciamento de banco de dados MySQL para persistência dos dados.

## Funcionalidades Implementadas

* **Gestão de Usuários:** Permite o registro de novos usuários (designados como compradores), armazenando informações como nome completo, endereço de e-mail, tipo de perfil (Cliente, Administrador) e data de nascimento.

* **Gestão de Ingressos:** Habilita o cadastro de ingressos individuais, vinculando-os a um evento preexistente e especificando detalhes como tipo de ingresso, preço unitário e um código único de identificação.

* **Processamento de Vendas:** Simula o fluxo de venda de ingressos, registrando as transações no banco de dados e atualizando de forma autônoma o número de ingressos disponíveis para o respectivo evento.

* **Consulta de Dados:** Oferece recursos para visualização e listagem dos eventos cadastrados e das vendas realizadas, com a recuperação de dados diretamente do armazenamento persistente.

## Tecnologias e Ferramentas

**Linguagem de Programação:** Java

**Framework de Interface Gráfica:** Java Swing (pacote javax.swing)

**Sistema de Gerenciamento de Banco de Dados (SGBD):** MySQL

**Camada de Conectividade com Banco de Dados:** JDBC (Java Database Connectivity)

**Ambiente de Desenvolvimento Integrado (IDE):** NetBeans IDE (alternativamente, IntelliJ IDEA, Eclipse)

**Sistema de Controle de Versão:** Git

**Plataforma de Hospedagem de Repositórios:** GitHub

## Pré-requisitos para Execução 

Para a configuração e execução bem sucedida deste projeto em um ambiente local, os seguintes componentes de software são indispensáveis:

**Java Development Kit (JDK) 8 ou versão superior:** Disponível para download em: https://www.oracle.com/java/technologies/downloads/

N**etBeans IDE:** Onde o projeto foi desenvolvido e é otimizado para abertura: https://netbeans.apache.org/download/index.html

**Servidor MySQL:** Uma instância do servidor MySQL em execução é fundamental. Opções incluem:

**MySQL Community Server:** https://dev.mysql.com/downloads/mysql/8.0.html

**Pacotes integrados (e.g., XAMPP, WAMP), que incluem MySQL:** https://www.apachefriends.org/index.html

**Ferramenta de Gerenciamento MySQL:** Essencial para a administração do banco de dados (ex: MySQL Workbench, phpMyAdmin).

**MySQL Connector/J (Driver JDBC):** O driver oficial para conexão Java com MySQL. Pode ser obtido em: https://dev.mysql.com/downloads/connector/j/ (selecione "Platform Independent" e faça o download do pacote .zip).

 ## Configuração do Ambiente de Banco de Dados

1.  **Inicialização do Servidor MySQL:** Assegure-se de que o serviço do seu servidor MySQL esteja ativo.

**2. Acesso à Ferramenta de Gerenciamento MySQL:** Abra a ferramenta de sua preferência (MySQL Workbench, phpMyAdmin, etc.).

**3. Criação do Esquema e Tabelas:** Execute os comandos SQL subsequentes para estabelecer o esquema de banco de dados (vendas_ingressos) e todas as tabelas requeridas pelo sistema:

```sql

CREATE DATABASE IF NOT EXISTS vendas_ingressos;
USE vendas_ingressos;


CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL, 
    data_nascimento DATE
);

CREATE TABLE IF NOT EXISTS eventos (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    nome_evento VARCHAR(255) NOT NULL,
    local VARCHAR(255) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(100),
    data_evento DATE NOT NULL,
    ingressos_disponiveis INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS ingressos (
    codigo_unico VARCHAR(50) PRIMARY KEY, 
    tipo_ingresso VARCHAR(100) NOT NULL, 
    preco DECIMAL(10, 2) NOT NULL,
    id_evento INT NOT NULL, 
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento)
);

CREATE TABLE IF NOT EXISTS vendas (
    numero_venda INT AUTO_INCREMENT PRIMARY KEY,
    data_venda DATE NOT NULL,
    id_comprador INT NOT NULL, 
    codigo_ingresso_vendido VARCHAR(50) NOT NULL, 
    quantidade INT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_comprador) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (codigo_ingresso_vendido) REFERENCES ingressos(codigo_unico)
);
```

**4. Ajuste das Credenciais de Conexão Java:**

* Localize e abra o arquivo src/vendasdeingresso/conexao/ConexaoMySQL.java no seu ambiente de desenvolvimento.

* Modifique as variáveis estáticas USUARIO e SENHA para corresponderem às credenciais de acesso do seu servidor MySQL:

```sql
private static final String USUARIO = "root"; // Seu usuário do MySQL
private static final String SENHA = "";     // Sua senha do MySQL (deixe vazio se não houver)
```
* **Observação Importante:**
 Caso tenha encontrado erros de "Access denied" anteriormente, é fundamental verificar se o método de autenticação configurado para o usuário root no MySQL é mysql_native_password. Esta configuração pode ser ajustada via MySQL Workbench, na seção Users and Privileges.

##  Guia de Execução do Sistema

 1. **Clonar o Repositório:**
Abra um terminal (Git Bash ou Prompt de Comando/PowerShell) e execute os comandos para clonar o repositório e navegar para o diretório do projeto:

```sql
git clone https://github.com/tassia22/Sistema-de-vendas-de-Ingresso.git
cd Sistema-de-vendas-de-Ingresso
```

2. **Abertura do Projeto no NetBeans:**
* Na NetBeans IDE, selecione a opção File > Open Project... (Abrir Projeto...).
* Navegue até o diretório onde o repositório foi clonado (Sistema-de-vendas-de-Ingresso).
* Selecione a pasta raiz do projeto (que contém src, nbproject, build.xml, etc.) e confirme com Open Project.

3. **Adição do Driver JDBC do MySQL às Bibliotecas:**

* No painel "Projects" (Projetos) do NetBeans, expanda a árvore do seu projeto.

* Clique com o botão direito do mouse sobre a pasta "Libraries" (Bibliotecas).

* Selecione a opção "Add JAR/Folder..." (Adicionar JAR/Pasta...).

* Navegue até a localização do arquivo .jar do MySQL Connector/J (obtido nos Pré-requisitos) e selecione-o (ex: mysql-connector-j-8.x.x.jar).

* Clique em Open.

4. **Início da Aplicação:**

* No painel "Projects" do NetBeans, localize o arquivo SistemaVendasGUI.java dentro da estrutura: Source Packages > vendasdeingresso.ui.

* Clique com o botão direito do mouse sobre SistemaVendasGUI.java.

* Selecione a opção Run File (Executar Arquivo).

* A interface gráfica do sistema será exibida, pronta para uso.
