/**
 * Autores
 *
 * Michelle Alejandra González Hernández
 * Tatiana Cubillos Montes
 * Sebastián Delgado Cardenas
 */
package co.edu.uniquindio.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subasta implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false)
    @Future (message = "La facha debe estar definida en el futuro")
    private LocalDate fechaLimite;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    private Producto producto;

    @OneToMany (mappedBy = "subasta")
    @ToString.Exclude
    @JsonIgnore
    private List<DetalleSubasta> listaDetalleSubasta;

}
