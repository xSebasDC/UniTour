package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.ProductoValido;
import co.edu.uniquindio.proyecto.dto.UsuarioYCompra;
import co.edu.uniquindio.proyecto.dto.ProductosPorUsuario;
import co.edu.uniquindio.proyecto.dto.UsuarioYProducto;
import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.repositorios.*;
import org.assertj.core.api.ObjectEnumerableAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PruebasTest {
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private CiudadRepo ciudadRepo;

    @Autowired
    private ComentarioRepo comentarioRepo;

    @Autowired
    private CompraRepo compraRepo;

    @Autowired
    private SubastaRepo subastaRepo;

    @Autowired
    private CategoriaRepo categoriaRepo;

    //----------------------------------------------- VÍDEO 06 --------------------------------------------------
    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarNombreTest() {
        List<Usuario> lista = usuarioRepo.findAllByNombreContains("Tatiana");
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarEMailTest() {
        Optional<Usuario> usuario = usuarioRepo.findByEmail("tatiana@gmail.com");
        if (usuario.isPresent()) {
            System.out.println(usuario.get());
        } else {
            System.out.println("No existe este correo");
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void paginarListaTest() {
        Pageable paginador = PageRequest.of(0, 2);
        Page<Usuario> lista = usuarioRepo.findAll(paginador);
        System.out.println(lista.stream().collect(Collectors.toList()));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void ordenarListaTest() {
        List<Usuario> lista = usuarioRepo.findAll(Sort.by("nombre"));
        System.out.println(lista);
    }

    //--------------------------- VÍDEO 7---------------------------------------------------------------
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerNombreVendedorTest() {
        String nombre = productoRepo.obtenerNombreVendedor(2);
        Assertions.assertEquals("Sebastian", nombre);
    }

 /*   @Test
    @Sql("classpath:dataset.sql")
    public void obtenerFavoritosUsuarioTest() {
        List<Producto> favoritos = usuarioRepo.obtenerProductosFavoritos("michelle@gmail.com");
        favoritos.forEach(System.out::println);
        Assertions.assertEquals(2, favoritos.size());
    }*/

    @Test
    @Sql("classpath:dataset.sql")
    public void listarUsuariosCiudadTest() {
        List<Usuario> usuarios = ciudadRepo.listarUsuarios("Armenia");
        Assertions.assertEquals(2, usuarios.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarUsuariosProductosTest() {
        List<Object[]> respuesta = usuarioRepo.listarUsuariosYProductos();

        for (Object[] objeto : respuesta) {
            System.out.println(objeto[0] + "----" + objeto[1]);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarUsuariosProductosTest2() {
        List<UsuarioYProducto> respuesta = usuarioRepo.listarUsuariosYProductos2();
        respuesta.forEach(System.out::println);
        Assertions.assertEquals(5, respuesta.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarProductosComentarios() {
        List<Object[]> respuesta = comentarioRepo.listarProductosYComentarios();

        respuesta.forEach(objeto -> System.out.println(objeto[0] + "---" + objeto[1]));
        Assertions.assertEquals(4, respuesta.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarUsuariosComentariosTest() {
        List<Usuario> usuarios = productoRepo.listarUsuariosComentarios(2);
        usuarios.forEach(System.out::println);
        Assertions.assertEquals(2, usuarios.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarProductosValidosTest1() {
        List<Object[]> productos = productoRepo.listarProductosValidos1(LocalDate.now());
        productos.forEach(objeto -> System.out.println(objeto[0] + "---" + objeto[1] + "---" + objeto[2] + "---" + objeto[3]));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarProductosValidosTest2() {
        List<ProductoValido> productos = productoRepo.listarProductosValidos2(LocalDate.now());
        productos.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarUsuariosComprasTest() {
        List<UsuarioYCompra> respuesta = usuarioRepo.listarUsuariosYCompras();
        respuesta.forEach(System.out::println);
        Assertions.assertEquals(5, respuesta.size());
    }

    //------------------------------------------------- Vídeo 08
    // Acá hay un error
    @Test
    @Sql("classpath:dataset.sql")
    public void listaProductosCompradosTest() {
        Long totalProductos = compraRepo.obtenerListaProductosComprados("123");
        System.out.println("El total es: ----------> " + totalProductos);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listaProductoCategoriasTest() {
        List<Object[]> productos = productoRepo.obtenerTotalProductosPorCategoria();
        productos.forEach(r -> System.out.println(r[0] + ", " + r[1]));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listaProductoSinComentariosTest() {
        List<Producto> productos = productoRepo.obtenerProductosSinComentarios();
        productos.forEach(System.out::println);
        Assertions.assertTrue(productos.get(0).getCodigo() == 3);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPorNombreTest() {
        List<Producto> productos = productoRepo.buscarProductoNombre("Computador");
        productos.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerProductosEnVentaTest() {
        List<ProductosPorUsuario> productos = productoRepo.obtenerProductosEnVenta();
        Long[] arrayRespuesta = new Long[(productos.size())];

        for (int i = 0; i < productos.size(); i++) {
            arrayRespuesta[i] = productos.get(i).getRegistros();
        }
        Assertions.assertArrayEquals(new Long[]{1L, 1L, 1L}, arrayRespuesta);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerValorTotalVentas() {
        Long total = compraRepo.calcularTotalVenta("123");
        //System.out.println(total);
        Assertions.assertEquals(800000L, total);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerValorTotalCompras() {
        Long total = compraRepo.calcularTotalCompras("123");
        //System.out.println(total);
        Assertions.assertEquals(1250000L, total);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCategoriaMasUsada() {
        List<Object[]> productos = productoRepo.obtenerCategoriaMasUsada();
        productos.forEach(r -> System.out.println(r[0]));

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerValorMasAltoSubasta() {
        Float valorMasAlto = subastaRepo.obtenerValorMasAlto(12);
        //System.out.println(valorMasAlto);
        Assertions.assertEquals(100000.0F, valorMasAlto);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerSubastasDiponibles() {
        List<Subasta> subastas = subastaRepo.listarSubastasDisponibles();
        subastas.forEach(System.out::println);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerComprasUsuario() {
        List<Compra> comprasUsuarios = compraRepo.obtenerComprasUsuario("123");
        comprasUsuarios.forEach(r -> System.out.println(r));

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPromedioCalificacion() {
        Float calificacion = productoRepo.obtenerPromedioCalificaciones(2);
        Assertions.assertEquals(4.5F, calificacion);

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerTotalComprasUsuario() {
        List<Object[]> comprasUsuario = usuarioRepo.obtenerTotalComprasUsuario("126");
        comprasUsuario.forEach(r -> System.out.println(r[0].toString() + " " + r[1].toString()));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerGanadorSubasta() {
        List<Usuario> usuario = subastaRepo.obtenerGanadorSubasta(12);
        //System.out.println(usuario);
        usuario.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCategoriasYCalificacionPromedio() {
        List<Object[]> categorias = categoriaRepo.listarCategoriasYCalificaciónPromedio();
        categorias.forEach(r -> System.out.println(r[0] + " calificacion: " + r[1]));
        //categorias.forEach(r -> System.out.println(r[0]));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarSubastasDeCategoria() {
        List<Subasta> subastas = categoriaRepo.obtenerSubastasCategoria(1);
        subastas.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarProductosConDescuentoEnRango() {
        List<Producto> productos = productoRepo.obtenerProductosConDescuentoRango(0.0, 0.05);
        productos.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void calcularCantidadPorductosCategoriaSubasta(){
        List<Object[]> respuesta = subastaRepo.cantidadProductosSubastaCategoria();
        respuesta.forEach(r -> System.out.println(r[0]+", "+ r[1]));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerTotalMediosPagos(){
        List <Object[]> respuesta = compraRepo.calcularTotalMediosPago();
        respuesta.forEach(r -> System.out.println(r[0]+", "+ r[1]));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerChatVendedor(){
        List<Chat> respuesta = productoRepo.chatUsuario("123");
        respuesta.forEach(System.out::println);
    }


    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerComentariosSinRespuesta(){
        List<Comentario> respuesta = productoRepo.listaComentariosSinRespuesta(2);
        respuesta.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerProductoMasVendidoCategoria(){
        List<Object[]> respuesta;
        respuesta = compraRepo.productoMasVendidoCategoria("0");
        respuesta.forEach(r -> System.out.println(r[0]+", "+ r[1]));
    }
}
