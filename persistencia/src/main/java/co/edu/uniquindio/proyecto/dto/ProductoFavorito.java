package co.edu.uniquindio.proyecto.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ProductoFavorito {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String nombre, imagenPrincipal;
    private Double precio;
}
