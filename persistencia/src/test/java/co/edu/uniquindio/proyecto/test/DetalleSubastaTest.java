package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.repositorios.DetalleSubastaRepo;
import co.edu.uniquindio.proyecto.repositorios.SubastaRepo;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DetalleSubastaTest {

    @Autowired
    DetalleSubastaRepo detalleSubastaRepo;

    @Autowired
    UsuarioRepo usuarioRepo;

    @Autowired
    SubastaRepo subastaRepo;

    @Test
    public void registrarTest(){

        Usuario usuario = usuarioRepo.findById("123").orElse(null);

        Subasta subasta = subastaRepo.findById(12).orElse(null);

        DetalleSubasta detalleSubasta = new DetalleSubasta(200.0D, LocalDate.now(), usuario, subasta);

        DetalleSubasta detalleSubastaGuardada =detalleSubastaRepo.save(detalleSubasta);

        Assertions.assertNotNull(detalleSubastaGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){

        detalleSubastaRepo.deleteById(344);

        DetalleSubasta detalleSubastaBuscada= detalleSubastaRepo.findById(344).orElse(null);

        Assertions.assertNull(detalleSubastaBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        DetalleSubasta guardado= detalleSubastaRepo.findById(12).orElse(null);
        guardado.setValor(90.000);

        detalleSubastaRepo.save(guardado);

        DetalleSubasta detalleSubastaBuscada = detalleSubastaRepo.findById(12).orElse(null);

        Assertions.assertEquals(90.000, detalleSubastaBuscada.getValor());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){
        List<DetalleSubasta> detalleSubastas=detalleSubastaRepo.findAll();

        detalleSubastas.forEach( ds -> System.out.println(ds));
    }
}
