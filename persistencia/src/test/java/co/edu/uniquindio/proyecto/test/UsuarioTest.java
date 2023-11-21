package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioTest {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private CiudadRepo ciudadRepo;

    @Test
    public void registrarTest(){
        Ciudad ciudad= new Ciudad(4,"Armenia");
        ciudadRepo.save(ciudad);

        List <String> telefonos= new ArrayList<>();
        telefonos.add("15651");
        telefonos.add("16165");

        Usuario usuario= new Usuario("12","cristiano ronaldo", "cr7@gmail.com", "siuu", "cr7");

        Usuario usuarioGuardado =usuarioRepo.save(usuario);

        Assertions.assertNotNull(usuarioGuardado);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest(){
        usuarioRepo.deleteById("123");

        Usuario usuarioBuscado= usuarioRepo.findById("123").orElse(null);

        Assertions.assertNull(usuarioBuscado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest(){
        Usuario guardado= usuarioRepo.findById("124").orElse(null);
        guardado.setEmail("tatiana_nuevo@gmail.com");

        usuarioRepo.save(guardado);

        Usuario usuarioBuscado = usuarioRepo.findById("124").orElse(null);

        Assertions.assertEquals("tatiana_nuevo@gmail.com", usuarioBuscado.getEmail());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest (){
        List<Usuario> usuarios=usuarioRepo.findAll();

        usuarios.forEach( u -> System.out.println(u));
    }



}
