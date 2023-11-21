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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString(callSuper = true)

public class Usuario extends Persona implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection()
    private List<String> telefonos;

    //----------------------------------RELACIONES ----------------------

    @ManyToOne
    private Ciudad ciudad;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Chat> listaChat;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Compra> listaCompras;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Producto> listaProductos;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Producto> productosFavoritos;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Comentario> listaComentarios;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<DetalleSubasta> subastasUsuario;

    //---------------------------------- CONSTRUCTOR ----------------------
    public Usuario(String codigo, String nombre, String email, String password, String username) {
        super(codigo, nombre, email, password, username);
    }
}
