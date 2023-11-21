package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ProductoCarrito;
import co.edu.uniquindio.proyecto.entidades.Compra;
import co.edu.uniquindio.proyecto.entidades.DetalleCompra;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.List;

public interface CompraServicio {

    //Crea una compra para irle agregando detalles
    Compra crearCompra(Usuario usuario) throws Exception;

    //Añadir un detalle a la compara
    Compra agregarDetalleCompra(Compra compra, DetalleCompra detalle);

    //Efectua compra
    Compra efectuarCompra(Compra compra) throws Exception;
    Compra agregarCompra(ArrayList<ProductoCarrito> productoCarrito, Usuario usuario, String medioPago) throws Exception;

    //Listar sus propias compras.
    List<Compra> listarComprasUsuario (String idUsuario);

    //Enviar correo electronico con los detalles de la compra
    void enviarCorreoElectronico (List<DetalleCompra> listaDetallesCompra);

    //Enviar correo electronico con los detalles de la compra
    String enviarCorreoElectronico (Usuario usario, Compra compra) throws AddressException, MessagingException;
}
