# ¡Juego Guess the movie! ✨🎥
(Adivina la pelicula)

Este proyecto es un juego interactivo en consola donde los jugadores intentan adivinar el título de una película letra por letra o el título completo. 
Con puntuación y ranking, ¡es perfecto para los amantes del cine!

## Características Principales
- **Adivinación de películas**: Puedes adivinar letra por letra o intentar adivinar el título completo.
- **Sistema de puntuación**: Gana o pierde puntos según tus aciertos y errores.
- **Ranking**: Guarda y muestra los mejores jugadores con sus puntuaciones.
- **Películas personalizables**: Puedes editar el archivo `peliculas.txt` para agregar tus propias películas.

## Tecnologías Utilizadas
- **Lenguaje**: Java
- **Entrada/Salida de archivos/ ficheros**: Para cargar y guardar películas y rankings.
- **Estructuras de datos**: Listas y colecciones para gestionar palabras, intentos y el ranking.

## Instrucciones de Uso
1. **Inicio del juego**:
   - Al iniciar, introduce un nickname para comenzar a jugar.
2. **Opciones durante el juego**:
   - Adivina letras o el título completo.
   - Si adivinas correctamente, ganarás puntos; si no, los perderás.
   - Puedes salir del juego en cualquier momento seleccionando la opción salir.
3. **Final del juego**:
   - Si logras adivinar, se mostrará tu puntuación y podrás ingresar al ranking si es suficientemente alta.

## Personalización
- **Agregar nuevas películas**: Edita el archivo `peliculas.txt` y agrega títulos, cada uno en una nueva línea.
- **Modificar el ranking**: El archivo `ranking.txt` guarda los jugadores y sus puntuaciones en formato `nickname,puntuacion`.

## Ejemplo de Juego
```
✨🎥 Bienvenido al juego de adivinar películas 🎥✨
Por favor, introduce tu nickname: CineFan123

La película tiene 10 caracteres (incluyendo espacios y puntuación).
Progreso: **********
Letras incorrectas: []
Intentos restantes: 10
Puntuación: 0

[1] Adivinar una letra
[2] Adivinar el título completo
[3] Salir
Elige una opción: 1

Ingresa una letra: t
¡Bien hecho! La letra 't' está en el título.
```

