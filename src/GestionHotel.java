import java.util.ArrayList;
import java.util.Scanner;

public class GestionHotel {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ArrayList<Persona> personas = new ArrayList<>();
            int opcion = 0;
            
            // Bucle para el menú interactivo
            do {
                System.out.println("\n--- GESTIÓN DE HOTEL ---");
                System.out.println("1. Registrar nuevo cliente");
                System.out.println("2. Mostrar listado de personas");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");

                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcion) {
                        case 1 -> {
                            // ID generado automáticamente
                            int id = personas.size() + 1;
                            System.out.println("ID asignado automáticamente: " + id);

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

                            // Instancia de cliente y añadir a la lista de personas
                            Persona nuevoCliente = new Cliente(id, nombre, apellido, dni, email, telefono);
                            personas.add(nuevoCliente);
                            System.out.println("¡Cliente registrado con éxito!");
                        }

                        case 2 -> {
                            System.out.println("\n--- LISTA DE PERSONAS EN EL HOTEL ---");
                            if (personas.isEmpty()) {
                                System.out.println("No hay personas registradas todavía.");
                            } else {
                                for (Persona p : personas) {
                                    System.out.println(p.toString());
                                }
                            }
                        }

                        case 3 -> System.out.println("Saliendo del sistema de gestión... Hasta pronto!");

                        default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                    }
                } else {
                    System.out.println("Por favor, introduce un número válido.");
                    scanner.next(); // Limpiar entrada incorrecta
                }

            } while (opcion != 3);
        }
    }
}