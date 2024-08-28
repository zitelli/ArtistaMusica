package br.com.alura.artistaMusica.model;

public enum TipoArtista {
    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");
    
    private String tipoOmdb;
	
	TipoArtista(String tipoOmdb) {
		this.tipoOmdb = tipoOmdb;
	}

	public static TipoArtista fromString(String text) {
	    for (TipoArtista tipoArtista : TipoArtista.values()) {
	        if (tipoArtista.tipoOmdb.equalsIgnoreCase(text)) {
	            return tipoArtista;
	        }
	    }
	    throw new IllegalArgumentException("Nenhum tipo encontrado para a string fornecida: " + text);
	}	
}
