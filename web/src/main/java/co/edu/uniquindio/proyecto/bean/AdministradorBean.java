package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.servicios.*;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class AdministradorBean{

    @Getter
    @Setter
    @Value("#{seguridadBean.administradorSeccion}")
    private Administrador adminSeccion;

    @Autowired
    private AdministradorServicio administradorServicio;

    @Autowired
    private CategoriaServicio categoriaServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Getter
    @Setter
    private PieChartModel pieProductosCategoria;

    @Getter
    @Setter
    private PieChartModel pieUsuariosCiudad;

    @Getter
    @Setter
    private BarChartModel barProductosCiudad;

    @Getter
    @Setter
    private BarChartModel barProductosCalificacion;

    @Getter
    @Setter
    private BarChartModel barPublicacionesUsuario;

    @PostConstruct
    public void inicializar() {
        graficarProductosXCategoria();
        graficarUsuariosXCiudad();
        graficarCantidadProductosXCiudades();
        graficarPublicacionesUsuarios();
        graficarProductosBasadosEnCalificacion();
    }

    private void graficarProductosXCategoria() {
        pieProductosCategoria = new PieChartModel();
        List<Categoria> listaCategorias = categoriaServicio.listarCategorias();

        for (Categoria c : listaCategorias) {
            List<Producto> listaProductos = administradorServicio.reportarCantidadProductosXCategoria(c.getCodigo());
            pieProductosCategoria.set(c.getNombre(), listaProductos.size());
        }

        pieProductosCategoria.setLegendPosition("e");
        pieProductosCategoria.setFill(true);
        pieProductosCategoria.setShowDataLabels(true);
        pieProductosCategoria.setDiameter(180);
    }

    private void graficarUsuariosXCiudad(){
        pieUsuariosCiudad = new PieChartModel();
        List<Ciudad> listaC = ciudadServicio.listarCiudades();

        for (Ciudad c : listaC) {
            List<Usuario> listaUsuarios = administradorServicio.reportarCantidadUsuariosXCiudad(c.getCodigo());
            pieUsuariosCiudad.set(c.getNombre(), listaUsuarios.size());
        }

        pieUsuariosCiudad.setLegendPosition("e");
        pieUsuariosCiudad.setFill(true);
        pieUsuariosCiudad.setShowDataLabels(true);
        pieUsuariosCiudad.setDiameter(180);
    }

    private void graficarCantidadProductosXCiudades() {
        barProductosCiudad = new BarChartModel();
        List<Number> valores = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColor = new ArrayList<>();
        BarChartDataSet barDataSet = new BarChartDataSet();
        ChartData data = new ChartData();
        barDataSet.setLabel("Productos");

        BarChartOptions opciones = opcionesBarras("", 0, 10);
        List<Ciudad> listaC = ciudadServicio.listarCiudades();
        for (Ciudad c : listaC) {
            List<Producto> productos = administradorServicio.reportarCantidadProductosXCiudad(c.getCodigo());
            valores.add(productos.size());
            labels.add(c.getNombre());
            bgColor.add("rgba(54, 162, 235, 0.2)");
        }

        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setData(valores);
        data.addChartDataSet(barDataSet);
        data.setLabels(labels);
        barProductosCiudad.setData(data);
        barProductosCiudad.setOptions(opciones);
    }

    private void graficarPublicacionesUsuarios() {
        barPublicacionesUsuario = new BarChartModel();
        List<Number> valores = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColor = new ArrayList<>();
        BarChartDataSet barDataSet = new BarChartDataSet();
        ChartData data = new ChartData();
        barDataSet.setLabel("Publicaciones");

        BarChartOptions opciones = opcionesBarras("", 0, 10);
        List<Usuario> listaU = usuarioServicio.listarUsuarios();
        for (Usuario u : listaU) {
            List<Producto> productos = productoServicio.listarProductosPropios(u.getCodigo());
            valores.add(productos.size());
            labels.add(u.getNombre());
            bgColor.add("rgba(54, 162, 235, 0.2)");
        }

        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setData(valores);
        data.addChartDataSet(barDataSet);
        data.setLabels(labels);
        barPublicacionesUsuario.setData(data);
        barPublicacionesUsuario.setOptions(opciones);
    }

    private void graficarProductosBasadosEnCalificacion() {
        barProductosCalificacion = new BarChartModel();
        List<Number> valores = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> bgColor = new ArrayList<>();
        BarChartDataSet barDataSet = new BarChartDataSet();
        ChartData data = new ChartData();
        barDataSet.setLabel("Prom calificación");

        BarChartOptions opciones = opcionesBarras("", 0, 5);

        List<Producto> productos = productoServicio.listarProductos();
        for (Producto p : productos) {
            valores.add(productoServicio.obtenerCalificacionPromedio(p.getCodigo()));
            labels.add(p.getNombre());
            bgColor.add("rgba(54, 162, 235, 0.2)");
        }

        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setData(valores);
        data.addChartDataSet(barDataSet);
        data.setLabels(labels);
        barProductosCalificacion.setData(data);

        barProductosCalificacion.setOptions(opciones);
    }

    public BarChartOptions opcionesBarras(String titulo, int min, int max) {
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        ticks.setMin(min);
        ticks.setMax(max);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(titulo);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("bottom");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#6f6f72");
        legendLabels.setFontSize(17);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        return options;
    }
}
