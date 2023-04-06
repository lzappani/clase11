import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

@Data
public class Producto {
    @NonNull
    private String nombre;
    @NonNull
    private Peso peso;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @NonNull
    private double precio;
}

@JsonFormat(shape=JsonFormat.Shape.ARRAY)
@Data
class Peso {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @NonNull
    private double magnitud;
    @NonNull
    private String unidad;
}