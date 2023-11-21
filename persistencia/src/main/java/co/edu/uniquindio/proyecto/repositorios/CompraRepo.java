package co.edu.uniquindio.proyecto.repositorios;
import co.edu.uniquindio.proyecto.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompraRepo  extends JpaRepository<Compra, Integer> {

    @Query("select count ( distinct d.producto) from Compra c join c.listaDetallesCompra d where c.usuario = :codigo")
    Long obtenerListaProductosComprados(String codigo);

    @Query("select sum(d.precio_producto*d.unidades) from DetalleCompra d where d.producto.usuario.codigo = :codigo")
    Long calcularTotalVenta(String codigo);

    @Query("select sum(d.precio_producto*d.unidades) from Compra c join c.listaDetallesCompra d where c.usuario.codigo = :codigo")
    Long calcularTotalCompras(String codigo);

    @Query("select sum(d.precio_producto *d.unidades ) from Compra c join c.listaDetallesCompra d where c.codigo = :codigo")
    Long calcularTotalCompra(Integer codigo);

    @Query("select c, d from Compra c join c.listaDetallesCompra d where c.usuario.codigo = :codigo ")
    List <Compra> obtenerComprasUsuario (String codigo);

    @Query("select c from Compra c where c.usuario.codigo = :codigo")
    List <Compra> listarComprasUsuario (String codigo);

    // 3. La cantidad de compras que se hace por cada medio de pago disponible.
    @Query ("select c.medioPago, count (c.medioPago) from Compra c group by c.medioPago")
    List <Object[]> calcularTotalMediosPago();

    // 10. El producto más vendido de una categoría específica.
    @Query ("select pc, max(dc.producto) from Compra c join c.listaDetallesCompra dc join dc.producto.listaCategorias pc where pc.codigo = :codigo")
    List<Object[]> productoMasVendidoCategoria(String codigo);

}
