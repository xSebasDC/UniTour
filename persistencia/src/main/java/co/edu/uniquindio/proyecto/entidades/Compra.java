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
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString

public class Compra implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column (nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String medioPago;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @OneToMany (mappedBy = "compra")
    @ToString.Exclude
    @JsonIgnore
    private List<DetalleCompra> listaDetallesCompra;

    //---------------------------------- COSNTURCTOR ----------------------
    public Compra(LocalDate fecha, String medioPago, Usuario usuario) {
        this.fecha = fecha;
        this.medioPago = medioPago;
        this.usuario = usuario;
    }

    public Compra(LocalDate fecha,Usuario usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
    }
}