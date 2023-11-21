package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Chat;
import co.edu.uniquindio.proyecto.entidades.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ChatRepo;
import co.edu.uniquindio.proyecto.repositorios.MensajeRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChatServicioImpl implements ChatServicio{

    private final ChatRepo chatRepo;
    private final MensajeRepo mensajeRepo;

    public ChatServicioImpl(ChatRepo chatRepo, MensajeRepo mensajeRepo) {
        this.chatRepo = chatRepo;
        this.mensajeRepo = mensajeRepo;
    }

    @Override
    public Chat crearChat(Usuario usuario, Producto producto){
        Chat chat = new Chat(usuario, producto);
        return chatRepo.save(chat);
    }

    @Override
    public void enviarMensaje(Usuario usuario, Producto producto, String mensaje) throws Exception {
        Chat chat = chatRepo.obtenerChatUsuarios(usuario.getCodigo(), producto.getCodigo());
        Mensaje msg = new Mensaje(mensaje, LocalDate.now(), producto.getUsuario().getCodigo());
        chat.getListaMensajes().add(msg);
        if(chatRepo.save(chat) == null) throw new Exception("El mensaje no pudo ser guardado");
    }

    @Override
    public void responderMensaje(Usuario usuario, Producto producto, String respuesta) throws Exception {
        Chat chat = chatRepo.obtenerChatUsuarios(usuario.getCodigo(), producto.getCodigo());
        Mensaje msg = new Mensaje(respuesta, LocalDate.now(), usuario.getCodigo());
        chat.getListaMensajes().add(msg);
        if(chatRepo.save(chat) == null) throw new Exception("El mensaje no pudo ser guardado");
    }

    @Override
    public Chat obtenerChat(Usuario usuario, Producto producto) {
        Chat chat = chatRepo.obtenerChatUsuarios(usuario.getCodigo(), producto.getCodigo());
        if(chat != null){
            return chat;
        }else{
            return crearChat(usuario, producto);
        }
    }

    @Override
    public Mensaje obtenerMensaje(int codigo) {
        return mensajeRepo.findById(codigo).get();
    }
}
