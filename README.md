# Simulación de fuego

Demo Java Swing que representa un fuego mediante una matriz de temperaturas.
Cada fotograma se calcula a partir del anterior y se muestra como una imagen
con una paleta de colores.

## Requisitos

- JDK 8 o posterior
- Entorno de escritorio compatible con Swing

El proyecto no necesita Maven ni Gradle.

## Compilar y ejecutar

Desde la carpeta del proyecto:

```sh
mkdir -p build
javac -d build $(find src -name '*.java')
java -cp build Main
```

La ventana inicia la simulación automáticamente.

## Cómo se calcula el fuego

`Fire` guarda una matriz de temperaturas entre 0 y 255. Al avanzar un
fotograma:

1. Se pone a cero la fila inferior.
2. Se crean puntos fríos y chispas aleatorias en esa fila. Las chispas tienen
   temperatura 255.
3. Se calcula cada fila de abajo hacia arriba. Una celda recibe el promedio de
   las tres celdas que tiene debajo. Si una de esas celdas queda fuera de la
   matriz, cuenta como 0; por eso el promedio siempre se divide entre 3.
4. El resultado se guarda en una matriz nueva, que pasa a ser el fotograma
   actual.

Las probabilidades iniciales de puntos fríos y chispas son 20% cada una.

## Colores y renderizado

`ColorTargetsDTO` define los colores de referencia de la paleta:

| Temperatura | Color | RGB |
|---:|---|---|
| 0 | Negro transparente | (0, 0, 0) |
| 25 | Marrón | (139, 69, 19) |
| 50 | Rojo | (255, 0, 0) |
| 100 | Naranja | (255, 165, 0) |
| 200 | Amarillo | (255, 255, 0) |
| 255 | Blanco | (255, 255, 255) |

`Palette` genera un color para cada temperatura entre 0 y 255 interpolando
linealmente los canales RGBA entre los colores de referencia. El color de
temperatura 0 es transparente para que el fondo no sea negro opaco.

`Render` convierte la matriz en un `BufferedImage` y lo dibuja en un `Canvas`.

## Organización del código

- `Main`: crea y conecta el modelo, el presentador y la ventana.
- `Model.Fire`: crea la matriz y calcula cada fotograma.
- `Model.ColorTargetsDTO`: almacena los puntos de color de referencia.
- `Model.Palette`: genera los colores intermedios.
- `Model.Render`: transforma temperaturas en píxeles y los pinta.
- `Model.MyModel`: coordina fuego, paleta, renderizado y temporizador.
- `Model.IModel`: define las operaciones del modelo disponibles al presentador.
- `Presenter.MyPresenter`: conecta el modelo con la vista.
- `Presenter.IPresenter`: define las acciones de presentación.
- `View.MyView`: crea la ventana que contiene el visor.
- `View.ViewerPanel`: aloja el lienzo de renderizado.
- `View.IView`: define las notificaciones que recibe la vista.

El temporizador de Swing avanza la simulación cada 50 ms. El modelo actualiza
la imagen y el renderizador solicita que Swing vuelva a dibujar el lienzo.
