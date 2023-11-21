package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Subasta;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
public class InicioBean implements Serializable {

    @Autowired
    private ProductoServicio productoServicio;

    @Getter @Setter
    private List<Producto> listaSubastas;

    @Autowired
    private SubastaServicio subastaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CategoriaServicio categoriaServicio;

    @Getter @Setter
    private List<Producto> productos;

    @Getter @Setter
    private List<Categoria> categorias;

    @Getter @Setter
    private Categoria categoriaSeleccionada;

    @Value("#{seguridadBean.usuarioSeccion}")
    private Usuario usuarioSeccion;

    @Autowired
    private AdministradorServicio administradorServicio;

    @PostConstruct
    public void inicializar(){
        this.listaSubastas= subastaServicio.obtenerSubastas();
        System.out.println(listaSubastas);
        this.productos = productoServicio.listarProductos();
        categorias = categoriaServicio.listarCategorias();
        administradorServicio.quemarAdmin();
    }


    public String irAlDetalle(String id) {
        return "/detalleProducto?faces-redirect=true&amp;producto="+id;
    }



    public String irAProductosCategoria(){
        System.out.println("Categoria: " + categoriaSeleccionada );
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/productosPorCategoria.xhtml?categoria="+categoriaSeleccionada.getCodigo());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/productosPorCategoria?faces-redirect=true&amp;categoria="+categoriaSeleccionada.getCodigo();
    }

    public String irAProductosUsuario(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/productos_usuario.xhtml?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/productos_usuario?faces-redirect=true";
    }

}

