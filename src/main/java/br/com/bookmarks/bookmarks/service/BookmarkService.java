package br.com.bookmarks.bookmarks.service;

import br.com.bookmarks.bookmarks.dto.BookmarkCreateDTO;
import br.com.bookmarks.bookmarks.dto.BookmarkResponseDTO;
import br.com.bookmarks.bookmarks.dto.BookmarkUpDateDTO;
import br.com.bookmarks.bookmarks.model.entity.Bookmark;
import br.com.bookmarks.bookmarks.repository.BookmarkRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class
BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    // BookmarkService.java

    public BookmarkResponseDTO criarBookmark(BookmarkCreateDTO dto) {
        // 1. DTO -> Entidade: Convertendo o DTO de entrada em uma entidade JPA.
        // Criamos um novo objeto Bookmark que será salvo no banco.
        Bookmark novaEntidade = new Bookmark();
        novaEntidade.setUrl(dto.url()); // Usamos os getters do record
        novaEntidade.setTitle(dto.title());
        novaEntidade.setDescription(dto.description());

        // 2. Salvando a Entidade: Usamos o repositório para persistir a nova entidade.
        // O metodo save() do JpaRepository retorna a entidade salva (agora com um ID).
        Bookmark entidadeSalva = bookmarkRepository.save(novaEntidade);

        // 3. Entidade -> DTO: Convertendo a entidade salva para o DTO de resposta.
        // Usamos nosso metodo "ajudante" para criar o DTO de resposta.
        return toResponseDTO(entidadeSalva);
    }

    public List<BookmarkResponseDTO> listBookmarks() {
        // 1. Busque a lista de ENTIDADES do repositório
        List<Bookmark> todasAsEntidades = bookmarkRepository.findAll();

        // 2. Converta a lista para um Stream, aplique o map, e colete o resultado
        return todasAsEntidades.stream() // <-- Converte a lista para um fluxo de dados
                .map(this::toResponseDTO)   // <-- Aplica a conversão a cada item do fluxo
                .toList();                  // <-- Coleta os itens convertidos em uma nova lista
    }

    public Bookmark findBookmarkById(Long id) {
        return bookmarkRepository.findBookmarkById(id);
    }

    public Optional<BookmarkResponseDTO> findBookmarkByTitle(String title) {
        // Atualize o nome da chamada aqui também
        return bookmarkRepository.findByTitleContainingIgnoreCase(title)
                .map(this::toResponseDTO);
    }

    // CORREÇÃO 1: Ajuste no tipo do parâmetro DTO
    public BookmarkResponseDTO editBookmark(Long id, BookmarkUpDateDTO.BookmarkUpdateDTO dto) {
        // 1. Buscar a Entidade: Primeiro, tentamos encontrar o bookmark no banco de dados.
        // O .orElseThrow() vai lançar uma exceção se o bookmark não for encontrado.

        // CORREÇÃO 2: Use o método padrão "findById" em vez de "findBookmarkById"
        Bookmark bookmarkExistente = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark não encontrado com o id: " + id));

        // 2. Atualizar os Campos: Se encontrado, atualizamos os campos da entidade com os dados do DTO.
        // CORREÇÃO 3: Verifique se os nomes dos campos (getters) correspondem aos do seu Record DTO
        bookmarkExistente.setUrl(dto.url());
        bookmarkExistente.setTitle(dto.title()); // Ajustado de title para titulo
        bookmarkExistente.setDescription(dto.description()); // Ajustado de description para descricao

        // 3. Salvar a Entidade Atualizada
        Bookmark entidadeAtualizada = bookmarkRepository.save(bookmarkExistente);

        // 4. Retornar o DTO de Resposta
        return toResponseDTO(entidadeAtualizada);
    }

    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }

    private BookmarkResponseDTO toResponseDTO(Bookmark bookmark){
        return new BookmarkResponseDTO(
                bookmark.getId(),
                bookmark.getUrl(),
                bookmark.getTitle(),
                bookmark.getDescription()
        );
    }
}
