import java.io.*;
import java.util.*;

/** Esta Clase gestiona el juego de adivinar la película.
 */
public class BenitezPaulaGame {
    private static final int MAX_INTENTOS = 10;
    private ArrayList<String> peliculas;
    private ArrayList<Jugador> ranking;
    private String peliculaSeleccionada;
    private String peliculaOculta;
    private int intentosRestantes;
    private ArrayList<Character> letrasIncorrectas;
    private ArrayList<Character> letrasAdivinadas;
    private int puntuacion;

    /** Este es el constructor de la clase Game.
     * Inicializa las listas de películas y ranking,
     * carga las películas desde un archivo.
     */
    public BenitezPaulaGame() {
        peliculas = new ArrayList<>();
        ranking = new ArrayList<>();
        cargarPeliculas();
        cargarRankingDesdeArchivo();
    }

    /** Carga las películas desde el archivo "peliculas.txt".
     * Como metodo de gestion de errores el try catch excepción,
     * muestra una lista de películas Si no se puede leer el archivo.
     */
    private void cargarPeliculas() {
        try (BufferedReader br = new BufferedReader(new FileReader("peliculas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                peliculas.add(linea.trim());
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las películas: " + e.getMessage());
            peliculas.add("Toy Story");
            peliculas.add("Titanic");
            peliculas.add("Jurassic Park");
            peliculas.add("La La Land");
            peliculas.add("Casablanca");
            peliculas.add("Harry Potter");
            peliculas.add("El Señor de los Anillos");
            peliculas.add("Interstellar");
            peliculas.add("El Rey León");
            peliculas.add("Gladiador");
            peliculas.add("El resplandor");
            peliculas.add("Alien");

        }
    }
    /** Este metodo selecciona una película al azar de la lista
     * y genera la versión oculta con asteriscos.
     */
    private void seleccionarPeliculaAleatoria() {
        Random random = new Random();
        peliculaSeleccionada = peliculas.get(random.nextInt(peliculas.size())).toLowerCase();
        ocultarPelicula();
    }

    /** metodo que genera la representación oculta de la película seleccionada,
     * reemplazando las letras por asteriscos mientras conserva espacios y puntuación.
     */
    private void ocultarPelicula() {
        StringBuilder oculta = new StringBuilder();
        for (char c : peliculaSeleccionada.toCharArray()) {
            if (Character.isLetter(c)) {
                oculta.append("*");
            } else {
                oculta.append(c);
            }
        }
        peliculaOculta = oculta.toString();
    }

    /**
     * Espe metodo inicia el juego con un nickname proporcionado por el usuario.
     * su parametro de entrada.
     * @param nickname el nombre del jugador
     */
    public void iniciarJuego(String nickname) {
        Scanner scanner = new Scanner(System.in);
        seleccionarPeliculaAleatoria();
        intentosRestantes = MAX_INTENTOS;
        letrasIncorrectas = new ArrayList<>();
        letrasAdivinadas = new ArrayList<>();
        puntuacion = 0;

        System.out.println("\nLa película tiene " + peliculaSeleccionada.length() + " caracteres (incluyendo espacios y puntuación).");

        /* Muestra el estado actual del juego, incluyendo la película oculta,
         * letras incorrectas, intentos restantes y puntuación.
         */
        while (intentosRestantes > 0 && peliculaOculta.contains("*")) {
            System.out.println("\nProgreso: " + peliculaOculta);
            System.out.println("Letras incorrectas: " + letrasIncorrectas);
            System.out.println("Intentos restantes: " + intentosRestantes);
            System.out.println("Puntuación: " + puntuacion);
            System.out.println("[1] Adivinar una letra");
            System.out.println("[2] Adivinar el título completo");
            System.out.println("[3] Salir");
            System.out.print("Elige una opción: ");

            int opcion = -1;
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // elimina salto de línea
            } else {
                scanner.nextLine(); //consume texto no válido
            }

            switch (opcion) {
                case 1:
                    adivinarLetra(scanner);
                    break;
                case 2:
                    if (adivinarTitulo(scanner, nickname)) {
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Has salido del juego.");
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }

        if (!peliculaOculta.contains("*")) {
            System.out.println("\n🎉 ¡Felicidades! Has adivinado la película: " + peliculaSeleccionada);
            guardarEnRanking(nickname);
        } else {
            System.out.println("\n😢 Te has quedado sin intentos. La película era: " + peliculaSeleccionada);
        }
        System.out.println("Tu puntuación final es: " + puntuacion);
        guardarRankingEnArchivo();
    }
    /** Permite al jugador adivinar una letra.
     * Actualiza el estado del juego según si la letra está o no en la película.
     * parametro de entrada:
     * @param scanner objeto Scanner para leer la entrada del usuario
     */
    private void adivinarLetra(Scanner scanner) {
        System.out.print("Ingresa una letra: ");
        String input = scanner.nextLine().toLowerCase();

        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            System.out.println("Entrada no válida. Ingresa una sola letra.");
            return;
        }

        char letra = input.charAt(0);
        if (letrasAdivinadas.contains(letra) || letrasIncorrectas.contains(letra)) {
            System.out.println("Ya has intentado esa letra. Prueba con otra.");
            return;
        }

        if (peliculaSeleccionada.contains(String.valueOf(letra))) {
            letrasAdivinadas.add(letra);
            actualizarPeliculaOculta(letra);
            puntuacion += 10;
            System.out.println("¡Bien hecho! La letra '" + letra + "' está en el título.");
        } else {
            letrasIncorrectas.add(letra);
            puntuacion -= 10;
            intentosRestantes--;
            System.out.println("Lo siento, la letra '" + letra + "' no está en el título.");
        }
    }

    private boolean adivinarTitulo(Scanner scanner, String nickname) {
        System.out.print("Ingresa el título de la película: ");
        String titulo = scanner.nextLine().toLowerCase();

        if (titulo.equals(peliculaSeleccionada)) {
            puntuacion += 20;
            System.out.println("\n🎉 ¡Correcto! Has adivinado la película: " + peliculaSeleccionada);
            guardarEnRanking(nickname);
            guardarRankingEnArchivo();
            return true;
        } else {
            puntuacion -= 20;
            System.out.println("\n😢 Incorrecto. La película era: " + peliculaSeleccionada);
            return true;
        }
    }
    /**Este metodo actualiza la representación oculta de la película al descubrir una letra.
     *su parametro de entrada es:
     * @param letra la letra adivinada por el jugador
     */
    private void actualizarPeliculaOculta(char letra) {
        StringBuilder actualizado = new StringBuilder();
        for (int i = 0; i < peliculaSeleccionada.length(); i++) {
            char c = peliculaSeleccionada.charAt(i);
            if (c == letra || peliculaOculta.charAt(i) != '*') {
                actualizado.append(c);
            } else {
                actualizado.append('*');
            }
        }
        peliculaOculta = actualizado.toString();
    }
    /** Guarda al jugador en el ranking si su puntuación es suficiente y el nickname no es repetido.
     * parametro de entrada:
     * @param nickname el nombre del jugador
     */
    private void guardarEnRanking(String nickname) {
        // Valida que el nickname no sea repetido
        while (esNicknameRepetido(nickname)) {
            System.out.println("El nickname '" + nickname + "' ya está en el ranking. Por favor, introduce otro:");
            Scanner scanner = new Scanner(System.in);
            nickname = scanner.nextLine().trim();
        }
        // Si el ranking ya tiene 5 jugadores, verificar si la puntuación supera la última posición
        if (ranking.size() == 5 && puntuacion <= ranking.get(ranking.size() - 1).getPuntuacion()) {
            System.out.println("\n😞 Tu puntuación no es suficiente para entrar en el ranking.");
            return; // Sale del método
        }

        // Añade al jugador al ranking
        ranking.add(new Jugador(nickname, puntuacion));
        Collections.sort(ranking, (j1, j2) -> j2.getPuntuacion() - j1.getPuntuacion());
        if (ranking.size() > 5) {
            ranking.remove(ranking.size() - 1);
        }
    }

    /**Verifica si un nickname ya existe en el ranking.
     *
     * @param nickname el nombre del jugador que se desea comprobar
     * @return true si el nickname ya está en el ranking, false en caso contrario
     */
    private boolean esNicknameRepetido(String nickname) {
        // Recorre la lista de jugadores en el ranking
        for (Jugador jugador : ranking) {
            // Compara el nickname ingresado con los existentes, ignorando mayúsculas y minúsculas
            if (jugador.getNickname().equalsIgnoreCase(nickname)) {
                return true; // Devuelve true si encuentra un nickname repetido
            }
        }
        return false; // Devuelve false si no hay coincidencias en el ranking
    }


    private void guardarRankingEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ranking.txt"))) {
            for (Jugador jugador : ranking) {
                writer.write(jugador.getNickname() + "," + jugador.getPuntuacion());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el ranking: " + e.getMessage());
        }
    }

    private void cargarRankingDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ranking.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    ranking.add(new Jugador(partes[0], Integer.parseInt(partes[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró el archivo de ranking. Se iniciará un ranking nuevo.");
        }
    }

    public void mostrarRanking() {
        System.out.println("\n🎖️ Ranking:");
        for (int i = 0; i < ranking.size(); i++) {
            Jugador jugador = ranking.get(i);
            System.out.println((i + 1) + ". " + jugador.getNickname() + " - " + jugador.getPuntuacion() + " puntos");
        }
    }
    /** En esta Clase gestionamos los jugadores.
     */
    private class Jugador {
        private String nickname;
        private int puntuacion;

        /** Este constructor de la clase Jugador.
         * tiene como parametros:
         * @param nickname  el nombre del jugador
         * @param puntuacion la puntuación obtenida por el jugador
         */
        public Jugador(String nickname, int puntuacion) {
            this.nickname = nickname;
            this.puntuacion = puntuacion;
        } // Asigna la puntuación obtenida al atributo de la clase

        public String getNickname() {
            return nickname;
        }

        public int getPuntuacion() {
            return puntuacion;
        }
    }
}
