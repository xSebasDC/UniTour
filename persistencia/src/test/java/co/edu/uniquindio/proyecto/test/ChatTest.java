package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.repositorios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChatTest {

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    UsuarioRepo usuarioRepo;

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    MensajeRepo mensajeRepo;


    @Test
    @Sql("classpath:dataset.sql")
    public void crearChatTest(){
        Usuario usuarioMensaje= usuarioRepo.findById("123").orElse(null);
        Producto productoMensaje= productoRepo.findById(1).orElse(null);

        Chat chat= new Chat(10,usuarioMensaje,productoMensaje);
        Chat chatGuardado=chatRepo.save(chat);
        Assertions.assertNotNull(chatGuardado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarChatTest(){
        chatRepo.deleteById(11);

        Chat chatBuscado= chatRepo.findById(11).orElse(null);

        Assertions.assertNull(chatBuscado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarChatTest(){
        Producto producto= productoRepo.findById(1).orElse(null);

        Chat guardado= chatRepo.findById(134).orElse(null);
        guardado.setProducto(producto);

        chatRepo.save(guardado);

        Chat chatBuscado = chatRepo.findById(134).orElse(null);

        Assertions.assertEquals(producto, chatBuscado.getProducto());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){
        List<Chat> chats=chatRepo.findAll();
        chats.forEach( c -> System.out.println(c));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerChatUsuarios(){
        Chat chat = chatRepo.obtenerChatUsuarios("123", 1);
        System.out.println("Tiene el chat con código: " + chat.getCodigo());
        Assertions.assertNotNull(chat);
    }
}
