package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Administrador;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepo extends JpaRepository<Administrador, String> {


    Optional<Administrador> findByEmail (String email);

    Optional<Administrador> findByEmailAndPassword (String email, String clave);

    Page<Administrador> findAll (Pageable paginador);

    Optional <Administrador> findByUsername (String username);

    @Query ("select a.email from Administrador a where a.codigo = :codigo")
    String consultarEmail (String codigo);
}
