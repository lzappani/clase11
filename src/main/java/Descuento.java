import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public abstract class Descuento {
    double cantidad;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    double tope;
}
