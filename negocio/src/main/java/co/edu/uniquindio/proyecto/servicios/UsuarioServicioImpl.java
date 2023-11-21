package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.stereotype.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo;

    public UsuarioServicioImpl(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Usuario registrarUsuario(Usuario u) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(u.getCodigo());
        if (buscado.isPresent()) {
            throw new Exception("El codigo del usuario ya existe");
        }

        buscado = buscarPorEmail(u.getEmail());
        if (buscado.isPresent()) {
            throw new Exception("El email del usuario ya existe");
        }

        buscado = usuarioRepo.findByUsername(u.getUsername());
        if (buscado.isPresent()) {
            throw new Exception("El usarname del usuario ya existe");
        }

        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        u.setPassword(passwordEncryptor.encryptPassword(u.getPassword()));


        return usuarioRepo.save(u);
    }

    @Override
    public List<Usuario> buscarUsuarios(String nombreUsuario, String[] filtros) {
        return usuarioRepo.buscarUsuarioNombre(nombreUsuario);
    }

    @Override
    public Usuario actualizarUsuario(Usuario u) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(u.getCodigo());
        if (buscado.isEmpty()) {
            throw new Exception("El usuario no existe");
        }
        return usuarioRepo.save(u);
    }

    @Override
    public Usuario obtenerUsuario(String codigo) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(codigo);
        if (buscado.isEmpty()) {
            throw new Exception("El usuario no existe");
        }
        return buscado.get();
    }

    private Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepo.findByEmail(email);
    }

    @Override
    public void eliminarUsuario(String codigo) throws Exception {
        Optional<Usuario> buscado = usuarioRepo.findById(codigo);
        if (buscado.isEmpty()) {
            throw new Exception("El código del usuario ya existe");
        }
        usuarioRepo.deleteById(codigo);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario iniciarSesión(String email, String password) throws Exception {
        Usuario usuarioEmail = usuarioRepo.findByEmail(email).orElseThrow(() -> new Exception("El email es incorrecto"));
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, usuarioEmail.getPassword())) {
            return usuarioEmail;
        } else {
            throw new Exception("La contraseña es incorrecta");
        }
    }

    @Override
    public String recuperarContraseña(String email) throws Exception {
        String mensaje = "¡Recuperación de contraseña! \n \n \nUsted ha solicitado la contraseña registrada en UniShop \n";

        String correoUsuario = email;

        Properties props = new Properties();

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getDefaultInstance(props);

        String correoRemitente = "tienda.unishop2021@gmail.com";
        String contrasenaRemitente = "programacionavanzada2021";
        String correoReceptor = correoUsuario;
        String asunto = "Recuperación de contraseña";

        mensaje += "\n\nTu contraseña es: \n";
        Optional<Usuario> usuario = usuarioRepo.findByEmail(email);

       mensaje += usuario.get().getPassword() + "\n";
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

}
