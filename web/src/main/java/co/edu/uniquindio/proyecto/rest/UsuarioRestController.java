package co.edu.uniquindio.proyecto.rest;

import co.edu.uniquindio.proyecto.dto.Mensaje;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioServicio.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable("id") String idUsuario){
        try {
            Usuario user = usuarioServicio.obtenerUsuario(idUsuario);
            return ResponseEntity.status(200).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){
        try {
            usuarioServicio.registrarUsuario(usuario);
            return ResponseEntity.status(200).body(new Mensaje("El usuario se creó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable("id") String idUsuario){
        try {
            usuarioServicio.eliminarUsuario(idUsuario);
            return ResponseEntity.status(200).body(new Mensaje("El usuario se eliminó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario){
        try {
            usuarioServicio.actualizarUsuario(usuario);
            return ResponseEntity.status(200).body(new Mensaje("El usuario se actualizó correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }

    @GetMapping("/favoritos/{id}")
    public ResponseEntity<?> obtenerFavoritosUsuario(@PathVariable("id") String idUsuario){
        try {
            List<Producto> lista = productoServicio.listarProductosFavoritosUsuario(idUsuario);
            return ResponseEntity.status(200).body(lista);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Mensaje(e.getMessage()));
        }
    }
}
