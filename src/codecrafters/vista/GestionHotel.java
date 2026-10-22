package codecrafters.vista;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.CapacidadInvalidaException;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.LineaReserva;
import codecrafters.modelo.Reserva;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GestionHotel {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            Controlador controlador = new Controlador();
            int opcion;

            do {
                System.out.println("\n--- GESTIÓN DE HOTEL ---");
                System.out.println("1. Registrar nuevo cliente");
                System.out.println("2. Listar clientes");
                System.out.println("3. Buscar cliente por nombre o apellido");
                System.out.println("4. Listar hoteles");
                System.out.println("5. Buscar hoteles por ciudad");
                System.out.println("6. Listar habitaciones de un hotel");
                System.out.println("7. Filtrar habitaciones por número de personas");
                System.out.println("8. Reservar habitación");
                System.out.println("9. Listar reservas");
                System.out.println("10. Cancelar reserva");
                System.out.println("11. Salir");
                System.out.print("Elige una opción: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Introduce un número válido.");
                    scanner.nextLine();
                    opcion = 0;
                    continue;
                }

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {

                    case 1 -> {
                        int id = controlador.listarClientes().stream()
                                .mapToInt(Cliente::getIdPersona)
                                .max().orElse(0) + 1;

                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();

                        System.out.print("DNI: ");
                        String dni = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Teléfono: ");
                        String telefono = scanner.nextLine();

                        Cliente cliente = new Cliente(
                                id, nombre, apellido, dni, email, telefono);

                        controlador.agregarCliente(cliente);
                        System.out.println("Cliente registrado correctamente.");
                    }

                    case 2 -> {
                        List<Cliente> clientes = controlador.listarClientes();

                        if (clientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                        } else {
                            for (Cliente cliente : clientes) {
                                System.out.println(resumenCliente(cliente));
                            }
                        }
                    }

                    case 3 -> {
                        System.out.print("Nombre o apellido: ");
                        String texto = scanner.nextLine();

                        List<Cliente> clientes = controlador.buscarClientes(texto);

                        if (clientes.isEmpty()) {
                            System.out.println("No se encontraron clientes.");
                        } else {
                            for (Cliente cliente : clientes) {
                                System.out.println(resumenCliente(cliente));
                            }
                        }
                    }

                    case 4 -> {
                        List<Hotel> hoteles = controlador.listarHoteles();

                        if (hoteles.isEmpty()) {
                            System.out.println("No hay hoteles registrados.");
                        } else {
                            for (int i = 0; i < hoteles.size(); i++) {
                                System.out.println((i + 1) + ". " + resumenHotel(hoteles.get(i)));
                            }
                        }
                    }

                    case 5 -> {
                        System.out.print("Ciudad: ");
                        String ciudad = scanner.nextLine();

                        List<Hotel> hoteles = controlador.buscarHotelesPorCiudad(ciudad);

                        if (hoteles.isEmpty()) {
                            System.out.println("No se encontraron hoteles.");
                        } else {
                            for (int i = 0; i < hoteles.size(); i++) {
                                System.out.println((i + 1) + ". " + resumenHotel(hoteles.get(i)));
                            }
                        }
                    }

                    case 6 -> {
                        Hotel hotel = seleccionarHotel(scanner, controlador);

                        if (hotel != null) {
                            List<Habitacion> habitaciones =
                                    controlador.listarHabitacionesPorHotel(hotel);

                            if (habitaciones.isEmpty()) {
                                System.out.println("El hotel no tiene habitaciones.");
                            } else {
                                for (Habitacion habitacion : habitaciones) {
                                    System.out.println(resumenHabitacion(habitacion));
                                }
                            }
                        }
                    }

                    case 7 -> {
                        Hotel hotel = seleccionarHotel(scanner, controlador);

                        if (hotel != null) {
                            System.out.print("Número de personas: ");

                            if (scanner.hasNextInt()) {
                                int personas = scanner.nextInt();
                                scanner.nextLine();

                                try {
                                    List<Habitacion> habitaciones =
                                            controlador.filtrarHabitacionesPorCapacidad(
                                                    hotel, personas);

                                    if (habitaciones.isEmpty()) {
                                        System.out.println("No hay habitaciones con esa capacidad.");
                                    } else {
                                        for (Habitacion habitacion : habitaciones) {
                                            System.out.println(resumenHabitacion(habitacion));
                                        }
                                    }
                                } catch (CapacidadInvalidaException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }

                            } else {
                                System.out.println("Introduce un número válido.");
                                scanner.nextLine();
                            }
                        }
                    }

                    case 8 -> reservarHabitacion(scanner, controlador);

                    case 9 -> {
                        List<Reserva> reservas = controlador.listarReservas();

                        if (reservas.isEmpty()) {
                            System.out.println("No hay reservas registradas.");
                        } else {
                            for (Reserva reserva : reservas) {
                                System.out.println(resumenReserva(reserva));
                            }
                        }
                    }

                    case 10 -> {
                        System.out.print("Introduce el ID de la reserva a cancelar: ");

                        if (scanner.hasNextInt()) {
                            int idReserva = scanner.nextInt();
                            scanner.nextLine();

                            if (controlador.cancelarReserva(idReserva)) {
                                System.out.println("Reserva cancelada con éxito.");
                            } else {
                                System.out.println("No se encontró ninguna reserva con ese ID.");
                            }
                        } else {
                            System.out.println("ID no válido.");
                            scanner.nextLine();
                        }
                    }

                    case 11 -> System.out.println("Saliendo del programa.");

                    default -> System.out.println("Opción no válida.");
                }

            } while (opcion != 11);
        }
    }

    private static String resumenCliente(Cliente cliente) {
        return cliente.getIdPersona() + ". " + cliente.getNombre() + " "
                + cliente.getApellido()
                + " | DNI: " + cliente.getDni()
                + " | " + cliente.getEmail()
                + " | Tel: " + cliente.getTelefono();
    }

    private static String resumenHotel(Hotel hotel) {
        return hotel.getNombre() + " (" + hotel.getCiudad() + ", "
                + hotel.getEstrellas() + " estrellas)";
    }

    private static String resumenHabitacion(Habitacion habitacion) {
        return "ID " + habitacion.getIdHabitacion()
                + " | Hab. " + habitacion.getNumero()
                + " | " + habitacion.getCategoria()
                + " | Capacidad: " + habitacion.getCapacidad()
                + " | " + String.format("%.2f", habitacion.getPrecioNoche()) + " €/noche";
    }

    private static String resumenReserva(Reserva reserva) {
        StringBuilder habitaciones = new StringBuilder();

        for (LineaReserva linea : reserva.getLineasReserva()) {
            if (habitaciones.length() > 0) {
                habitaciones.append(", ");
            }
            habitaciones.append(linea.getHabitacion().getNumero());
        }

        String cliente = reserva.getCliente() != null
                ? reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido()
                : "N/A";

        return "Reserva " + reserva.getIdReserva()
                + " | " + cliente
                + " | " + reserva.getFechaEntrada() + " a " + reserva.getFechaSalida()
                + " | " + reserva.getNumeroPersonas() + " personas"
                + " | Hab: " + habitaciones
                + " | " + reserva.getEstado()
                + " | " + String.format("%.2f", reserva.getImporte()) + " €";
    }

    private static Hotel seleccionarHotel(Scanner scanner, Controlador controlador) {

        List<Hotel> hoteles = controlador.listarHoteles();

        if (hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados.");
            return null;
        }

        for (int i = 0; i < hoteles.size(); i++) {
            System.out.println((i + 1) + ". " + resumenHotel(hoteles.get(i)));
        }

        System.out.print("Selecciona el número del hotel: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Introduce un número válido.");
            scanner.nextLine();
            return null;
        }

        int numero = scanner.nextInt();
        scanner.nextLine();

        if (numero < 1 || numero > hoteles.size()) {
            System.out.println("Hotel no válido.");
            return null;
        }

        return hoteles.get(numero - 1);
    }

    private static void reservarHabitacion(Scanner scanner, Controlador controlador) {

        Hotel hotel = seleccionarHotel(scanner, controlador);
        if (hotel == null) {
            return;
        }

        List<Cliente> clientes = controlador.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Primero debes registrar un cliente.");
            return;
        }

        Cliente cliente = clientes.get(0);

        try {
            System.out.print("Fecha de entrada (AAAA-MM-DD): ");
            LocalDate entrada = LocalDate.parse(scanner.nextLine());
            System.out.print("Fecha de salida (AAAA-MM-DD): ");
            LocalDate salida = LocalDate.parse(scanner.nextLine());

            System.out.print("Número de personas: ");
            int numPersonas = scanner.nextInt();
            scanner.nextLine();

            List<Habitacion> disponibles = controlador.buscarHabitacionesDisponibles(
                    hotel, entrada, salida, numPersonas);

            if (disponibles.isEmpty()) {
                System.out.println("No hay habitaciones disponibles para esas fechas y capacidad.");
                return;
            }

            System.out.println("Habitaciones disponibles:");
            for (Habitacion h : disponibles) {
                System.out.println(resumenHabitacion(h));
            }

            System.out.print("Selecciona el ID de la habitación que deseas reservar: ");
            int idHabitacion = scanner.nextInt();
            scanner.nextLine();

            Habitacion seleccionada = null;
            for (Habitacion h : disponibles) {
                if (h.getIdHabitacion() == idHabitacion) {
                    seleccionada = h;
                    break;
                }
            }

            if (seleccionada == null) {
                System.out.println("ID de habitación no válido o no disponible.");
                return;
            }

            int idReserva = controlador.listarReservas().size() + 1;
            Reserva nuevaReserva = new Reserva(idReserva, entrada, salida, numPersonas, "Activa");
            nuevaReserva.setCliente(cliente);
            nuevaReserva.agregarLineaReserva(seleccionada, seleccionada.getPrecioNoche());

            controlador.agregarReserva(nuevaReserva);

            System.out.println("¡Reserva creada con éxito! Importe total: "
                    + String.format("%.2f", nuevaReserva.getImporte()) + " €");

        } catch (Exception e) {
            System.out.println("Error al procesar la reserva: " + e.getMessage());
        }
    }
}