package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Compra;
import co.edu.uniquindio.proyecto.entidades.DetalleCompra;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CompraRepo;
import co.edu.uniquindio.proyecto.repositorios.DetalleCompraRepo;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
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
public class DetalleCompraTest {

    @Autowired
    DetalleCompraRepo detalleCompraRepo;

    @Autowired
    ProductoRepo productoRepo;

    @Autowired
    CompraRepo compraRepo;

    @Test
    public void registrarTest(){

        Producto producto = productoRepo.findById(1).orElse(null);

        Compra compra = compraRepo.findById(3050).orElse(null);

        DetalleCompra detalleCompra = new DetalleCompra(2,800000.0F,producto, compra);

        DetalleCompra detalleCompraGuardada =detalleCompraRepo.save(detalleCompra);

        Assertions.assertNotNull(detalleCompraGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){

        detalleCompraRepo.deleteById(3123);

        DetalleCompra detalleCompraBuscada= detalleCompraRepo.findById(3123).orElse(null);

        Assertions.assertNull(detalleCompraBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){


        DetalleCompra guardado= detalleCompraRepo.findById(3125).orElse(null);
        guardado.setUnidades(5);

        detalleCompraRepo.save(guardado);

        DetalleCompra detalleCompraBuscada = detalleCompraRepo.findById(3125).orElse(null);

        Assertions.assertEquals(5, detalleCompraBuscada.getUnidades());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<DetalleCompra> detalleCompras=detalleCompraRepo.findAll();

        detalleCompras.forEach( d -> System.out.println(d));
    }
}
