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
import javax.validation.constraints.NotBlank;
import java.util.List;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categoria {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @ToString.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany
    @ToString.Exclude
    @JsonIgnore
    private List<Producto> listaProductos;

    //---------------------------------- CONSTRUCTOR ----------------------

    public Categoria(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
}
