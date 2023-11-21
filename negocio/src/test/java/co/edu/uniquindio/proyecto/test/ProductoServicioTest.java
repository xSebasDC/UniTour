package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

@SpringBootTest (classes = NegocioApplication.class)
@Transactional
public class ProductoServicioTest {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Test
    public void obtenerProductoTest(){
        try {
            Usuario vendedor = usuarioServicio.obtenerUsuario("123");
            LocalDate ld = LocalDate.of(2021, 11, 25);
            Producto producto = new Producto("Televisor LG 55''", "Maravilloso Televisor LG 55''", 10, 4500000.0D, ld, vendedor);
            Producto publicado = productoServicio.publicarProducto(producto);
            Assertions.assertNotNull(publicado);
        }catch (Exception e){
            Assertions.assertTrue(false, e.getMessage());
        }
    }
}
