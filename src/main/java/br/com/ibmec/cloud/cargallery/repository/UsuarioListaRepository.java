package br.com.ibmec.cloud.cargallery.repository;

import br.com.ibmec.cloud.cargallery.models.UsuarioLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioListaRepository extends JpaRepository<UsuarioLista, UUID> {
}
