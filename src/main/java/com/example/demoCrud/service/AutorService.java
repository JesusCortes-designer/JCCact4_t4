package com.example.demoCrud.service;

import com.example.demoCrud.dto.AutorRequest;
import com.example.demoCrud.dto.AutorResponse;
import com.example.demoCrud.dto.PageResponse;
import com.example.demoCrud.exception.ApiException;
import com.example.demoCrud.models.Autor;
import com.example.demoCrud.repository.AutorRepository;
import com.example.demoCrud.repository.LibroRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public AutorService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<AutorResponse> listar(int pagina, int tamanio) {
        PageRequest pageable = PageRequest.of(Math.max(pagina, 0), Math.min(Math.max(tamanio, 1), 100), Sort.by("id"));
        return PageResponse.from(autorRepository.findAll(pageable).map(AutorResponse::from));
    }

    @Transactional(readOnly = true)
    public AutorResponse obtener(Long id) {
        return AutorResponse.from(buscar(id));
    }

    @Transactional
    public AutorResponse crear(AutorRequest req) {
        Autor autor = new Autor();
        autor.setNombre(req.getNombre().trim());
        autor.setNacionalidad(req.getNacionalidad());
        return AutorResponse.from(autorRepository.save(autor));
    }

    @Transactional
    public AutorResponse actualizar(Long id, AutorRequest req) {
        Autor autor = buscar(id);
        autor.setNombre(req.getNombre().trim());
        autor.setNacionalidad(req.getNacionalidad());
        return AutorResponse.from(autorRepository.save(autor));
    }

    @Transactional
    public void eliminar(Long id) {
        Autor autor = buscar(id);
        if (libroRepository.existsByAutorId(id)) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "No se puede eliminar el autor porque tiene libros registrados");
        }
        autorRepository.delete(autor);
    }

    private Autor buscar(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Autor no encontrado: " + id));
    }
}
