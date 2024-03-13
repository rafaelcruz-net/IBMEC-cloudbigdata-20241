package br.com.ibmec.cloud.cargallery.repository;


import br.com.ibmec.cloud.cargallery.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> findUsuarioByEmailAndSenha(String email, String senha);
}
