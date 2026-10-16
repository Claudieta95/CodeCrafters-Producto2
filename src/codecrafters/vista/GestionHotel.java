package codecrafters.vista;

import java.util.List;
import java.util.Scanner;

import codecrafters.controlador.Controlador;
import codecrafters.modelo.Cliente;
import codecrafters.modelo.Hotel;
import codecrafters.modelo.Habitacion;
import codecrafters.modelo.CapacidadInvalidaException;

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
                System.out.println("8. Salir");
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
                                System.out.println(cliente);
                            }
                        }
                    }

                    case 3 -> {
                        System.out.print("Nombre o apellido: ");
                        String texto = scanner.nextLine();

                        List<Cliente> clientes =
                                controlador.buscarClientes(texto);

                        if (clientes.isEmpty()) {
                            System.out.println("No se encontraron clientes.");
                        } else {
                            for (Cliente cliente : clientes) {
                                System.out.println(cliente);
                            }
                        }
                    }

                    case 4 -> {
                        List<Hotel> hoteles = controlador.listarHoteles();

                        if (hoteles.isEmpty()) {
                            System.out.println("No hay hoteles registrados.");
                        } else {
                            for (Hotel hotel : hoteles) {
                                System.out.println(hotel);
                            }
                        }
                    }

                    case 5 -> {
                        System.out.print("Ciudad: ");
                        String ciudad = scanner.nextLine();

                        List<Hotel> hoteles =
                                controlador.buscarHotelesPorCiudad(ciudad);

                        if (hoteles.isEmpty()) {
                            System.out.println("No se encontraron hoteles.");
                        } else {
                            for (Hotel hotel : hoteles) {
                                System.out.println(hotel);
                            }
                        }
                    }

                    case 6 -> {
                        Hotel hotel = seleccionarHotel(scanner, controlador);

                        if (hotel != null) {
                            List<Habitacion> habitaciones =
                                    controlador.listarHabitacionesPorHotel(hotel);

                            if (habitaciones.isEmpty()) {
                                System.out.println(
                                        "El hotel no tiene habitaciones.");
                            } else {
                                for (Habitacion habitacion : habitaciones) {
                                    System.out.println(habitacion);
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
                                        System.out.println(
                                                "No hay habitaciones con esa capacidad.");
                                    } else {
                                        for (Habitacion habitacion : habitaciones) {
                                            System.out.println(habitacion);
                                        }
                                    }
                                } catch (CapacidadInvalidaException e) {
                                    System.out.println(
                                            "Error: " + e.getMessage());
                                }

                            } else {
                                System.out.println("Introduce un número válido.");
                                scanner.nextLine();
                            }
                        }
                    }

                    case 8 -> System.out.println("Saliendo del programa.");

                    default -> System.out.println("Opción no válida.");
                }

            } while (opcion != 8);
        }
    }

    private static Hotel seleccionarHotel(
            Scanner scanner, Controlador controlador) {

        List<Hotel> hoteles = controlador.listarHoteles();

        if (hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados.");
            return null;
        }

        for (int i = 0; i < hoteles.size(); i++) {
            System.out.println((i + 1) + ". " + hoteles.get(i));
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
}
