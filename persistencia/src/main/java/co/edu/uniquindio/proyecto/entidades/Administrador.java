/**
 * Autores
 *
 * Michelle Alejandra González Hernández
 * Tatiana Cubillos Montes
 * Sebastián Delgado Cardenas
 */

package co.edu.uniquindio.proyecto.entidades;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

// SE INVOCA FUNCIONES DE LOMBOK
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString (callSuper = true)

public class Administrador extends Persona implements Serializable {

    //---------------------------------- CONSTRUCTOR ----------------------

    public Administrador(String codigo, String nombre, String email, String password, String username) {
        super(codigo, nombre, email, password, username);
    }
}
