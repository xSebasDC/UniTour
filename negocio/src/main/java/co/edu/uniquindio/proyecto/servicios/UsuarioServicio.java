package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;

import java.util.List;

public interface UsuarioServicio {

    //--------------------------------------------- GESTIÓN DE USUARIOS -------------------------------

    //Crear Usuario
    Usuario registrarUsuario (Usuario usuario) throws Exception;

    List<Usuario> buscarUsuarios(String nombreProducto, String[] filtros);

    //Actualizar Usuario
    Usuario actualizarUsuario (Usuario usuario) throws Exception;

    //Eliminar Usuario
    void eliminarUsuario (String codigo) throws  Exception;

    //Obtener usuario
    Usuario obtenerUsuario(String codigo) throws Exception;

    //Listar usuario
    List<Usuario> listarUsuarios();

    //Loguearse
    Usuario iniciarSesión(String email, String password) throws Exception;

    //Recuperar contraseñas usando correo electrónico.
    String recuperarContraseña (String correo) throws  Exception;


}
