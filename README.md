# Práctica 4 — Sabores en red

Código de arranque de la Práctica 4 de TC2007B.

Es la app de la Práctica 2 terminada: tres pantallas, navegación y ViewModel
compartido, con los datos todavía en memoria. Durante la práctica vas a cambiar
de dónde salen esos datos.

## Cómo empezar

1. Clona el repositorio y ábrelo en Android Studio.
2. Espera a que Gradle sincronice y corre la app: debes ver los cinco restaurantes.
3. Sigue la guía: https://startdroid.com/practicas/sabores-en-red.html

## Cómo trabajar

Haz un commit en cada checkpoint de la guía:

    git add -A ; git commit -m "checkpoint a1"

Si algo se rompe sin remedio, `git restore .` te regresa al último checkpoint bueno.

Los experimentos que rompen el código a propósito van en una rama:

    git switch -c experimento-c2     # antes de romper nada
    git switch main                  # el código bueno vuelve solo

## Uso de IA

Todo commit con código generado por IA debe declararlo con un trailer
`Co-Authored-By`. Ver la política completa en la guía.

## Entrega

Ver la rúbrica en la guía.
