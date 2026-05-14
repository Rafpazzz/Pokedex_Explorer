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

O **Pokedex Explorer** busca informacoes de um Pokemon pelo nome usando a API publica da PokeAPI. A aplicacao recebe o JSON bruto, usa Gson para desserializar os dados em DTOs e converte esses DTOs em modelos internos mais adequados para uso pela aplicacao.

No estado atual, o Pokemon pesquisado esta definido diretamente no codigo, no arquivo `api/src/Main.java`:

```java
String name = "charizard";
```

Ao executar o programa, os dados do Pokemon sao impressos no terminal.

## Funcionalidades

- Consulta de Pokemon por nome na PokeAPI.
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

1. `Main` define o nome do Pokemon e instancia `Service` e `Conection`.
2. `Service.findPokeByName` chama `Conection.responseAPI`.
3. `Conection` monta a URL final da PokeAPI e executa uma requisicao HTTP GET.
4. `Service` desserializa o JSON para `PokeDetailsDTO` usando Gson.
5. `Pokemon` recebe o DTO, extrai os dados relevantes e calcula as estatisticas por nivel.
6. `Main` imprime o resultado no console.

## Arquitetura

### `Main`

Ponto de entrada da aplicacao. Hoje ele define o Pokemon pesquisado diretamente no codigo e exibe o resultado retornado pelo servico.

### `client.Conection`

Responsavel por montar a URL da PokeAPI e fazer a chamada HTTP. A URL base usada e:

```text
https://pokeapi.co/api/v2/pokemon/
```

Observacao: o nome da classe esta escrito como `Conection`. Em ingles, a grafia mais comum seria `Connection`.

### `service.Service`

Camada intermediaria entre a entrada da aplicacao, o cliente HTTP e os modelos. Ela:

- solicita o JSON ao cliente HTTP;
- converte o JSON para `PokeDetailsDTO`;
- cria uma instancia de `Pokemon`;
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
Pokemon{
 id=6
, name='charizard
, level=42
, weight=905.0
, types=[fire, flying]
, base_stats=PokemonStats{hp=78, Attack=84, SpecialAttack=109, Defence=78, SpecialDefence=85, Speed=100}
, level_stats=PokemonStats{hp=117, Attack=75, SpecialAttack=96, Defence=70, SpecialDefence=76, Speed=89}
}
```

## Boas praticas e melhorias futuras

- Receber o nome do Pokemon por argumento de linha de comando ou entrada do usuario.
- Renomear `Conection` para `Connection` ou `PokeApiClient`.
- Padronizar nomes de pacotes em minusculo, por exemplo `models` em vez de `Models`.
- Evitar retornar `null` em caso de erro; considerar `Optional<Pokemon>` ou excecoes de dominio.
- Tratar respostas HTTP diferentes de `200 OK`.
- Validar nomes vazios ou invalidos antes da chamada HTTP.
- Criar testes unitarios para conversao de estatisticas e mapeamento de DTOs.
- Usar Maven ou Gradle para gerenciar dependencias e padronizar build.
- Remover do versionamento arquivos gerados, como `out/production`, se o objetivo for manter apenas codigo-fonte.
- Corrigir mensagens de erro para melhorar clareza e consistencia.

## Licenca

Este projeto esta licenciado sob os termos da licenca MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
