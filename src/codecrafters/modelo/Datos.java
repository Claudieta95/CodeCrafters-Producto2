package codecrafters.modelo;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Datos {

    private List<Cliente> clientes;
    private List<Hotel> hoteles;
    private List<Reserva> reservas;

    public Datos() {
        this.clientes = new ArrayList<>();
        this.hoteles = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    // Añadir un cliente
    public void agregarCliente(Cliente cliente) {
        if (cliente != null) {
            clientes.add(cliente);
        }
    }

    // Listar todos los clientes
    public List<Cliente> listarClientes() {
        return clientes;
    }

    // Buscar clientes por nombre o apellido
    public List<Cliente> buscarClientes(String texto) {
        List<Cliente> resultados = new ArrayList<>();

        if (texto == null) {
            return resultados;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(texto.toLowerCase())
                    || cliente.getApellido().toLowerCase().contains(texto.toLowerCase())) {
                resultados.add(cliente);
            }
        }

        return resultados;
    }

    
    // Añadir un hotel
    public void agregarHotel(Hotel hotel) {
        if (hotel != null) {
            hoteles.add(hotel);
        }
    }

    // Listar todos los hoteles
    public List<Hotel> listarHoteles() {
        return hoteles;
    }

    // Buscar hoteles por ciudad
    public List<Hotel> buscarHotelesPorCiudad(String ciudad) {
        List<Hotel> resultados = new ArrayList<>();

        if (ciudad == null) {
            return resultados;
        }

        for (Hotel hotel : hoteles) {
            if (hotel.getCiudad().toLowerCase().contains(ciudad.toLowerCase())) {
                resultados.add(hotel);
            }
        }

        return resultados;
    }

    
    // Listar las habitaciones de un hotel
    public List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) {
        List<Habitacion> resultados = new ArrayList<>();

        if (hotel != null) {
            resultados.addAll(hotel.getHabitaciones());
        }

        return resultados;
    }

    // Buscar habitaciones disponibles por fechas y capacidad
    public List<Habitacion> buscarHabitacionesDisponibles(
            Hotel hotel, LocalDate fechaEntrada, LocalDate fechaSalida,
            int numeroPersonas) {

        List<Habitacion> resultados = new ArrayList<>();

        if (hotel == null || fechaEntrada == null || fechaSalida == null
                || !fechaEntrada.isBefore(fechaSalida)) {
            return resultados;
        }

        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getCapacidad() >= numeroPersonas
                    && habitacion.estaDisponible(fechaEntrada, fechaSalida)) {
                resultados.add(habitacion);
            }
        }

        return resultados;
    }

    
    // Añadir una reserva
    public void agregarReserva(Reserva reserva) {
        if (reserva == null || !reserva.validarFechas()) {
            throw new IllegalArgumentException("La reserva no tiene fechas válidas.");
        }

        if (reserva.getLineasReserva().isEmpty()) {
            throw new IllegalArgumentException("La reserva debe tener al menos una habitación.");
        }

        if (!reservas.contains(reserva)) {
            reservas.add(reserva);
            reserva.calcularImporte();
        }
    }

    // Listar todas las reservas
    public List<Reserva> listarReservas() {
        return reservas;
    }

    // Buscar reservas de un cliente
    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        List<Reserva> resultados = new ArrayList<>();

        for (Reserva reserva : reservas) {
            if (reserva.getCliente() == cliente) {
                resultados.add(reserva);
            }
        }

        return resultados;
    }

    // Buscar reservas de un hotel
    public List<Reserva> buscarReservasPorHotel(Hotel hotel) {
        List<Reserva> resultados = new ArrayList<>();

        for (Reserva reserva : reservas) {
            for (LineaReserva linea : reserva.getLineasReserva()) {
                if (linea.getHabitacion().getHotel() == hotel) {
                    resultados.add(reserva);
                    break;
                }
            }
        }

        return resultados;
    }

    // Cancelar una reserva por su identificador
    public boolean cancelarReserva(int idReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == idReserva) {
                reserva.cancelar();
                return true;
            }
        }

        return false;
    }

}
