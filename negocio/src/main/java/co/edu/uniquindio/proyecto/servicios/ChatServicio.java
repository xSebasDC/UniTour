package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Chat;
import co.edu.uniquindio.proyecto.entidades.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;

public interface ChatServicio {

    //Crea el chat entre un usuario y el vendedor del producto en cuestión
    Chat crearChat(Usuario usuario, Producto producto);

    //Envía un mensaje al vendedor del producto en cuestión (Comprador)
    void enviarMensaje(Usuario usuario, Producto producto, String mensaje) throws Exception;

    //Responde a un mensaje del usuario en el producto (Vendedor)
    void responderMensaje(Usuario usuario, Producto producto, String respuesta) throws Exception;

    Chat obtenerChat(Usuario usuario, Producto producto);

    Mensaje obtenerMensaje(int codigo);
}
