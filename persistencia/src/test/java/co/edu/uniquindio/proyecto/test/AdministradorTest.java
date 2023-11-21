package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Administrador;
import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.AdministradorRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdministradorTest {

    @Autowired
    AdministradorRepo administradorRepo;

    @Test
    public void registrarTest(){

        Administrador administrador = new Administrador("12345","Mario","mariog@gmail.com","aymariog", "mg");

        Administrador administradorGuardado =administradorRepo.save(administrador);

        Assertions.assertNotNull(administradorGuardado);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){

        administradorRepo.deleteById("132");

        Administrador administradorBuscado= administradorRepo.findById("123").orElse(null);

        Assertions.assertNull(administradorBuscado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Administrador guardado= administradorRepo.findById("111").orElse(null);
        guardado.setEmail("tatiana_nuevo@gmail.com");

        administradorRepo.save(guardado);

        Administrador administradorBuscado = administradorRepo.findById("111").orElse(null);

        Assertions.assertEquals("tatiana_nuevo@gmail.com", administradorBuscado.getEmail());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Administrador> administradores=administradorRepo.findAll();

        administradores.forEach( a -> System.out.println(a));
    }
}
