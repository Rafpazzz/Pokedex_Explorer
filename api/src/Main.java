import Models.Pokemon;
import Models.PokemonTypes;
import client.Conection;
import service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int LIMITE_TIME = 6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service service = new Service();
        Conection conection = new Conection();
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon ultimoPokemonBuscado = null;
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    ultimoPokemonBuscado = buscarPokemonPorNome(scanner, service, conection);
                    exibirPokemonEncontrado(ultimoPokemonBuscado);
                    break;
                case 2:
                    ultimoPokemonBuscado = buscarPokemonPorNumero(scanner, service, conection);
                    exibirPokemonEncontrado(ultimoPokemonBuscado);
                    break;
                case 3:
                    adicionarPokemonNaLista(pokemons, ultimoPokemonBuscado);
                    break;
                case 4:
                    listarTodos(pokemons);
                    break;
                case 5:
                    listarPeloNome(scanner, pokemons);
                    break;
                case 6:
                    listarPeloTipo(scanner, pokemons);
                    break;
                case 7:
                    compararPokemons(scanner, service, pokemons);
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== Pokedex Explorer =====");
        System.out.println("1 - Buscar pokemon pelo nome");
        System.out.println("2 - Buscar pokemon pelo numero da pokedex");
        System.out.println("3 - Adicionar ultimo pokemon buscado na lista de 6 pokemons");
        System.out.println("4 - Listar todos os pokemons da lista");
        System.out.println("5 - Listar pokemon da lista pelo nome");
        System.out.println("6 - Listar pokemons da lista pelo tipo");
        System.out.println("7 - Comparar dois pokemons da lista pelo nome");
        System.out.println("0 - Sair");
    }

    private static Pokemon buscarPokemonPorNome(Scanner scanner, Service service, Conection conection) {
        System.out.print("Digite o nome do pokemon: ");
        String nome = scanner.nextLine().trim().toLowerCase();

        if (nome.isEmpty()) {
            System.out.println("Nome invalido.");
            return null;
        }

        return service.findPokeByName(nome, conection);
    }

    private static Pokemon buscarPokemonPorNumero(Scanner scanner, Service service, Conection conection) {
        int numero = lerInteiro(scanner, "Digite o numero da pokedex: ");
        return service.findPokeById(numero, conection);
    }

    private static void exibirPokemonEncontrado(Pokemon pokemon) {
        if (pokemon == null) {
            System.out.println("Pokemon nao encontrado.");
            return;
        }

        System.out.println("Pokemon encontrado:");
        System.out.println(pokemon);
    }

    private static void adicionarPokemonNaLista(List<Pokemon> pokemons, Pokemon pokemon) {
        if (pokemon == null) {
            System.out.println("Busque um pokemon antes de adicionar na lista.");
            return;
        }

        if (pokemons.size() >= LIMITE_TIME) {
            System.out.println("A lista ja possui 6 pokemons.");
            return;
        }

        if (existeNaLista(pokemons, pokemon.getGame_index())) {
            System.out.println("Esse pokemon ja esta na lista.");
            return;
        }

        pokemons.add(pokemon);
        System.out.println(pokemon.getName() + " foi adicionado na lista.");
    }

    private static void listarTodos(List<Pokemon> pokemons) {
        if (listaVazia(pokemons)) {
            return;
        }

        for (Pokemon pokemon : pokemons) {
            System.out.println(pokemon);
        }
    }

    private static void listarPeloNome(Scanner scanner, List<Pokemon> pokemons) {
        if (listaVazia(pokemons)) {
            return;
        }

        System.out.print("Digite o nome do pokemon: ");
        String nome = scanner.nextLine().trim();
        Pokemon pokemon = encontrarPorNome(pokemons, nome);

        if (pokemon == null) {
            System.out.println("Pokemon nao encontrado na lista.");
            return;
        }

        System.out.println(pokemon);
    }

    private static void listarPeloTipo(Scanner scanner, List<Pokemon> pokemons) {
        if (listaVazia(pokemons)) {
            return;
        }

        System.out.print("Digite o tipo do pokemon: ");
        String tipoBuscado = scanner.nextLine().trim();
        boolean encontrou = false;

        for (Pokemon pokemon : pokemons) {
            if (pokemonPossuiTipo(pokemon, tipoBuscado)) {
                System.out.println(pokemon);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum pokemon desse tipo foi encontrado na lista.");
        }
    }

    private static void compararPokemons(Scanner scanner, Service service, List<Pokemon> pokemons) {
        if (pokemons.size() < 2) {
            System.out.println("Adicione pelo menos 2 pokemons na lista para comparar.");
            return;
        }

        System.out.print("Digite o nome do primeiro pokemon: ");
        String primeiroNome = scanner.nextLine().trim();
        System.out.print("Digite o nome do segundo pokemon: ");
        String segundoNome = scanner.nextLine().trim();

        Pokemon primeiroPokemon = encontrarPorNome(pokemons, primeiroNome);
        Pokemon segundoPokemon = encontrarPorNome(pokemons, segundoNome);

        if (primeiroPokemon == null || segundoPokemon == null) {
            System.out.println("Os dois pokemons precisam estar na lista.");
            return;
        }

        System.out.println(service.comparePokemon(primeiroPokemon, segundoPokemon));
    }

    private static Pokemon encontrarPorNome(List<Pokemon> pokemons, String nome) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getName().equalsIgnoreCase(nome)) {
                return pokemon;
            }
        }

        return null;
    }

    private static boolean pokemonPossuiTipo(Pokemon pokemon, String tipoBuscado) {
        for (PokemonTypes tipo : pokemon.getTypes()) {
            if (tipo.getName().equalsIgnoreCase(tipoBuscado)) {
                return true;
            }
        }

        return false;
    }

    private static boolean existeNaLista(List<Pokemon> pokemons, int numeroPokedex) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getGame_index() == numeroPokedex) {
                return true;
            }
        }

        return false;
    }

    private static boolean listaVazia(List<Pokemon> pokemons) {
        if (pokemons.isEmpty()) {
            System.out.println("A lista de pokemons esta vazia.");
            return true;
        }

        return false;
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
            }
        }
    }
}
