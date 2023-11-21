package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.dto.ProductoCarrito;
import co.edu.uniquindio.proyecto.dto.ProductoFavorito;
import co.edu.uniquindio.proyecto.entidades.Administrador;
import co.edu.uniquindio.proyecto.entidades.Compra;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.AdministradorServicio;
import co.edu.uniquindio.proyecto.servicios.CompraServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

@Scope("session")
@Component
public class SeguridadBean implements Serializable {

    @Getter
    @Setter
    @NotBlank
    private String email, emailR, password;

    @Getter
    @Setter
    private boolean autenticado;

    @Getter
    @Setter
    private boolean autenticadoB;

    @Getter
    @Setter
    private boolean autenticadoA;

    @Getter
    @Setter
    private Usuario usuarioSeccion;

    @Getter
    @Setter
    private Administrador administradorSeccion;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private AdministradorServicio administradorServicio;

    @Getter
    @Setter
    private ArrayList<ProductoCarrito> productosCarrito;

    @Getter
    @Setter
    private List<Compra> listaMisCompras;

    @Getter
    @Setter
    private Float subtotal;

    @Autowired
    private CompraServicio compraServicio;

    @Getter
    @Setter
    private String medioPago;

    @Getter
    @Setter
    private ArrayList<String> mediosPago;

    @PostConstruct
    public void inicializar() {
        autenticado = false;
        autenticadoA = false;
        autenticadoB = false;
        subtotal = 0F;
        this.productosCarrito = new ArrayList<>();
        this.mediosPago = new ArrayList<>();
        mediosPago.add("Tarjeta de crédito");
        mediosPago.add("Tarjeta débito");
        mediosPago.add("Pago contraentrega");
        mediosPago.add("PSE");
        listaMisCompras = new ArrayList<Compra>();
    }

    public List<Compra> listarComprasUsuario() {
        System.out.println(usuarioSeccion.getCodigo());
        if (usuarioSeccion != null) {
            listaMisCompras = compraServicio.listarComprasUsuario(usuarioSeccion.getCodigo());
            return listaMisCompras;
        }
        return null;
    }

    public Double calcularSubTotal(int indice) {

        listaMisCompras = compraServicio.listarComprasUsuario(usuarioSeccion.getCodigo());
        Double total = 0.0;

        for (int i = 0; i < listaMisCompras.get(indice).getListaDetallesCompra().size(); i++) {
            total = listaMisCompras.get(indice).getListaDetallesCompra().get(i).getUnidades() * listaMisCompras.get(indice).getListaDetallesCompra().get(i).getProducto().getPrecioFinal();
        }
        return total;
    }

    public String iniciarSesion() {
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                if(email.equals("sebas@gmai.com")){
                    autenticadoB = true;
                }
                usuarioSeccion = usuarioServicio.iniciarSesión(email, password);
                autenticado = true;

                return "/index?faces-redirect=true";
            } catch (Exception e1) {

                try {
                    administradorSeccion = administradorServicio.iniciarSesión(email, password);
                    autenticadoA = true;
                    return "/index?faces-redirect=true";
                } catch (Exception e2) {

                }
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e1.getMessage());
                FacesContext.getCurrentInstance().addMessage("login-bean", fm);
            }
        }
        return null;
    }

    public String recuperarContrasenia() {
        if (!emailR.isEmpty()) {
            try {
                usuarioServicio.recuperarContraseña(emailR);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información", "Se ha enviado un mensaje de recuoercación a su correo electrónico");
                FacesContext.getCurrentInstance().addMessage(null, fm);
                return "/index?faces-redirect=true";
            } catch (Exception e1) {
                try {
                    administradorServicio.recuperarContraseña(emailR);
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información", "Se ha enviado un mensaje de recuoercación a su correo electrónico");
                    FacesContext.getCurrentInstance().addMessage(null, fm);
                    return "/index?faces-redirect=true";

                } catch (Exception e2) {

                }
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e1.getMessage());
                FacesContext.getCurrentInstance().addMessage("login-bean", fm);
            }
        }
        return null;
    }

    public String cerrarSeccion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public void agregarAlCarrito(Integer codigo, Float precio, String nombre, String imagen, Float descuento) {
        ProductoCarrito pc = new ProductoCarrito(codigo, nombre, imagen, 1, precio, descuento);
        if (!productosCarrito.contains(pc)) {
            System.out.print("Hola2");
            productosCarrito.add(pc);
            subtotal += pc.getPrecioFinal();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El producto se ha agregado a carrito");
            FacesContext.getCurrentInstance().addMessage("add-cart", fm);
        }
    }

    public void eliminarDelCarrito(int indice) {
        subtotal -= productosCarrito.get(indice).getPrecioFinal();
        productosCarrito.remove(indice);
    }

    public void actualizarSubtotal() {
        subtotal = 0F;
        for (ProductoCarrito p : productosCarrito) {
            subtotal += p.getPrecioFinal() * p.getUnidades();
        }
    }

    public String comprar() {
        if (usuarioSeccion != null && !productosCarrito.isEmpty()) {
            try {
                if (mediosPago.contains(medioPago)) {
                    Compra compraRealizada = compraServicio.agregarCompra(productosCarrito, usuarioSeccion, medioPago);
                    compraServicio.enviarCorreoElectronico(usuarioSeccion, compraRealizada);
                    productosCarrito.clear();
                    subtotal = 0F;
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La compra se ha efectuado correctamente, revisa tu correo para visualizar tu compra");
                    FacesContext.getCurrentInstance().addMessage("msj-compra", fm);
                    return "/usuario/carrito?faces-redirect=true";
                } else {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Debe seleccionar un medio de pago para efectuar la compra");
                    FacesContext.getCurrentInstance().addMessage("msj-compra", fm);
                }
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "La compra no se ha podido efectuar correctamente: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage("msj-compra", fm);
                return null;
            }
        }
        return null;
    }







}
