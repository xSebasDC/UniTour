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
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

public class Chat implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private Producto producto;

    //---------------------------------- RELACIONES ----------------------
    @OneToMany (mappedBy = "chat")
    @ToString.Exclude
    @JsonIgnore
    private List<Mensaje> listaMensajes;

    //---------------------------------- CONSTRUCTOR ----------------
    public Chat(int codigo) {
        this.codigo = codigo;
    }

    public Chat(Integer codigo, Usuario usuario, Producto producto) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.producto = producto;
    }

    public Chat(Usuario usuario, Producto producto) {
        this.usuario = usuario;
        this.producto = producto;
    }
}
