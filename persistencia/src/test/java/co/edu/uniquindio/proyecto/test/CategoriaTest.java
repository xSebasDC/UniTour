package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Administrador;
import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoriaTest {

    @Autowired
    CategoriaRepo categoriaRepo;

    @Test
    public void registrarTest(){

        Categoria categoria = new Categoria(3,"Ropa");

        Categoria categoriaGuardada =categoriaRepo.save(categoria);

        Assertions.assertNotNull(categoriaGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){

        categoriaRepo.deleteById(0);

        Categoria categoriaBuscada= categoriaRepo.findById(0).orElse(null);

        Assertions.assertNull(categoriaBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){


        Categoria guardado= categoriaRepo.findById(2).orElse(null);
        guardado.setNombre("Electrodomesticos");

        categoriaRepo.save(guardado);

        Categoria categoriaBuscada = categoriaRepo.findById(2).orElse(null);

        Assertions.assertEquals("Electrodomesticos", categoriaBuscada.getNombre());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Categoria> categorias=categoriaRepo.findAll();

        categorias.forEach( cat -> System.out.println(cat));
    }
}
