package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Comentario;
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
public class EditarUsuarioBean implements Serializable {
    @Value("#{param['producto']}")
    private String usuarioParam;

    @Getter
    @Setter
    private Usuario usuario;

    @Autowired
    private UsuarioServicio usuarioServicio;


    @Value("#{seguridadBean.usuarioSeccion}")
    private Usuario usuarioSeccion;

    @PostConstruct
    public void inicializar() {

        if(usuarioParam!=null && !usuarioParam.isEmpty()) {
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
