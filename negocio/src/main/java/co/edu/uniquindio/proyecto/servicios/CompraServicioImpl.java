package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.dto.ProductoCarrito;
import co.edu.uniquindio.proyecto.entidades.Compra;
import co.edu.uniquindio.proyecto.entidades.DetalleCompra;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CompraRepo;
import co.edu.uniquindio.proyecto.repositorios.DetalleCompraRepo;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class CompraServicioImpl implements CompraServicio {

    private final CompraRepo compraRepo;
    private final UsuarioRepo usuarioRepo;
    private final DetalleCompraRepo detalleCompraRepo;
    private final ProductoRepo productoRepo;

    public CompraServicioImpl(CompraRepo compraRepo, UsuarioRepo usuarioRepo, DetalleCompraRepo detalleCompraRepo, ProductoRepo productoRepo) {
        this.compraRepo = compraRepo;
        this.usuarioRepo = usuarioRepo;
        this.detalleCompraRepo = detalleCompraRepo;
        this.productoRepo = productoRepo;
    }

    @Override
    public Compra crearCompra(Usuario usuario) throws Exception {
        if (usuarioRepo.findById(usuario.getCodigo()) != null) {
            return new Compra(LocalDate.now(), usuario);
        } else {
            throw new Exception("El usuario no existe");
        }
    }

    @Override
    public Compra agregarDetalleCompra(Compra compra, DetalleCompra detalle) {
        compra.getListaDetallesCompra().add(detalle);
        return compra;
    }

    @Override
    public Compra efectuarCompra(Compra compra) throws Exception {
        for (DetalleCompra d : compra.getListaDetallesCompra()) {
            detalleCompraRepo.save(d);
        }
        return compraRepo.save(compra);
    }

    @Override
    public Compra agregarCompra(ArrayList<ProductoCarrito> productoCarrito, Usuario usuario, String medioPago) throws Exception {
        try {
            Compra compra = new Compra();
            compra.setFecha(LocalDate.now(ZoneId.of("America/Bogota")));
            compra.setUsuario(usuario);
            compra.setMedioPago(medioPago);

            compraRepo.save(compra);

            DetalleCompra detalle;
            List<DetalleCompra> lista = new ArrayList<>();
            for (ProductoCarrito p : productoCarrito) {
                detalle = new DetalleCompra();
                if(detalleCompraRepo.verificarUnidades(p.getCodigo()) > p.getUnidades()){
                    detalle.setCompra(compra);
                    detalle.setPrecio_producto(p.getPrecio());
                    detalle.setUnidades(p.getUnidades());
                    detalle.setProducto(productoRepo.findById(p.getCodigo()).get());
                    //Verificar las unidades
                    quitarUnidades(p.getCodigo(), p.getUnidades());
                    detalleCompraRepo.save(detalle);
                    lista.add(detalle);
                    compra.setListaDetallesCompra(lista);
                }

            }

            return compra;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public void quitarUnidades (Integer codigoProducto, Integer unidadesComprada){
       Optional<Producto> producto = productoRepo.findById(codigoProducto);
       Producto productoActual =producto.get();
       productoActual.setUnidades(productoActual.getUnidades() - unidadesComprada );
    }

    @Override
    public List<Compra> listarComprasUsuario(String idUsuario) {
        System.out.println("\n\n" + idUsuario);
        return compraRepo.listarComprasUsuario(idUsuario);
    }

    @Override
    public void enviarCorreoElectronico(List<DetalleCompra> listaDetallesCompra) {

    }

    @Override
    public String enviarCorreoElectronico(Usuario usuario, Compra compra) throws MessagingException {

        String mensaje = "-----------------¡MUCHAS GARCIAS!------------- \n\nUsted ha realizado una compra en UniShop \n\nSu factura es: \n\n";
        String emailCliente = usuario.getEmail();
        Properties props = new Properties();
        Double totalCompra = 0.0;

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getDefaultInstance(props);

        String correoRemitente = "tienda.unishop2021@gmail.com";
        String contrasenaRemitente = "programacionavanzada2021";
        String correoReceptor = emailCliente;
        String asunto = "Detalles de la compra realizada en UniShop";

        mensaje += "La compra realizada en la fecha; " + compra.getFecha().toString() + "\n";


        for (int i = 0; i < compra.getListaDetallesCompra().size(); i++) {
            System.out.println("Entro "+ i+ compra.getListaDetallesCompra().get(i).getProducto().getNombre());
            mensaje += "\t \t Unidades:";
            mensaje += compra.getListaDetallesCompra().get(i).getUnidades();
            mensaje += "\t \t Producto:";
            mensaje += compra.getListaDetallesCompra().get(i).getProducto().getNombre();
            mensaje += "\t \t SubTotal:";
            mensaje += compra.getListaDetallesCompra().get(i).getProducto().getPrecioFinal() + "";
            mensaje += "\n";

            totalCompra += compra.getListaDetallesCompra().get(i).getProducto().getPrecioFinal();

        }

        mensaje += "\n Total de la compra: ";

        mensaje += totalCompra + "\n";

        mensaje += "\n El método de pago escogido fue: ";
        mensaje += compra.getMedioPago().toString();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(correoRemitente));

        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(correoReceptor));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport t = session.getTransport("smtp");
        t.connect(correoRemitente, contrasenaRemitente);
        t.sendMessage(message, message.getRecipients(MimeMessage.RecipientType.TO));

        String verificacion = "Revisa tu correo eléctronico";

        t.close();

        return verificacion;
    }
}
