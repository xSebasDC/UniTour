package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Categoria;
import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Compra;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CompraRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompraTest {

    @Autowired
    CompraRepo compraRepo;

    @Autowired
    UsuarioRepo usuarioRepo;

    @Test
    public void registrarTest(){
        Usuario usuario = usuarioRepo.findById("123").orElse(null);

        Compra compra = new Compra(LocalDate.now(),"paypal", usuario);

        Compra compraGuardada =compraRepo.save(compra);

        Assertions.assertNotNull(compraGuardada);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){
        compraRepo.deleteById(3020);

        Compra compraBuscada= compraRepo.findById(3020).orElse(null);

        Assertions.assertNull(compraBuscada);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Compra guardado= compraRepo.findById(3040).orElse(null);
        guardado.setMedioPago("Tarjeta de credito");

        compraRepo.save(guardado);

        Compra compraBuscada = compraRepo.findById(3040).orElse(null);

        Assertions.assertEquals("Tarjeta de credito", compraBuscada.getMedioPago());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){

        List<Compra> compras=compraRepo.findAll();

        compras.forEach( compra -> System.out.println(compra));
    }

}
