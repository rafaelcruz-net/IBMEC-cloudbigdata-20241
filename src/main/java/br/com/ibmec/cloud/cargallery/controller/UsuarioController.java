package br.com.ibmec.cloud.cargallery.controller;


import br.com.ibmec.cloud.cargallery.controller.request.LoginRequest;
import br.com.ibmec.cloud.cargallery.models.Usuario;
import br.com.ibmec.cloud.cargallery.models.UsuarioLista;
import br.com.ibmec.cloud.cargallery.repository.UsuarioListaRepository;
import br.com.ibmec.cloud.cargallery.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioListaRepository usuarioListaRepository;

    private final String DEFAULT_LISTA_DESEJA = "Carros Favoritos";



    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {

        if (this.repository.findUsuarioByEmail(usuario.getEmail()).isEmpty() == false) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Criar a lista default do usuario
        UsuarioLista lista = new UsuarioLista();
        lista.setId(UUID.randomUUID());
        lista.setNome(this.DEFAULT_LISTA_DESEJA);
        lista.setUsuario(usuario);

        usuario.getListaDesejo().add(lista);

        this.repository.save(usuario);
        this.usuarioListaRepository.save(lista);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> obter(@PathVariable("id") UUID id) {
        return this.repository.findById(id).map(usuario -> {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@Valid @RequestBody LoginRequest request) {
        Optional<Usuario> optUsuario = this.repository.findUsuarioByEmailAndSenha(request.getEmail(), request.getSenha());

        return optUsuario.map(usuario -> {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
