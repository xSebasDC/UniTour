package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class UsuarioServiciotest {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Test
    public void registrarTest(){
        Usuario u = new Usuario("123", "Pepito", "pepito@email.com", "12345", "elpepe" );
        try {
            Usuario respuesta = usuarioServicio.registrarUsuario(u);
            Assertions.assertNotNull(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.assertTrue(false);
        }
    }

    @Test
    public void eliminarTest(){
        try {
            usuarioServicio.eliminarUsuario("123");
            Assertions.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.assertTrue(false);
        }
    }

    @Test
    public void listarTest() throws Exception {
        Usuario u = new Usuario("121", "Marcos", "pepido@email.com", "12345", "elpepe");
        usuarioServicio.registrarUsuario(u);

        List<Usuario> lista = usuarioServicio.listarUsuarios();
        lista.forEach(System.out::println);
    }

    @Test
    public void actualizarTest() {
        try {
            Usuario u = usuarioServicio.obtenerUsuario("123");
            u.setPassword("#912011");
            Usuario modificado = usuarioServicio.actualizarUsuario(u);
            Assertions.assertEquals("#912011", modificado.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest() {
        try {
            Usuario usuario = usuarioServicio.iniciarSesión("pepito@email.com","12345" );
            Assertions.assertNotNull(usuario);
        } catch (Exception e) {
            Assertions.assertTrue(false, e.getMessage());
        }

    }

}
