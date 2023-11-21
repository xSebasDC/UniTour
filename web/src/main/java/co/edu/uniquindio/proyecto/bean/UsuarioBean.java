package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.dto.ProductoFavorito;
import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.CiudadServicio;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class UsuarioBean implements Serializable {

    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private List<Usuario> usuarios;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    @Getter @Setter
    private CiudadServicio ciudadServicio;

    @Getter @Setter
    @Value("#{seguridadBean.usuarioSeccion}")
    private Usuario usuarioSeccion;

    @Getter @Setter
    private String idUsuarioSeleccionado;

    @Getter @Setter
    private String idUsuarioSeleccionado1;

    @Getter @Setter
    private List<Producto> listaProductos;

    @Getter @Setter
    private List<ProductoFavorito> listaProductosFavoritos;

    @PostConstruct
    public void inicializar() throws Exception {
        usuario = new Usuario();
        ciudades = ciudadServicio.listarCiudades();
        this.usuarios = usuarioServicio.listarUsuarios();
        if(usuarioSeccion!=null){
            listaProductos = productoServicio.listarProductosPropios(usuarioSeccion.getCodigo());
            listaProductosFavoritos = new ArrayList<>();
        }
    }

    public List<ProductoFavorito> getFavoritos(){
        try {
            List<Producto> productos = productoServicio.listarProductosFavoritosUsuario(usuarioSeccion.getCodigo()) ;
            listaProductosFavoritos.clear();

            for (int i = 0; i < productos.size() ; i++) {
                ProductoFavorito productoFavorito = new ProductoFavorito(productos.get(i).getCodigo(),productos.get(i).getNombre(), productos.get(i).getImagenPrincipal(), productos.get(i).getPrecio());
                listaProductosFavoritos.add(productoFavorito);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductosFavoritos;
    }

    public void registrarUsuario(){
        try {
            usuarioServicio.registrarUsuario(usuario);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro exitoso");
            FacesContext.getCurrentInstance().addMessage("msj-bean",msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean",msg);
        }
    }
    public  String redireccionarUsuario(String id){
        seleccionarUsuarioA(id);
        return "/editarUsuario?faces-redirect=true&amp;codigo="+id;
    }
    public void seleccionarUsuarioA(String id){
        System.out.println(id);
        this.idUsuarioSeleccionado1 = id;
        System.out.println(idUsuarioSeleccionado1);

    }
    public void seleccionarUsuario(String id){
        this.idUsuarioSeleccionado = id;
            eliminarUsuario();

    }


    public void eliminarUsuario(){
        try {
            if( idUsuarioSeleccionado != null) {
                Usuario usuario1= usuarioServicio.obtenerUsuario(idUsuarioSeleccionado);
                usuarioServicio.eliminarUsuario(idUsuarioSeleccionado);
                usuarios.remove(usuario1);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "la eliminacion ha sido exitosa");
                FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
            }

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean",msg);
        }
    }

    public String actualizarUsuario(){
        try {
            if(idUsuarioSeleccionado1!= null) {
                System.out.println(idUsuarioSeleccionado1);
                Usuario usuario1 = usuarioServicio.obtenerUsuario(idUsuarioSeleccionado1);
                usuario1.setNombre(nombre);
                usuario1.setEmail(email);
                usuario1.setUsername(username);
                usuarioServicio.actualizarUsuario(usuario1);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Actualizacion exitosa");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "/index?faces-redirect=true";
            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
            return null;
    }

    public void agregarFavoritos(Integer codigo, Float precio, String nombre, String imagen){

        System.out.println("\n\n"+ usuarioSeccion.getCodigo());

        ProductoFavorito pf = new ProductoFavorito(codigo, nombre, imagen, precio.doubleValue());
        if(!listaProductosFavoritos.contains(pf)){
            listaProductosFavoritos.add(pf);
            Producto producto = productoServicio.obtenerProducto(codigo);
            try{
                productoServicio.guardarProductoFavorito(producto, usuarioSeccion);

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El producto se ha agregado a productos favoritos");
                FacesContext.getCurrentInstance().addMessage("add-fav", fm);
            }catch (Exception e){
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("add-fav", fm);
            }



        }
    }

    public void eliminarDeFavoritos(int indice){

        ProductoFavorito eliminado = listaProductosFavoritos.remove(indice);
    }

}
