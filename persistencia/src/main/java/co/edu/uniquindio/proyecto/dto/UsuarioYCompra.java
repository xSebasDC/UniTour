package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.entidades.Compra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UsuarioYCompra {

    private String email;
    private String nombre;
    private Compra producto;
}
