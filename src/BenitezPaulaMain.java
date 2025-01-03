import java.util.Scanner;


/** Clase principal que inicializa y ejecuta el juego de adivinar películas.
 * Esta clase gestiona la interacción inicial con el usuario, incluyendo la solicitud
 * del nickname, y muestra el ranking al final del juego.
 */
public class BenitezPaulaMain {
    public static void main(String[] args) {
        BenitezPaulaMain programa= new BenitezPaulaMain();
        programa.inicio();
    }
    public void inicio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("✨🎬 Bienvenido al juego de adivinar películas 🎬✨");
        System.out.print("Por favor, introduce tu nickname: ");
        String nickname = scanner.nextLine(); // Lee el nickname introducido

        // Crea las instancia del juego
        BenitezPaulaGame juego = new BenitezPaulaGame();

        // Inicia el juego
        juego.iniciarJuego(nickname);

        // Muestra el ranking al final del juego
        juego.mostrarRanking();

        scanner.close();

    }

}
