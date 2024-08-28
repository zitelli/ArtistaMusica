package br.com.alura.artistaMusica.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.artistaMusica.model.Artista;
import br.com.alura.artistaMusica.model.Musica;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

   @Query(value = "select m from Artista a join a.musicas m where a.nome ilike %:nome%")
   List<Musica> buscarMusicasPorArtista(String nome);

   Optional<Artista> findByNomeContainingIgnoreCase(String nome);
   
   @Query(value = "select m from Artista a join a.musicas m where 1 = 1")
   List<Musica> buscarTodasMusicas();
    
}
