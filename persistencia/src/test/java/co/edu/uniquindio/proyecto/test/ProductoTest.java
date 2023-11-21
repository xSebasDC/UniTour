package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
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
public class ProductoTest {

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    UsuarioRepo usuarioRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void registrarTest(){
        Ciudad ciudad = ciudadRepo.findById(1).orElse(null);
        Usuario usuario = usuarioRepo.findById("123").orElse(null);
        System.out.println(usuario.getNombre());
        System.out.println(ciudad.getNombre());
        Producto producto = new Producto("iphone 13","Iphone 13",20,3500000.0, LocalDate.now(), usuario);
        producto.setCiudad(ciudad);
        producto.setUsuario(usuario);
        producto.setDescripcion("Bonito");

        Producto productoGuardada = productoRepo.save(producto);

        Assertions.assertNotNull(productoGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){
        productoRepo.deleteById(2);

        Producto productoBuscada= productoRepo.findById(2).orElse(null);

        Assertions.assertNull(productoBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Producto guardado= productoRepo.findById(1).orElse(null);
        guardado.setDescripcion("Bueno bonito y barato");

        productoRepo.save(guardado);

        Producto productoBuscada = productoRepo.findById(1).orElse(null);

        Assertions.assertEquals("Bueno bonito y barato", productoBuscada.getDescripcion());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Producto> productos=productoRepo.findAll();

        productos.forEach( p -> System.out.println(p));
    }
}
