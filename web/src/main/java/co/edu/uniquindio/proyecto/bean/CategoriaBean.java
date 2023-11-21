package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.servicios.CategoriaServicio;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class CategoriaBean implements Serializable {
    @Autowired
    private CategoriaServicio categoriaServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Getter @Setter
    private List<Producto> productos;

    @Value("#{param['categoria']}")
    private String categoriaParam;

    @Getter @Setter
    private Categoria categoria;

    @PostConstruct
    public void inicializar(){
        if(categoriaParam!=null && !categoriaParam.isEmpty()) {
            try {
                categoria = categoriaServicio.obtenerCategoria(Integer.parseInt(categoriaParam));
                productos = productoServicio.listarProductos(Integer.parseInt(categoriaParam));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List <Categoria> getCatetegoriasTienda(){
        return categoriaServicio.listarCategorias();
    }

}
