package br.com.alura.artistaMusica.principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.alura.artistaMusica.model.Artista;
import br.com.alura.artistaMusica.model.Musica;
import br.com.alura.artistaMusica.model.TipoArtista;
import br.com.alura.artistaMusica.repository.ArtistaRepository;
import br.com.alura.artistaMusica.service.ConsumoAPIMariTalkAI;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ArtistaRepository repositorio;
    private List<Artista> artistasCadastrados = new ArrayList<>();
    private List<Musica> musicasCadastradas = new ArrayList<>();
 
    
	public Principal(ArtistaRepository repositorio) {
		this.repositorio = repositorio;
	}

	public void exibeMenu() throws IOException {
	      var  opcao = -1;
	        var menu = """
	                1 - Cadastrar artistas
	                2 - Cadastrar músicas
	                3 - Listar artistas
	                4 - Listar músicas
	                5 - Buscar música por artista
	                6 - Pesquisar dados sobre um artista
	                0 - Sair
	                """;

	        while (opcao != 0) {
	     		System.out.println("\n\n");
	        	System.out.println(menu);
	            opcao = leitura.nextInt();
	            leitura.nextLine();

	            switch (opcao) {
	                case 1:
	                	cadastrarArtistas();
	                    break;
	                case 2:
	                	cadastrarMusicas();
	                    break;
	                case 3:
	                	listarArtistas();
	                    break;
	                case 4:
	                	listarMusicas();
	                    break;
	                case 5:
	                	buscarMusicasPorArtista();
	                    break;
	                case 6:
	                	pesquisarDadosDoArtista();
	                    break;
	                case 0:
	                    System.out.println("Encerrando a aplicação!");
	                    break;
	                default:
	                    System.out.println("Opção inválida!");
	            }
	        }
	    }

	private void pesquisarDadosDoArtista() throws IOException {
		System.out.println("Informe o nome do artista: ");
		String nome = reader.readLine();
		String dadosArtista = ConsumoAPIMariTalkAI.sendChatRequest(nome);
        try {
            JSONObject jsonObject = new JSONObject(dadosArtista);
            String resposta = jsonObject.optString("answer");
            System.out.println(resposta);
            // Iterate over the array and print each hobby
//            for (int i = 0; i < hobbiesArray.length(); i++) {
//                String hobby = hobbiesArray.getString(i);
//                System.out.println(hobby);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(dadosArtista);
//        Gson gson = new Gson();
//        Response responseObj = gson.fromJson(dadosArtista, Response.class);
//        System.out.println(responseObj.getAnswer()); 
	}

	private void buscarMusicasPorArtista() {
		System.out.println("Informe o nome do artista: ");
		String nome = leitura.nextLine();
		Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
		if (artista.isPresent()) {
			Artista artistaEncontrado = artista.get();
			System.out.println("Artista: " + artistaEncontrado.getNome());
			System.out.println("Músicas:");
			artistaEncontrado.getMusicas().forEach(System.out::println);
		} else {
			System.out.println("Artista não encontrado!");
			return;
		}
	}

	private void listarArtistas() {
		artistasCadastrados = repositorio.findAll();
		artistasCadastrados.forEach(System.out::println);
	}
	
	private void listarMusicas() {
//		artistasCadastrados = repositorio.findAll();
//		artistasCadastrados.forEach(a -> a.getMusicas().forEach(System.out::println));
		musicasCadastradas = repositorio.buscarTodasMusicas();
		musicasCadastradas.forEach(a -> System.out.println(a.getNome() + " (" + a.getArtista().getNome() + ")"  ));
		
	}

	private void cadastrarMusicas() {
		System.out.println("Informe o nome do artista: ");
		String nome = leitura.nextLine();
		Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
		if (artista.isPresent()) {
			Artista artistaEncontrado = artista.get();
			System.out.println("Informe os nomes das músicas, separadas por virgulas:");
			var aux = leitura.nextLine();
			List<Musica> lista = new ArrayList<>();
			List<String> musicas = Arrays.asList(aux.split(","));
			for (int i=0; i< musicas.size(); i++) {
				Musica musica = new Musica(musicas.get(i).trim());
				lista.add(musica);
			}
			artistaEncontrado.setMusicas(lista);
			repositorio.save(artistaEncontrado);
		} else {
			System.out.println("Artista não cadastrado!");
		}
		
	}		

	private void cadastrarArtistas() {
		String cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("S")) {
			System.out.println("Nome do artista:");
	        var nome = leitura.nextLine();
	        Boolean tentaNovamente = true;
	        while (tentaNovamente) {
		        try {
		        	System.out.println("Qual é o tipo do artista? (Solo/Dupla/Banda)");
		        	var estilo = leitura.nextLine();
	//	        	tipoArtista = TipoArtista.fromString(estilo);
		        	TipoArtista tipo = TipoArtista.valueOf(estilo.toUpperCase());
		        	tentaNovamente = false;
		           	Artista artista = new Artista(nome, tipo);
		            repositorio.save(artista);
		        } catch(IllegalArgumentException e) {
		        	System.out.println("Tipo incorreto!");
		        }
			}
        	System.out.println("Cadastrar outro artista? (S/N)");
        	cadastrarNovo = leitura.nextLine();
        }
	}        
}
