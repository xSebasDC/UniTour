package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ProductoFavorito;
import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.excepciones.ProductoNoEncontradoException;

import java.util.List;

public interface ProductoServicio {

    //-------------------------------------------------- GESTIÓN CON USUARIOS (vendedores) -----------------------------------------------------

    //Publicar un producto para vender  y (crear)
    Producto publicarProducto(Producto p) throws Exception;

    //Actualizar producto
    void actualizarProducto(Producto p) throws Exception;

    //Eliminar producto
    void eliminarProducto(Integer codigoProducto) throws Exception;

    void actualizarComentario(String id);

    Comentario obtenerComentario (Integer comentario) throws  Exception;
    //Cambia el descuento de un producto
    void cambiarDescuentoProducto(Producto producto, double descuento) throws ProductoNoEncontradoException;

    //Listar productos de un usuario.
    List <Producto> listarProductosUsuario(String codigoUsuario) throws Exception;

    //------------------------------------------------------- GESTIÓN GENERAL ---------------------------------------------

    //Obtener productos
    Producto obtenerProducto(Integer codigoProducto) throws ProductoNoEncontradoException;

    //Listar todos los productos disponibles
    List<Producto> listarProductos();

    //Listar todos los productos disponibles (por categoría)
    List<Producto> listarProductos(Integer codigoCategoria);

    //Obtener calificación promedio
    Float obtenerCalificacionPromedio(Integer codigoProducto);

    //Obtener los propios productos de un vendedor
    List<Producto> listarProductosPropios (String codigoVendedor);

    //------------------------------------------------ GESTIÓN CON USUARIOS (compradores) -----------------------------------------------

    //Calificar y comentar un producto (usando estrella)
    void comentarProducto(String mensaje, Float calificacion, Usuario usuario, Producto producto ) throws Exception;
    void comentarProducto(Comentario comentario) throws Exception;
    //Guardar productos en su lista de favoritos y quitar productos de dicha lista
    void guardarProductoFavorito(Producto producto, Usuario usuario) throws Exception;

    void eliminarProductoFavorito(Producto producto, Usuario usuario) throws Exception;

    //Buscar productos por nombre (Filtrar por categoría, precio, ubicación, calificación)
    List <Producto> buscarProductos (String busqueda, String [] filtros);

    //Listar productos favoritos de un usuario.
    List<Producto> listarProductosFavoritosUsuario(String codigoUsuario) throws Exception;

    List<Producto> obtenerProductosMasVendidos();

    List<Producto> obtenerProductosConOfertas();

    List<Producto> obtenerProductosRangoPrecio(Float rangoMin, Float rangoMax);

    List<Comentario> obtenerComentariosProducto(int codigo);
}
