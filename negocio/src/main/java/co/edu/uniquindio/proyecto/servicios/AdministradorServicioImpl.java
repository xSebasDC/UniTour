package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Administrador;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.AdministradorRepo;
import co.edu.uniquindio.proyecto.repositorios.CategoriaRepo;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
import org.springframework.stereotype.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class AdministradorServicioImpl implements AdministradorServicio{

    private final AdministradorRepo administradorRepo;
    private final CategoriaRepo categoriaRepo;
    private final CiudadRepo ciudadRepo;

    public AdministradorServicioImpl(AdministradorRepo administradorRepo, CategoriaRepo categoriaRepo, CiudadRepo ciudadRepo) {
        this.administradorRepo = administradorRepo;
        this.categoriaRepo = categoriaRepo;
        this.ciudadRepo = ciudadRepo;
    }

    @Override
    public String recuperarContraseña(String email) throws Exception {
        String mensaje = "¡Recuperación de contraseña! \n \n \nUsted ha solicitado la contraseña registrada en UniShop \n";

        String correoAdmi = email;

        Properties props = new Properties();

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getDefaultInstance(props);

        String correoRemitente = "tienda.unishop2021@gmail.com";
        String contrasenaRemitente = "programacionavanzada2021";
        String correoReceptor = correoAdmi;
        String asunto = "Recuperación de contraseña";

        mensaje += "\n\nTu contraseña es: \n";
        Optional<Administrador> administrador = administradorRepo.findByEmail(email);

        mensaje += administrador.get().getPassword() + "\n";
        mensaje += "\n\n¡Recuerde no perder de vista esta información! \n";

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(correoRemitente));

        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(correoReceptor));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport t= session.getTransport("smtp");
        t.connect(correoRemitente, contrasenaRemitente);
        t.sendMessage(message, message.getRecipients(MimeMessage.RecipientType.TO) );

        String verificacion = "Revisa tu correo eléctronico";

        t.close();

        return verificacion;
    }

    @Override
    public Administrador iniciarSesión(String email, String password) throws Exception {
        return administradorRepo.findByEmailAndPassword(email, password).orElseThrow(() -> new Exception("Los datos de autenticación son incorrectos"));
    }

    @Override
    public void quemarAdmin() {

    }

    @Override
    public List<Producto> reportarCantidadProductosXCategoria(int codigoCat) {
        return categoriaRepo.obtenerProductosCategoria(codigoCat);
    }

    @Override
    public List<Producto> reportarCantidadProductosXCiudad(int codigoCiu) {
        return ciudadRepo.obtenerProductosXCiudad(codigoCiu);
    }

    @Override
    public List<Usuario> reportarCantidadUsuariosXCiudad(int codigoCiu) {
        return ciudadRepo.obtenerUsuariosCiudad(codigoCiu);
    }
}
