package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.entidades.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UsuarioYProducto {

    private String email;
    private String nombre;
    private Producto producto;
}
