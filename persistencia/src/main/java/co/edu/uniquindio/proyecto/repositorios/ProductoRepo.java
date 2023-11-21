package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.dto.ProductoValido;
import co.edu.uniquindio.proyecto.dto.ProductosPorUsuario;
import co.edu.uniquindio.proyecto.entidades.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {
    Page<Producto> findAll(Pageable paginador);

    @Query("select p.usuario.nombre from Producto p where p.codigo = :id")
    String obtenerNombreVendedor(Integer id);

    @Query ("select c from Producto p join p.listaComentarios c where p.codigo = :id")
    List<Comentario> obtenerComentario1(Integer id);

    @Query ("select c from Comentario c where c.producto.codigo = :id")
    List<Comentario> obtenerComentario2(Integer id);

    @Query ("select distinct c.usuario from Producto p join p.listaComentarios c where p.codigo = :id")
    List<Usuario> listarUsuariosComentarios (Integer id);

    @Query("select p.nombre, p.descripcion, p.precio, p.ciudad.nombre from Producto p where :fechaActual < p.fechaLimite")
    List<Object[]> listarProductosValidos1(LocalDate fechaActual);

    @Query("select new co.edu.uniquindio.proyecto.dto.ProductoValido (p.nombre, p.descripcion, p.precio, p.ciudad) from Producto p where :fechaActual < p.fechaLimite")
    List<ProductoValido> listarProductosValidos2(LocalDate fechaActual);

    @Query("select c, count (p) from Producto p join p.listaCategorias c group by c")
    List<Object[]> obtenerTotalProductosPorCategoria ();

    @Query("select p from Producto p where p.listaComentarios is empty  ")
    List<Producto> obtenerProductosSinComentarios ();

    @Query("select p from Producto p where p.nombre like concat('%', :nombre, '%')")  //ES IGUAL A: findByNombreConstins(String nombre)
    List<Producto> buscarProductoNombre(String nombre);

    @Query("select distinct p from Producto p left join p.listaCategorias c where p.nombre like concat('%', :busqueda,'%') or c.nombre like concat('%', :busqueda,'%') or p.ciudad.nombre like concat('%', :busqueda,'%') ")
    List<Producto> buscarProductoVariables(String busqueda);

    @Query("select new co.edu.uniquindio.proyecto.dto.ProductosPorUsuario(p.usuario.codigo, p.usuario.email, count (p)) from Producto p group by p.usuario")
    List<ProductosPorUsuario> obtenerProductosEnVenta();

    @Query("select c, count (p) as total from Producto p join p.listaCategorias c group by c order by total desc")
    List<Object []> obtenerCategoriaMasUsada();

    @Query("select avg(c.calificacion) from Producto p join p.listaComentarios c where p.codigo = :codigo")
    Float obtenerPromedioCalificaciones(Integer codigo);

    // 4. Una lista de los chats de un vendedor.
    @Query("select distinct u.listaChat from Producto p join p.usuario u join u.listaChat where u.codigo = :codigo")
    List <Chat> chatUsuario (String codigo);

    // 6. Una lista de los comentarios de un producto específico que aún no tienen respuesta.
    @Query("select c from Producto p join p.listaComentarios c where p.codigo = :codigoProducto and c.respuesta is null " )
    List <Comentario> listaComentariosSinRespuesta (Integer codigoProducto);

    // 7. Una lista de productos que tienen un descuento que está dentro de un rango que se pase por parámetro.
    //	  Solo muestre los productos que tengan unidades disponibles.
    @Query("select p from Producto p where p.descuento between :min and :max and p.unidades > 0")
    List<Producto> obtenerProductosConDescuentoRango(double min, double max);

    @Query("select p from Producto p where p.usuario.codigo =:codigoVendedor")
    List<Producto> obtenerProductosVendedor (String codigoVendedor);

    @Query ("Select p from Producto p where p.fechaLimite >= CURRENT_DATE and p.unidades > 0 ")
    List<Producto> obtenerTodosProductosRestriccion();

    @Query ("select p from Producto p where p.descuento > 0.0")
    List<Producto> obtenerProductosConOferta();

    @Query("select p, count(p.codigo) as cantidad from Compra c join c.listaDetallesCompra d join d.producto p group by p order by cantidad desc ")
    List<Producto> obtenerProductosMasVendidos();

    @Query("select p from Producto p where p.precio between :rangoMin and :rangoMax")
    List<Producto> obtenerProductosRangoPrecio(Float rangoMin, Float rangoMax);
}
