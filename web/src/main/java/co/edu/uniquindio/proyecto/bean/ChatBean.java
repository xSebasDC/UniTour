package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Chat;
import co.edu.uniquindio.proyecto.entidades.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ChatServicio;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

@Component
@ViewScoped
public class ChatBean {

    @Autowired
    private ChatServicio chatServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Value("#{param['producto']}")
    private String productoParam;

    @Value("#{seguridadBean.usuarioSeccion}")
    private Usuario usuarioSeccion;

    @Getter
    @Setter
    private Chat chat;

    @Getter
    @Setter
    private Producto producto;
    @PostConstruct
    public void inicializar() {
        if (productoParam != null && !productoParam.isEmpty() && usuarioSeccion != null) {
            producto = productoServicio.obtenerProducto(Integer.parseInt(productoParam));
            chat = chatServicio.obtenerChat(usuarioSeccion, producto);
        }
    }

    public boolean mensajePropio(String idMsg) {
        try {
            Mensaje msg = chatServicio.obtenerMensaje(Integer.parseInt(idMsg));
            String codigoUser = msg.getEmisor();
            Usuario user = usuarioServicio.obtenerUsuario(codigoUser);
            if (user == chat.getUsuario()) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
