package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CiudadTest {

    @Autowired
    CiudadRepo ciudadRepo;

    @Test
    public void registrarTest(){
        Ciudad ciudad = new Ciudad(4,"Barranquilla");

        Ciudad ciuidadGuardada =ciudadRepo.save(ciudad);

        Assertions.assertNotNull(ciuidadGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){
        ciudadRepo.deleteById(1);

        Ciudad ciuidadBuscada= ciudadRepo.findById(1).orElse(null);

        Assertions.assertNull(ciuidadBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Ciudad guardado= ciudadRepo.findById(2).orElse(null);
        guardado.setNombre("Bogota");

        ciudadRepo.save(guardado);

        Ciudad ciuidadBuscada = ciudadRepo.findById(2).orElse(null);

        Assertions.assertEquals("Bogota", ciuidadBuscada.getNombre());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Ciudad> ciudades = ciudadRepo.findAll();

        ciudades.forEach( ciu -> System.out.println(ciu));
    }
}
