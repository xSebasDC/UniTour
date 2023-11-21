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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Producto implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "Debe añadir una descripción")
    @Column(nullable = false)
    private String descripcion;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer unidades;

    @Positive
    @Column(nullable = false)
    private Double precio;

    @Future
    @Column(nullable = false)
    private LocalDate fechaLimite;

    @PositiveOrZero
    private Double descuento;

    @ElementCollection
    private List<String> imagenes;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    @ToString.Exclude
    //@JoinColumn(nullable = false)
    private Usuario usuario;

    @ManyToOne
    @ToString.Exclude
    private Ciudad ciudad;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Subasta> listaSubastas;

    @OneToMany (mappedBy = "producto", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Comentario> listaComentarios;

    @OneToMany (mappedBy = "producto", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Chat> listaChats;

    @OneToMany (mappedBy = "producto", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<DetalleCompra> listaDetalleCompra;

    @ManyToMany(mappedBy = "listaProductos", cascade = CascadeType.ALL)
    @ToString.Exclude
    //@JsonIgnore
    private List<Categoria> listaCategorias;

    //---------------------------------- CONSTRUCTOR ----------------------
    public Producto(String nombre, String descripcion, Integer unidades, Double precio, LocalDate fechaLimite, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.precio = precio;
        this.fechaLimite = fechaLimite;
        this.usuario = usuario;
        this.imagenes = new ArrayList<>();
    }



    /*
    public Producto(String nombre, String descripcion, Integer unidades, Double precio, LocalDate fechaLimite, Usuario usuario, List<String> imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.precio = precio;
        this.fechaLimite = fechaLimite;
        this.usuario = usuario;
        this.imagenes = new ArrayList<>();
    }
     */

    public String getImagenPrincipal(){
        if(imagenes != null && !imagenes.isEmpty()){

            return imagenes.get(0);
        }else{
            return "default.png";
        }
    }

    public Double getPrecioFinal(){
        if (descuento>0){
            Double resta= (descuento/100)* precio;
            return precio-resta;
        }
        return precio;
    }

}

