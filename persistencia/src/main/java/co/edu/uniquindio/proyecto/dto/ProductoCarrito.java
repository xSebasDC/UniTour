package co.edu.uniquindio.proyecto.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ProductoCarrito {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String nombre, imagen;
    private Integer unidades;
    private Float precio;
    private Float descuento;
    private Float precioFinal;

    public Float getPrecioFinal(){
        System.out.println("i:"+precio);
        if (descuento>0){
            Float resta= (descuento/100)* precio;
            System.out.println(precio+ "-" + resta);
            return precio-resta;
        }
        System.out.println("ab" + precio);
        return precio;
    }

    public ProductoCarrito(Integer codigo, String nombre, String imagen, Integer unidades, Float precio, Float descuento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.unidades = unidades;
        this.precio = precio;
        this.descuento = descuento;
        precioFinal = getPrecioFinal();
    }
}
