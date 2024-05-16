***
# **ChocoStock - O doce controle de vendas e estoque**
## Descrição
- Este é um projeto de um sistema de controle de estoque e vendas para uma fábrica de chocolate, desenvolvido em Java como parte de um Projeto Prático da matéria **MC302 - Programação Orientada a Objetos do Instituto de Computação da UNICAMP** no ano de 2024. O sistema permite gerenciar o estoque de produtos da fábrica, realizar vendas e controlar o fluxo de entrada e saída de produtos.

## Desenvolvedores
- [Ainaras Marão](https://github.com/MaraoLT) - 182338
- [Douglas Pereira](https://github.com/Dourialp) - 245202
- [Matheus Veiga](https://github.com/mvl18) - 269494
- [Yan Oliveira](https://github.com/Cl4nyz) - 236363

## Motivação
- A motivação por trás deste projeto é criar uma ferramenta eficiente para auxiliar na gestão do estoque e das vendas de uma fábrica de chocolate. Automatizar esses processos pode aumentar a eficiência operacional e reduzir erros humanos.

## Aplicação Cotidiana
Este sistema pode ser utilizado no cotidiano da fábrica de chocolate para realizar as seguintes tarefas:
- Registro dos clientes da loja da fábrica.
- Registro de matéria prima no estoque, assim como os dados do fornecedor daquele material.
- Registro de todos os produtos finalizados e que agora fazem parte do estoque de vendas.
- Registro de novos pedidos de clientes, assim como todo o controle para saber se o pedido já foi pago ou enviado ao cliente.
- Verificar a disponibilidade de produtos no estoque.
- Atualizar a quantidade de produtos e de matéria prima disponíveis no estoque, a fim de facilitar o controle.
- Histórico de todos os pedidos, bem como a capacidade de gerar relatórios de venda e estoque.

## [Diagrama UML do Projeto](https://app.diagrams.net/#G1ob392-avmraMdT-HdXfV2x8NyNQ4EVuE#%7B%22pageId%22%3A%22X-9vTq_ojHjCq0rzShb6%22%7D)
![Diagrama UML](imagens/ChocoStock_UML.png)

### Classes
- `Loja`:
- `Endereço`:
- `Pedido`:
- `Colaborador`:
- `Cliente`:
- `Fornecedor`:
- `Funcionario`:
- `Estoque`:
- `Item`:
- `Equipamento`:
- `Material`:
- `Ingrediente`:
- `Embalagem`:
- `Produto`:
- `Caixa`:
- `Chocolate`:

### Enums
- `Estados`: Lista as unidades federativas possíveis para os endereços.
- `Status`: Seta o status do pedido.
- `TiposEmbalagens`: Enumera os tipos de embalagem que a fábrica precisa comprar.
- `TiposIngredientes`: Enumera os tipos de ingredientes que a fábrica precisa comprar.
- `TiposChocolates`: Enumera todos os tipos de chocolate que a fábrica produz.
- `TiposComplementos`: Enumera todos os complementos para os chocolates.
- `TiposCaixas`: Enumera todos os tipos de caixa que a fábrica vende.

### Interfaces
- `AddRemove`: Interface para adicionar ou remover objetos de uma lista de objetos.
- `Escolhivel`: Interface para escolher um objeto de uma lista de objetos.
- `Codificavel`: Verifica se o objeto possui código.
- `Identificavel`: Verifica se o objeto possui ID.
- `Nomeavel`: Verifica se o objeto possui nome.

## Heranças e Relações
O projeto utiliza herança e relações entre as classes para organizar e facilitar o desenvolvimento do sistema:

- No diagrama UML as heranças e relações são represntadas da seguinte forma:
	- **Herança:** linha branca com um triangulo vazio. 
	- **Associação:** linha verde simples.
	- **Agregação:** linha verde com um losango vazio no lado do "todo".
	- **Composição:** linha verde com um losango preenchido no lado do "todo".
	- **Dependência:** linha verde tracejada com uma seta.
	- **Interface:** linha laranja com uma circunferência.
	- **Enumeração:** linha laranja simples.

## Como Usar
Para utilizar este sistema, siga os passos abaixo:
1. Clone o repositório para a sua máquina local.
2. Abra o projeto em sua IDE favorita.
3. Compile e execute o arquivo principal `Main.java`.
4. Siga as instruções no console para interagir com o sistema.

## Contribuição
Contribuições são bem-vindas! Se você identificar algum problema ou tiver sugestões de melhorias, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Futuro do projeto
- [ ] Implementar interface gráfica.
- [ ] Implementar a classe Insumos, que abrangerá os gastos da fábrica com toda a matéria prima, pessoal, energia, água...
