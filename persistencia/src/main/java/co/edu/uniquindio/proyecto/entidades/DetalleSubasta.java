/**
 * Autores
 *
 * Michelle Alejandra González Hernández
 * Tatiana Cubillos Montes
 * Sebastián Delgado Cardenas
 */
package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString

public class DetalleSubasta implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDate fecha_subasta;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subasta subasta;

    //---------------------------------- CONSTURCTOR ----------------------
    public DetalleSubasta(Double valor, LocalDate fecha_subasta, Usuario usuario, Subasta subasta) {
        this.valor = valor;
        this.fecha_subasta = fecha_subasta;
        this.usuario = usuario;
        this.subasta = subasta;
    }
}
