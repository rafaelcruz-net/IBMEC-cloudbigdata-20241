package br.com.ibmec.cloud.cargallery.controller;


import br.com.ibmec.cloud.cargallery.controller.request.LoginRequest;
import br.com.ibmec.cloud.cargallery.controller.request.UsuarioListaRequest;
import br.com.ibmec.cloud.cargallery.models.Carro;
import br.com.ibmec.cloud.cargallery.models.Usuario;
import br.com.ibmec.cloud.cargallery.models.UsuarioLista;
import br.com.ibmec.cloud.cargallery.repository.CarroRepository;
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

    @Autowired
    private CarroRepository carroRepository;

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

    @PostMapping("{id}/favoritar/{idCarro}")
    public ResponseEntity favoritar(@PathVariable("id") UUID id, @PathVariable("idCarro") UUID idCarro) {

        //Faço as buscas do usuário e carro
        Optional<Usuario> optUsuario = this.repository.findById(id);

        Optional<Carro> optCarro = this.carroRepository.findById(idCarro);

        //Caso não ache o usuário, retornar um 404
        if (optUsuario.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Caso não ache o carro a ser associado retornar um 404
        if (optCarro.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optUsuario.get();
        Carro carro = optCarro.get();

        usuario.getListaDesejo().get(0).getCarros().add(carro);
        usuarioListaRepository.save(usuario.getListaDesejo().get(0));

        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @PostMapping("{id}/desfavoritar/{idCarro}")
    public ResponseEntity desfavoritar(@PathVariable("id") UUID id, @PathVariable("idCarro") UUID idCarro) {

        //Faço as buscas do usuário e carro
        Optional<Usuario> optUsuario = this.repository.findById(id);

        Optional<Carro> optCarro = this.carroRepository.findById(idCarro);

        //Caso não ache o usuário, retornar um 404
        if (optUsuario.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Caso não ache o carro a ser associado retornar um 404
        if (optCarro.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optUsuario.get();
        Carro carro = optCarro.get();

        for(Carro item : usuario.getListaDesejo().get(0).getCarros()) {
            if (item.getId() == carro.getId()) {
                usuario.getListaDesejo().get(0).getCarros().remove(carro);
                break;
            }
        }
        usuarioListaRepository.save(usuario.getListaDesejo().get(0));
        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @PostMapping("{id}/criar-lista")
    public ResponseEntity criarLista(@PathVariable("id") UUID id, @Valid @RequestBody UsuarioListaRequest request) {
        Optional<Usuario> optUsuario = this.repository.findById(id);

        //Caso não ache o usuário, retornar um 404
        if (optUsuario.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optUsuario.get();

        UsuarioLista usuarioLista = new UsuarioLista();
        usuarioLista.setUsuario(usuario);
        usuarioLista.setNome(request.getNome());

        usuarioListaRepository.save(usuarioLista);

        return new ResponseEntity(usuario, HttpStatus.OK);

    }

    @PostMapping("{id}/lista/{idLista}/adicionar/{idCarro}")
    public ResponseEntity adicionarCarroLista(@PathVariable("id") UUID id, @PathVariable("idLista") UUID idLista, @PathVariable("idCarro") UUID idCarro) {
        //Faço as buscas do usuário, carro e lista
        Optional<Usuario> optUsuario = this.repository.findById(id);
        Optional<Carro> optCarro = this.carroRepository.findById(idCarro);
        Optional<UsuarioLista> optUsuarioLista = this.usuarioListaRepository.findById(idLista);

        //Caso não ache o usuário, retornar um 404
        if (optUsuario.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Caso não ache o a lista a ser associada o carro retornar um 404
        if (optUsuarioLista.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Caso não ache o carro a ser associado retornar um 404
        if (optCarro.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        UsuarioLista lista = optUsuarioLista.get();
        Carro carro = optCarro.get();

        //Adiciona na lista
        lista.getCarros().add(carro);

        //Salva no banco de dados
        usuarioListaRepository.save(lista);

        return new ResponseEntity(optUsuario.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}/lista/{idLista}/remover/{idCarro}")
    public ResponseEntity removerCarroLista(@PathVariable("id") UUID id, @PathVariable("idLista") UUID idLista, @PathVariable("idCarro") UUID idCarro) {
        //Faço as buscas do usuário, carro e lista
        Optional<Usuario> optUsuario = this.repository.findById(id);
        Optional<Carro> optCarro = this.carroRepository.findById(idCarro);
        Optional<UsuarioLista> optUsuarioLista = this.usuarioListaRepository.findById(idLista);

        //Caso não ache o usuário, retornar um 404
        if (optUsuario.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Caso não ache o a lista a ser associada o carro retornar um 404
        if (optUsuarioLista.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Caso não ache o carro a ser associado retornar um 404
        if (optCarro.isEmpty() == true) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        UsuarioLista lista = optUsuarioLista.get();
        Carro carro = optCarro.get();

        //Adiciona na lista
        for(Carro item : lista.getCarros()) {
            if (item.getId() == carro.getId()) {
                lista.getCarros().remove(carro);
                break;
            }
        }

        //Salva no banco de dados
        usuarioListaRepository.save(lista);

        return new ResponseEntity(optUsuario.get(), HttpStatus.OK);
    }

}
