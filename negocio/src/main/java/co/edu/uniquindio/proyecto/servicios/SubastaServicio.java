package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.DetalleSubasta;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Subasta;

import java.util.List;

public interface SubastaServicio {

    //Crear subastas.
    Subasta crearSubasta (Subasta subasta) throws Exception;

    List<Producto> obtenerSubastas();

    Subasta obtenerSubastaProducto(String id);

    void guardarDetalleSubasta(DetalleSubasta detalle);

}
