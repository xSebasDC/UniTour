package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Producto;

import java.util.List;

public interface CategoriaServicio {
    Categoria crearCategoria(Categoria categoria) throws Exception;

    Categoria crearCategoria(String nombre);

    Categoria obtenerCategoria(int codigo) throws Exception;

    List<Categoria> listarCategorias();


}
