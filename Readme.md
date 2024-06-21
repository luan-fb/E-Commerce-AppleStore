<h1 align="center">AppleStore</h1>

<p align="center">
  <img src="https://github.com/luan-fb/E-Commerce-AppleStore/blob/main/src/main/resources/img/LogoAppleStore.png" alt="AppleStore Logo" width="350">
</p>

## Descrição do Projeto
O AppleStore é um sistema de e-commerce desenvolvido como trabalho final das disciplinas de Linguagens de Programação (LP) e Programação Orientada a Objetos (POO). Foi desenvolvido com o objetivo de oferecer aos usuários uma plataforma intuitiva para navegar, visualizar e comprar produtos da Apple online.
Proporcionando uma experiência de compra online que atenda aos padrões de qualidade da Apple, com foco na usabilidade e na segurança das transações.

## Funcionalidades Telas

### Tela de Login
- Permite que usuários façam login no sistema.
- Usuários podem optar por entrar como cliente ou administrador.

### Tela de Cadastro
- Permite que novos usuários se cadastrem no sistema.
- Coleta informações básicas como nome, email, senha e tipo de usuário.

### Tela de Administração
- Administradores podem gerenciar produtos (adicionar, remover, atualizar).
- Administradores podem gerenciar usuários (adicionar, remover, alterar).
- Administradores podem visualizar todos os pedidos realizados.

### Tela Vitrine
- Exibe a lista de produtos disponíveis para compra.
- Usuários podem adicionar produtos ao carrinho.
- Usuários podem ver detalhes do produto (descrição, preço, estoque).

### Tela Carrinho
- Permite que os usuários visualizem os produtos adicionados ao carrinho.
- Usuários podem alterar a quantidade de produtos ou removê-los do carrinho.
- Exibe o total da compra.

### Tela Pagamento
- Usuários podem selecionar a forma de pagamento.
- Exibe o resumo da compra e permite confirmar o pagamento.

## Demonstração
<h3 align="center">Tela Login</h3>
<p align="center">
  <img src="link-para-gif-login.gif" alt="Tela de Login">
</p>

<h3 align="center">Tela Cadastro</h3>
<p align="center">
  <img src="link-para-gif-admin.gif" alt="Tela de Administração">
</p>

<h3 align="center">Tela Vitrine</h3>
<p align="center">
  <img src="link-para-gif-vitrine.gif" alt="Tela Vitrine">
</p>

<h3 align="center">Tela Admin</h3>
<p align="center">
  <img src="link-para-gif-admin.gif" alt="Tela de Administração">
</p>

<h3 align="center">Tela Carrinho</h3>
<p align="center">
  <img src="link-para-gif-admin.gif" alt="Tela de Administração">
</p>

<h3 align="center">Tela Pagamento</h3>
<p align="center">
  <img src="link-para-gif-admin.gif" alt="Tela de Administração">
</p>

## Diagrama ERD
<p align="center">
  <img src="https://github.com/luan-fb/E-Commerce-AppleStore/blob/main/src/main/resources/img/AppleStore_Diagrama.png" alt="Diagrama ERD">
</p>

## Estrutura do Projeto

├── src                        
│ ├── main       
│ │ ├── java  
│ │ │ ├── controlador   
│ │ │ │ └── UsuarioController.java    
│ │ │ ├── dao   
│ │ │ │ ├── CarrinhoDao.java  
│ │ │ │ ├── FormaPagamentoDao.java  
│ │ │ │ ├── ItemCarrinhoDao.java   
│ │ │ │ ├── PedidoDao.java    
│ │ │ │ ├── ProdutoDao.java    
│ │ │ │ └── UsuarioDao.java    
│ │ │ ├── entidades    
│ │ │ │ ├── Carrinho.java    
│ │ │ │ ├── FormaPagamento.java    
│ │ │ │ ├── ItemCarrinho.java    
│ │ │ │ ├── Pedido.java    
│ │ │ │ ├── Produto.java   
│ │ │ │ └── Usuario.java    
│ │ │ ├── exception   
│ │ │ │ ├── DadoInvalidoException.java    
│ │ │ │ └── DadoNuloException.java   
│ │ │ ├── servico   
│ │ │ │ ├── CarrinhoService.java  
│ │ │ │ ├── FormaPagamentoService.java     
│ │ │ │ ├── PedidoService.java   
│ │ │ │ ├── ProdutoService.java   
│ │ │ │ └── UsuarioService.java   
│ │ │ ├── util   
│ │ │ │ └── HibernateUtil.java   
│ │ │ └── view   
│ │ │ ├── TelaAdmin.java   
│ │ │ ├── TelaCadastro.java   
│ │ │ ├── TelaCarrinho.java   
│ │ │ ├── TelaLogin.java   
│ │ │ ├── TelaPagamento.java  
│ │ │ └── TelaVitrine.java    
│ │ └── resources   
│ │ ├── img   
│ │ │ └── [imagens do projeto]    
│ │ └── application.properties   
├── .gitignore   
├── pom.xml   
└── README.md   



## Tecnologias Utilizadas
- **Java**: Linguagem de programação principal.
- **Hibernate**: Framework ORM para persistência de dados.
- **MySQL**: Banco de dados relacional.
- **Swing**: Biblioteca gráfica para a interface do usuário.
- **Maven**: Gerenciamento de dependências.

## Configuração do Ambiente

### Pré-requisitos
- **Java JDK** (versão 8 ou superior)
- **Maven**
- **MySQL** (ou outro banco de dados relacional)
- **IDE** (IntelliJ, Eclipse, etc.)

### Passos para Configuração
1. Clone o repositório:
    ```bash
    git clone https://github.com/luan-fb/E-Commerce-AppleStore.git
    cd E-Commerce-AppleStore
    ```

2. Configure o banco de dados MySQL:
    ```sql
    CREATE DATABASE AppleStore;
    USE AppleStore;
    -- Execute o script SQL fornecido para criar as tabelas e inserir os dados
    ```

3. Configure o arquivo `application.properties` com suas credenciais do banco de dados.

4. Compile o projeto usando Maven:
    ```bash
    mvn clean install
    ```

5. Execute o projeto:
    ```bash
    mvn exec:java -Dexec.mainClass="com.luan-fb.AppleStore.Main"
    ```

## Como Executar o Projeto
1. Abra a classe `TelaLogin` na sua IDE.
2. Execute o método `main`.

## Contribuição
Contribuições são sempre bem-vindas! Sinta-se à vontade para enviar um pull request com melhorias.


## Autores
- **[Luan Ferreira Boaventura](https://github.com/luan-fb)**
- **[Patrick Henrique Nunes De Paula]()**

## Contato
- **Email**: luanfboaventura@gmail.com
- **LinkedIn**: [Luan Ferreira](linkedin.com/in/luan-ferreira-671b03259)