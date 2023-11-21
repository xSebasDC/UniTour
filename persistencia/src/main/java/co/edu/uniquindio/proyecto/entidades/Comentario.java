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
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Float.valueOf;
import static java.lang.String.valueOf;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString

public class Comentario implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 200)
    @NotBlank
    private String mensaje;

    @Column(length = 200)
    private String respuesta;

    @Column(nullable = false)
    private LocalDate fecha_comentario;

    @Column(nullable = false)
    @PositiveOrZero
    private Float calificacion;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Producto producto;

    //---------------------------------- CONSTRUCTOR ----------------------
    public Comentario(String mensaje, LocalDate fecha_comentario, Float calificacion, Usuario usuario, Producto producto) {
        this.mensaje = mensaje;
        this.fecha_comentario = fecha_comentario;
        this.calificacion = calificacion;
        this.usuario = usuario;
        this.producto = producto;
    }

    public String getFechaEstilo(){
        return fecha_comentario.format(DateTimeFormatter.ISO_DATE);
    }

    public Integer getCalificacionEntera(){
        return Math.round(calificacion);
    }
}
