package br.com.ibmec.cloud.cargallery.repository;


import br.com.ibmec.cloud.cargallery.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
