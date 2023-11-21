package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class ComentarioTest {

    @Autowired
    ComentarioRepo comentarioRepo;

    @Autowired
    UsuarioRepo usuarioRepo;

    @Autowired
    ProductoRepo productoRepo;

    @Test
    public void registrarTest(){
        Usuario usuario = usuarioRepo.findById("123").orElse(null);
        Producto producto = productoRepo.findById(1).orElse(null);

        Comentario comentario = new Comentario("Bien",LocalDate.now(),5.0F, usuario ,producto );

        Comentario comentarioGuardado =comentarioRepo.save(comentario);

        Assertions.assertNotNull(comentarioGuardado);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){
        comentarioRepo.deleteById(16);

        Comentario comentarioBuscado= comentarioRepo.findById(16).orElse(null);

        Assertions.assertNull(comentarioBuscado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Comentario guardado= comentarioRepo.findById(15).orElse(null);
        guardado.setMensaje("malo esa mierda");

        comentarioRepo.save(guardado);

        Comentario comentarioBuscado = comentarioRepo.findById(15).orElse(null);

        Assertions.assertEquals("malo esa mierda", comentarioBuscado.getMensaje());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Comentario> comentarios=comentarioRepo.findAll();

        comentarios.forEach( com -> System.out.println(com));
    }
}
