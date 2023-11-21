package co.edu.uniquindio.proyecto.rest;

import co.edu.uniquindio.proyecto.dto.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoServicio productoServicio;

    /**
     * CRUD PRODUCTO
     **/
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            productoServicio.publicarProducto(producto);
            return ResponseEntity.status(200).body(new Mensaje("El producto se creó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable("id") String idProducto) {
        try {
            productoServicio.eliminarProducto(Integer.parseInt(idProducto));
            return ResponseEntity.status(200).body(new Mensaje("El producto se eliminó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto) {
        try {
            productoServicio.actualizarProducto(producto);
            return ResponseEntity.status(200).body(new Mensaje("El producto se actualizó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoServicio.listarProductos();
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<?> obtenerProducto(@PathVariable("idProducto") String idProducto) {
        try {
            Producto producto = productoServicio.obtenerProducto(Integer.parseInt(idProducto));
            return ResponseEntity.status(200).body(producto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    /**
     * FUNCIONALIDADES
     **/
    @GetMapping("comentarios/{idProducto}")
    public List<Comentario> obtenerComentariosProducto(@PathVariable("idProducto") String idProducto) {
        return productoServicio.obtenerComentariosProducto(Integer.parseInt(idProducto));
    }

    @GetMapping("calificacion/{idProducto}")
    public Float obtenerCalificacionPromedioProducto(@PathVariable("idProducto") String idProducto) {
        Float calificacionPromedio = productoServicio.obtenerCalificacionPromedio(Integer.parseInt(idProducto));
        if(calificacionPromedio!=null){
            return calificacionPromedio;
        }else{
            return 0F;
        }
    }

    /**
     * BUSCAR POR CATEGORIA, CIUDAD, NOMBRE
     **/
    @GetMapping("buscarProductos/{variable}")
    public List<Producto> buscarProductos(@PathVariable("variable") String variable) {
        return productoServicio.buscarProductos(variable, null);
    }

    /**
     * BUSCAR POR RANGO DE PRECIOS
     **/
    @GetMapping("buscarProductosRango/{rangoMin}{rangoMax}")
    public List<Producto> buscarProductos(@PathVariable("rangoMin") float rangoMin, @PathVariable("rangoMax") float rangoMax) {
        return productoServicio.obtenerProductosRangoPrecio(rangoMin, rangoMax);
    }

}
