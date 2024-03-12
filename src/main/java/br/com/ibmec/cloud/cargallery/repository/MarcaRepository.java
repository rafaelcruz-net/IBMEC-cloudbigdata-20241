package br.com.ibmec.cloud.cargallery.repository;


import br.com.ibmec.cloud.cargallery.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, UUID> {
}
