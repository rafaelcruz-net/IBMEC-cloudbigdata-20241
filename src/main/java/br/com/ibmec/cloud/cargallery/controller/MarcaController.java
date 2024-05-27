package br.com.ibmec.cloud.cargallery.controller;

import br.com.ibmec.cloud.cargallery.controller.request.CarroRequest;
import br.com.ibmec.cloud.cargallery.controller.request.MarcaRequest;
import br.com.ibmec.cloud.cargallery.models.Carro;
import br.com.ibmec.cloud.cargallery.models.Marca;
import br.com.ibmec.cloud.cargallery.repository.CarroRepository;
import br.com.ibmec.cloud.cargallery.repository.MarcaRepository;
import br.com.ibmec.cloud.cargallery.service.AzureSearchService;
import br.com.ibmec.cloud.cargallery.service.AzureStorageAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/marca")
public class MarcaController {
    @Autowired
    private MarcaRepository repository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private AzureStorageAccountService accountService;

    @Autowired
    private AzureSearchService searchService;

    @PostMapping
    public ResponseEntity<Marca> criar(@Valid @RequestBody MarcaRequest request) throws Exception {

        Marca marca = new Marca();
        marca.setNome(request.getNome());

        //Salva no banco de dados a marca
        this.repository.save(marca);

        //Verificar se o usuário enviou carros
        for (CarroRequest item : request.getCarros()) {
            Carro carro = new Carro();
            carro.setId(UUID.randomUUID());
            carro.setNome(item.getNome());
            carro.setDescricao(item.getDescricao());

            //Sobe a imagem para o Azure
            String imageUrl = this.accountService.uploadFileToAzure(item.getImagemBase64());
            carro.setImagem(imageUrl);

            carro.setMarca(marca);

            //Associa a marca ao carro
            marca.getCarros().add(carro);

            //Salva no banco de dados
            this.carroRepository.save(carro);

            //Indexa no Azure Search
            this.searchService.index(carro.getId(), marca.getNome(), carro.getNome());
        }


        return new ResponseEntity<>(marca, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Marca> obter(@PathVariable("id") UUID id) {
        return  this.repository.findById(id).map(item -> {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/carro")
    public ResponseEntity<Marca> associarCarro(@PathVariable("id") UUID id, @Valid @RequestBody CarroRequest request) throws Exception {
        Optional<Marca> optMarca = this.repository.findById(id);

        if (optMarca.isEmpty() == true) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Hibernate Optional -> Se ele achou, obtenho a instancia
        Marca marca = optMarca.get();

        //Cria uma nova instancia de carro para salvar
        Carro carro = new Carro();
        carro.setNome(request.getNome());
        carro.setDescricao(request.getDescricao());

        //Sobe a imagem para o Azure
        //String imageUrl = this.accountService.uploadFileToAzure(request.getImagemBase64());
        //carro.setImagem(imageUrl);

        carro.setMarca(marca);
        marca.getCarros().add(carro);

        //Salva o carro e associa a marca
        this.carroRepository.save(carro);

        //Indexa no Azure Search
        this.searchService.index(carro.getId(), marca.getNome(), carro.getNome());

        //Responde para o usuário
        return new ResponseEntity<>(marca, HttpStatus.CREATED);
    }

    @GetMapping("{id}/carro")
    public ResponseEntity<List<Carro>> obterCarros(@PathVariable("id") UUID id) {
        return this.repository.findById(id).map(item -> {
            return new ResponseEntity<>(item.getCarros(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Marca>> obterMarcar() {
        return new ResponseEntity<>(this.repository.findAll(), HttpStatus.OK) ;
    }

}
