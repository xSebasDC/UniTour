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
import java.util.List;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString

public class Ciudad implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false)
    private String nombre;

    //---------------------------------- RELACIONES ----------------------
    @OneToMany(mappedBy = "ciudad")
    @ToString.Exclude
    @JsonIgnore
    private List<Usuario> listaUsuarios;

    @OneToMany(mappedBy = "ciudad")
    @ToString.Exclude
    @JsonIgnore
    private List<Producto> listaProductos;

    //---------------------------------- CONSTRUCTOR ----------------------
    public Ciudad(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public Ciudad(String nombre) {
        this.nombre = nombre;
    }
}
