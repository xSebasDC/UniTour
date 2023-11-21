package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Mensaje;
import co.edu.uniquindio.proyecto.repositorios.MensajeRepo;
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
public class MensajeTest {

    @Autowired
    MensajeRepo mensajeRepo;

    @Test
    public void registrarTest(){
        Mensaje mensaje = new Mensaje("que se dice mi socio",LocalDate.now(), "123");

        Mensaje mensajeGuardado =mensajeRepo.save(mensaje);

        Assertions.assertNotNull(mensajeGuardado);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){

        mensajeRepo.deleteById(314);

        Mensaje mensajeBuscado= mensajeRepo.findById(314).orElse(null);

        Assertions.assertNull(mensajeBuscado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Mensaje guardado= mensajeRepo.findById(315).orElse(null);
        guardado.setMensaje("malo esa mierda");

        mensajeRepo.save(guardado);

        Mensaje mensajeBuscado = mensajeRepo.findById(315).orElse(null);

        Assertions.assertEquals("malo esa mierda", mensajeBuscado.getMensaje());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Mensaje> mensajes=mensajeRepo.findAll();

        mensajes.forEach( m -> System.out.println(m));
    }
}
