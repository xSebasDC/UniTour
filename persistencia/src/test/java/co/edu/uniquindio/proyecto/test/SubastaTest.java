package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.DetalleSubasta;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Subasta;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
import co.edu.uniquindio.proyecto.repositorios.SubastaRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubastaTest {
    @Autowired
    SubastaRepo subastaRepo;

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    UsuarioRepo usuarioRepo;

    @Test
    public void registrarTest(){
        List<DetalleSubasta> detalleSubastas= new ArrayList<>();
        Usuario usuario = usuarioRepo.findById("123").orElse(null);
        Producto producto = new Producto("iphone 13","Iphone 13",20,3500000.0,LocalDate.now(),usuario);
        productoRepo.save(producto);
        Subasta subasta = new Subasta(15,LocalDate.now(),producto,detalleSubastas);

        Subasta subastaGuardada =subastaRepo.save(subasta);

        Assertions.assertNotNull(subastaGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){

        subastaRepo.deleteById(13);

        Subasta subastaBuscada= subastaRepo.findById(13).orElse(null);

        Assertions.assertNull(subastaBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Usuario usuario = usuarioRepo.findById("123").orElse(null);
        Producto producto = new Producto("iphone 12","Iphone 12",20,3500000.0,LocalDate.now(),usuario);
        productoRepo.save(producto);

        Subasta guardado= subastaRepo.findById(14).orElse(null);
        guardado.setProducto(producto);

        subastaRepo.save(guardado);

        Subasta subastaBuscada = subastaRepo.findById(14).orElse(null);

        Assertions.assertEquals(producto, subastaBuscada.getProducto());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Subasta> subastas=subastaRepo.findAll();

        subastas.forEach( s -> System.out.println(s));
    }
}
