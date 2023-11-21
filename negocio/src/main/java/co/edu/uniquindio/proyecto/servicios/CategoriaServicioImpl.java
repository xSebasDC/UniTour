package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicioImpl implements CategoriaServicio{

    private  final CategoriaRepo categoriaRepo;

    public CategoriaServicioImpl(CategoriaRepo categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) throws Exception {
        return categoriaRepo.save(categoria);
    }

    @Override
    public Categoria crearCategoria(String nombre) {
        Categoria cat = new Categoria(nombre);
        return categoriaRepo.save(cat);
    }

    @Override
    public Categoria obtenerCategoria(int codigo) throws Exception {
        Optional<Categoria> cat = categoriaRepo.findById(codigo);
        if(cat.isEmpty()){
            throw  new Exception("No se ha encontrado el tipo");
        }
        return cat.get();
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepo.findAll();
    }


}
