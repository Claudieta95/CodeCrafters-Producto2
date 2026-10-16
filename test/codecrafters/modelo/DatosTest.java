package codecrafters.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DatosTest {

    @Test
    void comprobarFiltradoHabitacionesPorCapacidad() {
        Datos datos = new Datos();
        Hotel hotel = datos.listarHoteles().get(0);

        List<Habitacion> habitaciones =
                datos.filtrarHabitacionesPorCapacidad(hotel, 3);

        assertEquals(1, habitaciones.size());
        assertEquals(103, habitaciones.get(0).getNumero());

        assertThrows(
                CapacidadInvalidaException.class,
                () -> datos.filtrarHabitacionesPorCapacidad(hotel, 0)
        );
    }
}
