package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.DetalleSubasta;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Subasta;
import co.edu.uniquindio.proyecto.repositorios.DetalleSubastaRepo;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
import co.edu.uniquindio.proyecto.repositorios.SubastaRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubastaServicioImpl implements SubastaServicio{

    private final SubastaRepo subastaRepo;
    private final DetalleSubastaRepo detalleSubastaRepo;

    public SubastaServicioImpl(SubastaRepo subastaRepo, DetalleSubastaRepo detalleSubastaRepo) {
        this.subastaRepo = subastaRepo;
        this.detalleSubastaRepo = detalleSubastaRepo;
    }

    @Override
    public Subasta crearSubasta(Subasta subasta) {
        return subastaRepo.save(subasta);
    }

    @Override
    public List<Producto> obtenerSubastas() {
        return subastaRepo.listarProductosSubasta();
    }

    @Override
    public Subasta obtenerSubastaProducto(String id) {
        return subastaRepo.obtenerSubastaProducto(Integer.parseInt(id));
    }

    @Override
    public void guardarDetalleSubasta(DetalleSubasta detalle){
        detalleSubastaRepo.save(detalle);
    }

}
