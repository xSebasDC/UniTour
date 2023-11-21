package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProductoValido {

    private String nombre;
    private String descripcion;
    private Double precio;
    private Ciudad ciudad;

}
