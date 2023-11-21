package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CiudadRepo extends JpaRepository<Ciudad, Integer> {
    Optional<Ciudad> findByNombre (String nombreCiudad);

    @Query ("select u from Ciudad c join c.listaUsuarios u where c.nombre = :nombre")
    List<Usuario> listarUsuarios (String nombre);

    @Query("select p from Producto p where p.ciudad.codigo = :codigo")
    List<Producto> obtenerProductosXCiudad(int codigo);

    @Query ("select u from Ciudad c join c.listaUsuarios u where c.codigo = :codigoCiu")
    List<Usuario> obtenerUsuariosCiudad(int codigoCiu);
}
