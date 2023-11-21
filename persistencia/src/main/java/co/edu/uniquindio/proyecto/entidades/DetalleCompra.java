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
import javax.validation.constraints.Positive;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString

public class DetalleCompra {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Positive
    @Column(nullable = false)
    private Integer unidades;

    @Positive
    @Column(nullable = false)
    private Float precio_producto;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Compra compra;

    @ManyToOne
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Producto producto;

    //---------------------------------- CONSTRUCTOR ----------------------
    public DetalleCompra(Integer unidades, Float precio_producto, Producto producto, Compra compra) {
        this.unidades = unidades;
        this.precio_producto = precio_producto;
        this.producto = producto;
        this.compra = compra;
    }

}
