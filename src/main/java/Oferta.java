import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class Oferta {
    @NonNull
    private Producto producto;
    @NonNull
    private Descuento descuento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NonNull
    private Date fechaDesde;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NonNull
    private Date fechaHasta;

    public Oferta(@NonNull Producto producto, @NonNull Descuento descuento, String fechaDesde, String fechaHasta) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.producto = producto;
        this.descuento = descuento;
        this.fechaDesde = format.parse(fechaDesde);
        this.fechaHasta = format.parse(fechaHasta);
    }
}
