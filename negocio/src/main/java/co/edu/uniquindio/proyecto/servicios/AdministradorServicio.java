package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Administrador;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;

import java.util.List;

public interface AdministradorServicio {

    //---------------------------------------------------- BÁSICOS --------------------------------------

    //Recuperar contraseña usando correo electrónico
    String recuperarContraseña (String email) throws Exception;

    //Loguearse
    Administrador iniciarSesión(String email, String password) throws Exception;

    void quemarAdmin();
    //--------------------------------------------------- REPORTES -------------------------------------

    //Obtiene la cantidad de productos que tiene la categoria con el código dado por parámetro
    List<Producto> reportarCantidadProductosXCategoria(int codigoCat);

    //Obtiene la cantidad de productos por cada ciudad
    List<Producto> reportarCantidadProductosXCiudad(int codigoCiu);

    //Obtiene la cantidad de usuarios por cada ciudad
    List<Usuario> reportarCantidadUsuariosXCiudad(int codigoCiu);
}
