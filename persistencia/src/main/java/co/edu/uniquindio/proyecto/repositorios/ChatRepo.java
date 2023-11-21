package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Integer> {

    @Query("Select c from Chat c where c.producto.codigo = :codigoProducto and c.usuario.codigo = :codigoUsuario")
    Chat obtenerChatUsuarios(String codigoUsuario, Integer codigoProducto);
}
