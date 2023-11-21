package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
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
public class DetalleProductoBean implements Serializable {

    @Value("#{param['producto']}")
    private String productoParam;

    @Getter @Setter
    private Producto producto;

    @Autowired
    private ProductoServicio productoServicio;

    @Getter @Setter
    private String respuesta;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ComentarioRepo comentarioRepo;

    @Getter @Setter
    private Comentario nuevoComentario;

    @Getter @Setter
    private List<Comentario> listaComentarios;

    @Value("#{seguridadBean.usuarioSeccion}")
    private Usuario usuarioSeccion;

    @PostConstruct
    public void inicializar() {
        nuevoComentario = new Comentario();
        if(productoParam!=null && !productoParam.isEmpty()) {
            try {
                producto = productoServicio.obtenerProducto(Integer.parseInt(productoParam));
                this.listaComentarios = producto.getListaComentarios();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String irAlChat(){
        System.out.println("en detalle pro: " + producto.getCodigo());
        String c = "/usuario/chat_producto?faces-redirect=true&amp;producto=" + producto.getCodigo();
        return c;
    }

    public void responder(String id){
        System.out.println(id);
        try {
            Comentario c= productoServicio.obtenerComentario(Integer.parseInt(id));
            System.out.println(respuesta);
            c.setRespuesta(respuesta);
            productoServicio.actualizarComentario(id);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void crearComentario(){
        try{
            if(usuarioSeccion != null){
                nuevoComentario.setProducto(producto);
                nuevoComentario.setUsuario(usuarioSeccion);
                productoServicio.comentarProducto(nuevoComentario);
                this.listaComentarios.add(nuevoComentario);
                nuevoComentario = new Comentario();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getCalificacionPromedio(){
        Float calificacion = productoServicio.obtenerCalificacionPromedio(producto.getCodigo());
        if(calificacion != null){
            return Math.round(productoServicio.obtenerCalificacionPromedio(producto.getCodigo()));
        }
        return 0;
    }

}
