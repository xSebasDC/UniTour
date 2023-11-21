package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Subasta;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubastaRepo extends JpaRepository<Subasta, Integer> {

    @Query("select max(d.valor) from Subasta s join s.listaDetalleSubasta d where s.codigo = :codigo")
    Float obtenerValorMasAlto(Integer codigo);

    @Query("select s from Subasta s where current_time  < s.fechaLimite")
    List<Subasta> listarSubastasDisponibles();

    @Query("select distinct p from Subasta s join s.producto p ")
    List<Producto> listarProductosSubasta();

    // 1. La cantidad de productos que están en subasta por cada categoría.
    @Query("select c, count (distinct p) from Subasta s join s.producto p join p.listaCategorias c group by c")
    List<Object[]> cantidadProductosSubastaCategoria();

    // 8. Dado el código de una subasta, devolver el usuario ganador de dicha subasta.
    @Query("select det.usuario from Subasta sub join sub.listaDetalleSubasta det where sub.codigo = :codigo group by det order by max(det.valor) desc")
    List<Usuario> obtenerGanadorSubasta(Integer codigo);

    @Query("select distinct s from Subasta s where s.producto.codigo =:id")
    Subasta obtenerSubastaProducto(Integer id);
    
}
