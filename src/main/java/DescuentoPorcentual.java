import lombok.Data;
import lombok.NonNull;

@Data
public class DescuentoPorcentual extends Descuento{
    @NonNull
    private String tipo;

    public DescuentoPorcentual(double cantidad, double tope) {
        super();
        this.tipo = "%";
        if (cantidad >=0 && cantidad < 1) super.cantidad = cantidad;
        else throw new ArithmeticException("La cantidad debe estar entre 0 y 1");
        if (tope >0) super.tope = tope;
        else throw new ArithmeticException("El tope debe ser mayor a cero");
    }
}
