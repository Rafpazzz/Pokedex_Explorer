# Pokedex Explorer

Aplicacao Java de console que consulta dados de Pokemon na [PokeAPI](https://pokeapi.co/) e transforma a resposta JSON em objetos de dominio com estatisticas, tipos, peso, identificador e um nivel aleatorio.

## Sumario

- [Sobre o projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Como executar](#como-executar)
- [Fluxo da aplicacao](#fluxo-da-aplicacao)
- [Arquitetura](#arquitetura)
- [Modelos e DTOs](#modelos-e-dtos)
- [Boas praticas e melhorias futuras](#boas-praticas-e-melhorias-futuras)
- [Licenca](#licenca)

## Sobre o projeto

O **Pokedex Explorer** busca informacoes de Pokemon usando a API publica da PokeAPI. A aplicacao recebe o JSON bruto, usa Gson para desserializar os dados em DTOs e converte esses DTOs em modelos internos mais adequados para uso pela aplicacao.

No estado atual, a aplicacao possui um menu interativo no arquivo `api/src/Main.java`, implementado com `do while` e `switch case`. Pelo menu, o usuario pode buscar Pokemon por nome ou numero da Pokedex, adicionar os resultados em uma lista de ate 6 Pokemon, listar os itens salvos e comparar dois Pokemon da lista.

## Funcionalidades

- Consulta de Pokemon por nome na PokeAPI.
- Consulta de Pokemon pelo numero da Pokedex.
- Timeout de 10 segundos nas chamadas HTTP para evitar travamento aparente do menu.
- Tratamento de respostas HTTP diferentes de `200 OK` como busca nao encontrada.
- Menu interativo de console com `do while` e `switch case`.
- Lista local com limite de 6 Pokemon.
- Adicao do ultimo Pokemon buscado na lista.
- Bloqueio de Pokemon duplicado na lista pelo numero da Pokedex.
- Listagem de todos os Pokemon salvos.
- Busca e listagem de Pokemon salvo pelo nome.
- Busca e listagem de Pokemon salvos pelo tipo.
- Comparacao de dois Pokemon da lista pelo nome.
- Conversao da resposta JSON para DTOs com Gson.
- Criacao de um modelo `Pokemon` a partir dos dados recebidos.
- Extracao de tipos do Pokemon.
- Extracao das estatisticas base.
- Geracao de nivel aleatorio entre 1 e 100.
- Calculo de estatisticas ajustadas pelo nivel.
- Impressao dos dados finais no console.

## Tecnologias

- Java 21
- Java HTTP Client (`java.net.http.HttpClient`)
- Gson 2.13.2
- PokeAPI
- IntelliJ IDEA

## Estrutura do projeto

```text
.
├── LICENSE
├── README.md
├── api
│   ├── api.iml
│   └── src
│       ├── Main.java
│       ├── Models
│       │   ├── Pokemon.java
│       │   ├── PokemonStats.java
│       │   └── PokemonTypes.java
│       ├── client
│       │   └── Conection.java
│       ├── dto
│       │   ├── NamedResource.java
│       │   ├── PokeDetailsDTO.java
│       │   ├── StatSlotDTO.java
│       │   └── TypeSlotDTO.java
│       └── service
│           └── Service.java
└── out
    └── production
```

### Principais diretorios

| Caminho | Responsabilidade |
| --- | --- |
| `api/src` | Codigo-fonte da aplicacao Java. |
| `api/src/client` | Camada responsavel pela comunicacao HTTP com a PokeAPI. |
| `api/src/service` | Camada de servico que coordena busca, conversao JSON e criacao do modelo. |
| `api/src/dto` | Records usados para mapear a estrutura da resposta da API. |
| `api/src/Models` | Modelos de dominio usados pela aplicacao. |
| `out/production` | Saida compilada gerada pela IDE. |

## Como executar

### Pre-requisitos

- JDK 21 instalado.
- Gson 2.13.2 disponivel no classpath.
- Conexao com a internet para acessar `https://pokeapi.co/api/v2/pokemon/`.

### Executando pelo IntelliJ IDEA

1. Abra o projeto no IntelliJ IDEA.
2. Confirme que o JDK do projeto esta configurado como Java 21.
3. Confirme que a biblioteca `gson-2.13.2.jar` esta adicionada ao modulo `api`.
4. Execute a classe `Main`.

### Executando pelo terminal

Se o Gson estiver no cache Maven local, use:

```bash
javac -cp "$HOME/.m2/repository/com/google/code/gson/gson/2.13.2/gson-2.13.2.jar" -d out/production/api $(find api/src -name "*.java")
java -cp "out/production/api:$HOME/.m2/repository/com/google/code/gson/gson/2.13.2/gson-2.13.2.jar" Main
```

Se o JAR estiver em outro local, substitua o caminho do Gson nos comandos acima.

## Fluxo da aplicacao

1. `Main` instancia `Scanner`, `Service`, `Conection` e uma lista local de Pokemon.
2. O menu e exibido em um `do while` ate o usuario escolher a opcao `0 - Sair`.
3. O `switch case` direciona a opcao escolhida para busca, adicao, listagem ou comparacao.
4. Nas buscas, `Service.findPokeByName` ou `Service.findPokeById` chama `Conection.responseAPI`.
5. `Conection` monta a URL final da PokeAPI e executa uma requisicao HTTP GET com timeout de 10 segundos.
6. Se a API retornar um status diferente de `200 OK`, a resposta e tratada como vazia.
7. `Service` valida a resposta e desserializa o JSON para `PokeDetailsDTO` usando Gson.
8. `Pokemon` recebe o DTO, extrai os dados relevantes e calcula as estatisticas por nivel.
9. `Main` exibe o resultado no console ou adiciona o ultimo Pokemon buscado na lista.

## Arquitetura

### `Main`

Ponto de entrada da aplicacao. Ele controla o menu interativo, guarda a lista local de ate 6 Pokemon e chama os servicos para buscar e comparar Pokemon.

Opcoes disponiveis no menu:

```text
1 - Buscar pokemon pelo nome
2 - Buscar pokemon pelo numero da pokedex
3 - Adicionar ultimo pokemon buscado na lista de 6 pokemons
4 - Listar todos os pokemons da lista
5 - Listar pokemon da lista pelo nome
6 - Listar pokemons da lista pelo tipo
7 - Comparar dois pokemons da lista pelo nome
0 - Sair
```

### `client.Conection`

Responsavel por montar a URL da PokeAPI e fazer a chamada HTTP. A chamada possui timeout de 10 segundos e retorna resposta vazia quando a API responde com status diferente de `200 OK`. A URL base usada e:

```text
https://pokeapi.co/api/v2/pokemon/
```

Observacao: o nome da classe esta escrito como `Conection`. Em ingles, a grafia mais comum seria `Connection`.

### `service.Service`

Camada intermediaria entre a entrada da aplicacao, o cliente HTTP e os modelos. Ela:

- solicita o JSON ao cliente HTTP;
- converte o JSON para `PokeDetailsDTO`;
- cria uma instancia de `Pokemon`;
- busca Pokemon por nome;
- busca Pokemon por numero da Pokedex;
- compara dois Pokemon usando a media das estatisticas ajustadas pelo nivel;
- valida resposta vazia ou invalida antes de criar o modelo;
- trata excecoes de entrada/saida e interrupcao.

### `Models.Pokemon`

Modelo principal da aplicacao. Representa um Pokemon com:

- identificador da PokeAPI;
- nome;
- nivel aleatorio;
- peso;
- tipos;
- estatisticas base;
- estatisticas ajustadas pelo nivel.

Tambem contem a regra de conversao das estatisticas base para estatisticas por nivel.

### `Models.PokemonStats`

Representa as estatisticas de batalha:

- HP;
- Attack;
- SpecialAttack;
- Defence;
- SpecialDefence;
- Speed.

### `Models.PokemonTypes`

Representa um tipo do Pokemon, como `fire`, `flying`, `water` ou `grass`.

## Modelos e DTOs

Os DTOs refletem apenas os campos da resposta da PokeAPI que a aplicacao usa.

| DTO | Finalidade |
| --- | --- |
| `PokeDetailsDTO` | Mapeia `id`, `name`, `weight`, `types` e `stats`. |
| `TypeSlotDTO` | Mapeia a posicao e o tipo do Pokemon. |
| `StatSlotDTO` | Mapeia estatistica base, effort e nome da estatistica. |
| `NamedResource` | Mapeia objetos da API com `name` e `url`. |

## Exemplo de saida

Como o nivel e aleatorio, os valores de `level_stats` podem mudar a cada execucao.

```text
===== Pokedex Explorer =====
1 - Buscar pokemon pelo nome
2 - Buscar pokemon pelo numero da pokedex
3 - Adicionar ultimo pokemon buscado na lista de 6 pokemons
4 - Listar todos os pokemons da lista
5 - Listar pokemon da lista pelo nome
6 - Listar pokemons da lista pelo tipo
7 - Comparar dois pokemons da lista pelo nome
0 - Sair
Escolha uma opcao: 1
Digite o nome do pokemon: charizard
Pokemon encontrado:

==============================
Pokemon: CHARIZARD
Numero da Pokedex: 6
Nivel: 42
Peso: 905.0
Tipos: [fire, flying]

Estatisticas Base:
  HP: 78
  Ataque: 84
  Ataque Especial: 109
  Defesa: 78
  Defesa Especial: 85
  Velocidade: 100

Estatisticas no Nivel 42:
  HP: 117
  Ataque: 75
  Ataque Especial: 96
  Defesa: 70
  Defesa Especial: 76
  Velocidade: 89
==============================
```

## Boas praticas e melhorias futuras

- Renomear `Conection` para `Connection` ou `PokeApiClient`.
- Padronizar nomes de pacotes em minusculo, por exemplo `models` em vez de `Models`.
- Evitar retornar `null` em caso de erro; considerar `Optional<Pokemon>` ou excecoes de dominio.
- Criar testes unitarios para conversao de estatisticas e mapeamento de DTOs.
- Usar Maven ou Gradle para gerenciar dependencias e padronizar build.
- Remover do versionamento arquivos gerados, como `out/production`, se o objetivo for manter apenas codigo-fonte.
- Corrigir mensagens de erro para melhorar clareza e consistencia.

## Licenca

Este projeto esta licenciado sob os termos da licenca MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
