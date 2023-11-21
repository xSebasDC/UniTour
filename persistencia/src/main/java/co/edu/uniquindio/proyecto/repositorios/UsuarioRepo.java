package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.dto.ProductoFavorito;
import co.edu.uniquindio.proyecto.dto.UsuarioYCompra;
import co.edu.uniquindio.proyecto.dto.UsuarioYProducto;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, String> {
    List<Usuario> findAllByNombreContains(String nombre);

    @Query("select u from Usuario u where u.nombre like concat('%', :nombre, '%')")  //ES IGUAL A: findByNombreConstins(String nombre)
    List<Usuario> buscarUsuarioNombre(String nombre);

    Optional<Usuario> findByEmail (String email);

    Optional<Usuario> findByEmailAndPassword (String email, String clave);

    Page<Usuario> findAll (Pageable paginador);

    @Query("select p from Usuario u, IN (u.productosFavoritos) p where u.codigo = :codigo")
    List<Producto> obtenerProductosFavoritos(String codigo);

    @Query("select p from Usuario u, IN (u.listaProductos) p where u.codigo = :codigo")
    List<Producto> obtenerProductosUsuario(String codigo);

    @Query("select u.email, p from Usuario u join u.listaProductos p")
    List <Object[]> listarUsuariosYProductos();

    Optional <Usuario> findByUsername (String username);

 /*   @Query("select u.email, p from Usuario u left join u.listaProductos p")
    List <Object[]> listarUsuariosYProductos1();*/

    @Query("select new co.edu.uniquindio.proyecto.dto.UsuarioYProducto (u.email, u.nombre, p) from Usuario u left join u.listaProductos p")
    List <UsuarioYProducto> listarUsuariosYProductos2();

    //Otras consultas

    //Obtiene todos las compras y sus usuarios
    @Query("select new co.edu.uniquindio.proyecto.dto.UsuarioYCompra (u.email, u.nombre, p) from Usuario u left join u.listaCompras p")
    List <UsuarioYCompra> listarUsuariosYCompras();

    // 9. El valor total de cada una de las compras que ha hecho un usuario específico.
    @Query("select sum(d.unidades * d.precio_producto), c from DetalleCompra d join d.compra c where c.usuario.codigo = :codigo group by c")
    List <Object[]> obtenerTotalComprasUsuario(String codigo);


    @Query ("select u.email from Usuario u where u.codigo = :codigo")
    String consultarEmail (String codigo);

}
