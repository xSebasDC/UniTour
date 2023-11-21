package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.servicios.*;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
import org.primefaces.shaded.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class ProductoBean implements Serializable {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    @Getter
    @Setter
    private CategoriaServicio categoriaServicio;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private String descripcion;

    @Getter @Setter
    private Integer unidades;

    @Getter @Setter
    private Double precio;

    @Getter @Setter
    private Double descuento;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    @Autowired
    private SubastaServicio subastaServicio;

    @Getter @Setter
    private Producto producto;

    @Getter
    private String idProductoSeleccionado;

    @Value("${upload.url}")
    private String urlImagenes;

    private ArrayList<String> imagenes;

    @Getter @Setter
    private List<Categoria> categorias;

    @Getter
    @Setter
    private List<Categoria> categoriasSeleccionadas;

    @Getter
    @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private List<Producto> productosMasVendidos;

    @Getter @Setter
    private List<Producto> productosConOfertas;

    @Value("#{seguridadBean.usuarioSeccion}")
    private Usuario usuarioSeccion;

    @Getter @Setter
    private Float precioSubasta;

    @PostConstruct
    public void inicializar() {
        producto = new Producto();
        this.imagenes = new ArrayList<>();
        producto.setImagenes(imagenes);
        categorias = categoriaServicio.listarCategorias();
        ciudades = ciudadServicio.listarCiudades();
        productosMasVendidos = productoServicio.obtenerProductosMasVendidos();
        productosConOfertas = productoServicio.obtenerProductosConOfertas();
    }

    public String publicarProducto() {
        try {
            if (usuarioSeccion != null) {
                if (!imagenes.isEmpty()) {
                    producto.setUsuario(usuarioSeccion);
                    producto.setListaCategorias(categoriasSeleccionadas);
                    producto.setFechaLimite(LocalDate.now().plusMonths(2));

                    for (Categoria c : categoriasSeleccionadas) {
                        c.getListaProductos().add(producto);
                    }
                    Producto productoPublicado = productoServicio.publicarProducto(producto);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se ha publicado un producto nuevo");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    return "/index?faces-redirect=true";
                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Es necesario subir al menos una imagen");
                    FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
                }
            } else {

            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }



    public void subirImagenes(FileUploadEvent event) {
        UploadedFile imagen = event.getFile();
        String nombreImagen = subirImagen(imagen);
        if (nombreImagen != null) {
            imagenes.add(nombreImagen);
        }
    }

    public String subirImagen(UploadedFile file) {
        try {
            InputStream input = file.getInputStream();
            String fileName = FilenameUtils.getName(file.getFileName());
            String baseName = FilenameUtils.getBaseName(fileName) + "_";
            String extension = "." + FilenameUtils.getExtension(fileName);
            File fileDest = File.createTempFile(baseName, extension, new File(urlImagenes));
            FileOutputStream output = new FileOutputStream(fileDest);
            IOUtils.copy(input, output);

            return fileDest.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void seleccionarProducto(String id){
        this.idProductoSeleccionado = id;

    }

    public String actualizarProducto(){
        try {
            if(idProductoSeleccionado!= null) {
                System.out.println(idProductoSeleccionado);
                Producto producto1 = productoServicio.obtenerProducto(Integer.parseInt(idProductoSeleccionado));
                producto1.setNombre(nombre);
                producto1.setDescripcion(descripcion);
                producto1.setUnidades(unidades);
                producto1.setPrecio(precio);
                producto1.setDescuento(descuento);
                productoServicio.actualizarProducto(producto1);
                System.out.println("melito creado");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Actualizacion exitosa");
                FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
                return "/index?faces-redirect=true";
            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public String eliminarProducto(){
        if(usuarioSeccion != null && idProductoSeleccionado != null){
            try {
                productoServicio.eliminarProducto(Integer.parseInt(idProductoSeleccionado));
                return "/usuario/perfil?faces-redirect=true";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String subastarProducto(){
        if(usuarioSeccion != null){
            try {
                System.out.println("Subasta de: " + idProductoSeleccionado);
                Subasta sub = new Subasta();
                sub.setFechaLimite(LocalDate.now().plusWeeks(2));
                sub.setProducto(productoServicio.obtenerProducto(Integer.parseInt(idProductoSeleccionado)));
                subastaServicio.crearSubasta(sub);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información", "Se ha puesto en subasta el producto");
                FacesContext.getCurrentInstance().addMessage(null, fm);
                return "/usuario/perfil?faces-redirect=true";
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("login-bean", fm);
            }
        }
        return null;
    }


    public String hacerSubastaProducto(){
        Subasta s = subastaServicio.obtenerSubastaProducto(idProductoSeleccionado);
        System.out.println(usuarioSeccion);
        System.out.println(s);
        System.out.println(precioSubasta);
        if(usuarioSeccion != null && precioSubasta != null){
            DetalleSubasta ds = new DetalleSubasta(precio, LocalDate.now(), usuarioSeccion, s);
            System.out.println(idProductoSeleccionado);
            subastaServicio.guardarDetalleSubasta(ds);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se ha realizado una propuesta a la subasta");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }else{
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "Es necesario ingresar un valor");
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

}
