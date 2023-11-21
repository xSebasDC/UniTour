package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CiudadServicioImpl implements CiudadServicio{

    private final CiudadRepo ciudadRepo;

    public CiudadServicioImpl(CiudadRepo ciudadRepo) {
        this.ciudadRepo = ciudadRepo;
    }

    @Override
    public Ciudad registrarCiudad(Ciudad ciudad) throws Exception {
        return ciudadRepo.save(ciudad);
    }

    @Override
    public List<Ciudad> listarCiudades() {
        return ciudadRepo.findAll();
    }

    @Override
    public Ciudad registrarCiudad(String nombre){
        Ciudad ciudad = new Ciudad(nombre);
        return ciudadRepo.save(ciudad);
    }

    @Override
    public Ciudad obtenerCiudad(int codigo) throws Exception {
        Optional<Ciudad> ciudad= ciudadRepo.findById(codigo);
        if (ciudad.isEmpty()){
            throw new Exception("La ciudad buscada no existe");
        }
        return ciudad.get();
    }
}
