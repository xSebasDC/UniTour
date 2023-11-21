package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ProductoFavorito;
import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.excepciones.ProductoNoEncontradoException;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepo;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoRepo productoRepo;
    private final UsuarioRepo usuarioRepo;
    private final CategoriaRepo categoriaRepo;
    private final ComentarioRepo comentarioRepo;

    public ProductoServicioImpl(ProductoRepo productoRepo, UsuarioRepo usuarioRepo, CategoriaRepo categoriaRepo, ComentarioRepo comentarioRepo) {
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
        this.categoriaRepo = categoriaRepo;
        this.comentarioRepo = comentarioRepo;
    }

    @Override
    public Producto publicarProducto(Producto p) throws Exception {
        try {
            return productoRepo.save(p);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void actualizarProducto(Producto p) throws Exception {
        productoRepo.save(p);
    }

    @Override
    public void eliminarProducto(Integer codigoProducto) throws Exception {
        Optional<Producto> producto = productoRepo.findById(codigoProducto);
        if (producto.isEmpty()) {
            throw new Exception("El código dle producto no existe");
        }else{
            productoRepo.deleteById(codigoProducto);
        }

    }

    @Override
    public void actualizarComentario(String id) {
        Optional <Comentario> buscado = comentarioRepo.findById(Integer.parseInt(id));
        if (buscado.isEmpty()){
        }
       comentarioRepo.save(buscado.get());
    }

    @Override
    public Comentario obtenerComentario(Integer comentario) throws Exception {
        return comentarioRepo.findById(comentario).get();
    }

    @Override
    public Producto obtenerProducto(Integer codigoProducto) throws ProductoNoEncontradoException {
        return productoRepo.findById(codigoProducto).orElseThrow(() -> new ProductoNoEncontradoException("El código del producto no es válido"));
    }

    @Override
    public void cambiarDescuentoProducto(Producto producto, double descuento) throws ProductoNoEncontradoException {
        producto.setDescuento(descuento);
        productoRepo.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {

        //return productoRepo.findAll();
        return productoRepo.obtenerTodosProductosRestriccion();
    }

    @Override
    public List<Producto> listarProductos(Integer codigoCategoria) {
        return categoriaRepo.obtenerProductosCategoria(codigoCategoria);
    }

    @Override
    public List<Producto> listarProductosPropios(String codigoVendedor) {
        return productoRepo.obtenerProductosVendedor(codigoVendedor);
    }

    @Override
    public Float obtenerCalificacionPromedio(Integer codigoProducto) {
        return productoRepo.obtenerPromedioCalificaciones(codigoProducto);
    }

    @Override
    public void comentarProducto(String mensaje, Float calificacion, Usuario usuario, Producto producto) throws Exception {
        Comentario comentario = new Comentario(mensaje, LocalDate.now(), calificacion, usuario, producto);
        comentarioRepo.save(comentario);
    }

    @Override
    public void comentarProducto(Comentario comentario) throws Exception {
        comentario.setFecha_comentario(LocalDate.now());
        comentarioRepo.save(comentario);
    }

    @Override
    public void guardarProductoFavorito(Producto producto, Usuario usuario) throws Exception {
        if (usuarioRepo.findById(usuario.getCodigo()) != null) {
            if (productoRepo.findById(producto.getCodigo()) != null) {
                if (!usuario.getProductosFavoritos().contains(producto)) {
                    usuario.getProductosFavoritos().add(producto);
                    usuarioRepo.save(usuario);
                } else {
                    throw new Exception("El usuario ya ha marcado como favorito el producto " + producto.getNombre());
                }
            } else {
                throw new Exception("El producto con el código " + producto.getCodigo() + " no existe.");
            }
        } else {
            throw new Exception("El usuario con el código " + usuario.getCodigo() + " no existe.");
        }
    }

    @Override
    public void eliminarProductoFavorito(Producto producto, Usuario usuario) throws Exception {
        if (usuarioRepo.findById(usuario.getCodigo()) != null) {
            if (productoRepo.findById(producto.getCodigo()) != null) {
                if (usuario.getProductosFavoritos().contains(producto)) {
                    usuario.getProductosFavoritos().remove(producto);
                    usuarioRepo.save(usuario);
                } else {
                    throw new Exception("El usuario no ha marcado como favorito el producto " + producto.getNombre());
                }
            } else {
                throw new Exception("El producto con el código " + producto.getCodigo() + " no existe.");
            }
        } else {
            throw new Exception("El usuario con el código " + usuario.getCodigo() + " no existe.");
        }
    }

    @Override
    public List<Producto> buscarProductos(String busqueda, String[] filtros) {
        return productoRepo.buscarProductoVariables(busqueda);
    }

    @Override
    public List<Producto> listarProductosUsuario(String codigoUsuario) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(codigoUsuario);
        if (buscado.isEmpty()) {
            throw new Exception("El usuario con el código: " + codigoUsuario + " no existe");
        }
        return usuarioRepo.obtenerProductosUsuario(codigoUsuario);
    }

    @Override
    public List<Producto> listarProductosFavoritosUsuario(String codigoUsuario) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(codigoUsuario);
        if (buscado.isEmpty()) {
            throw new Exception("El usuario con el código: " + codigoUsuario + " no existe");
        }
        return usuarioRepo.obtenerProductosFavoritos(codigoUsuario);
    }

    @Override
    public List<Producto> obtenerProductosMasVendidos(){
        return productoRepo.obtenerProductosMasVendidos();
    }

    @Override
    public List<Producto> obtenerProductosConOfertas(){
        return productoRepo.obtenerProductosConOferta();
    }

    @Override
    public List<Producto> obtenerProductosRangoPrecio(Float rangoMin, Float rangoMax) {
        return productoRepo.obtenerProductosRangoPrecio(rangoMin, rangoMax);
    }

    @Override
    public List<Comentario> obtenerComentariosProducto(int codigo) {
        return comentarioRepo.obtenerComentariosProducto(codigo);
    }


}