package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Integer> {
// 2. Una lista con las categorías y su calificación promedio. Ordene la lista de mayor a menor de acuerdo a la calificación promedio.
//	  (incluya todos las categorías así no tenga productos con calificaciones)
    @Query("select  cat.nombre, avg(com.calificacion) from Categoria cat left join cat.listaProductos p left join p.listaComentarios com group by cat order by avg(com.calificacion) desc")
    List<Object[]> listarCategoriasYCalificaciónPromedio();

    // 5. La lista de subastas de una categoría específica.
    // Tenga en cuenta que solo debe mostrarlas subastas que aún están disponibles (validar fecha)
    @Query("select s from Categoria cat left join cat.listaProductos p left join p.listaSubastas s where cat.codigo = :codigoCat and s.fechaLimite >= CURRENT_DATE")
    List<Subasta> obtenerSubastasCategoria(Integer codigoCat);

    @Query("select p from Categoria cat left join cat.listaProductos p where cat.codigo = :codigoCat and p.fechaLimite >= CURRENT_DATE and p.unidades > 0")
    List<Producto> obtenerProductosCategoria(Integer codigoCat);

}