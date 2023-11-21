package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Ciudad;

import java.util.List;

public interface CiudadServicio {
    Ciudad registrarCiudad(Ciudad ciudad)throws Exception;

    Ciudad registrarCiudad(String nombre)throws Exception;

    Ciudad obtenerCiudad(int codigo) throws Exception;

    List<Ciudad> listarCiudades();
}
