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
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

// SE INVOCA FUNCIONES DE LOMBOK
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@AllArgsConstructor
@ToString

public class Persona implements Serializable {

    //---------------------------------- ATRIBUTOS ----------------------
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 10)
    @Length(max = 10)
    private String codigo;

    @NotBlank(message = "Debe tener un nombre obligatoriamente")
    @Column(nullable = false, length = 100)
    @Length(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caractes")
    @NotBlank
    private String nombre;

    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 120)
    @Length(max = 120)
    @Email(message = "Escriba un email válido")
    @NotBlank
    private String email;

    @NotBlank(message = "El usuario debe tener una contraseña")
    @Column(nullable = false, length = 100)
    @Length(max = 100, message = "La contraseña debe tener máximo 100 caracteres")
    @NotBlank
    //@JsonIgnore
    private String password;

    @NotBlank(message = "El usuario debe tener un username")
    @Column(nullable = false, length = 50)
    @Length(max = 50)
    private String username;
}
