package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
public class BusquedaBean implements Serializable {
    @Getter @Setter
    private String busqueda;

    @Getter @Setter
    @Value("#{param['busqueda']}")
    private String busquedaParam;

    @Getter @Setter
    private List<Producto> productos;

    @Getter @Setter
    private List<Usuario> usuarios;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @PostConstruct
    public void inicializar(){
        if(busquedaParam != null && !busquedaParam.isEmpty()){
            usuarios = usuarioServicio.buscarUsuarios(busquedaParam,null);
            productos = productoServicio.buscarProductos(busquedaParam, null);
        }
    }
    public String buscar(){
        return "resultado_busqueda?faces-redirect=true&amp;busqueda="+busqueda;
    }
}
