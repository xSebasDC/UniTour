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
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

public class Mensaje implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "Debe añadir un mensaje obligatoriamente")
    @Column(nullable = false, length = 200)
    private String mensaje;

    @Column(nullable = false)
    private String emisor;

    @Column(nullable = false)
    private LocalDate fecha;

    //---------------------------------- RELACIONES ----------------------
    @ManyToOne
    //@JoinColumn(nullable = false)
    private Chat chat;

    //---------------------------------- CONSTURCTOR ----------------------
    public Mensaje(String mensaje, LocalDate fecha, String emisor) {
        this.mensaje = mensaje;
        this.emisor = emisor;
        this.fecha = fecha;
    }

    public String getFechaEstilo(){
        return fecha.format(DateTimeFormatter.ISO_DATE);
    }
}
