# SuRo

Ein Minecraft-Plugin für ein Survival-Rollenspiel-Projekt (SuRo).

## Features

- Spielergruppenverwaltung (Mitspieler, Gamemaster, Spectator)
- Zeitgesteuerter Spielstart und Spielende mit Countdown
- Spezielle Events wie Acid Rain und Zwei-Spieler-Kampf
- Verwaltung von Spielerinventaren und Positionen beim Ein-/Austritt
- Verschiedene Admin-Kommandos (z.B. /start, /pause, /setup, /groups, /alert)
- Schutzmechanismen für Spielphasen (z.B. kein Schaden vor Spielstart)
- Live-Status für Spieler

## Installation

1. Kompiliere das Plugin mit Java 16.
2. Erstelle ein Minecraft Server (Beispielhaft für einen Lokalen Server: https://www.youtube.com/watch?v=EStA1Z3eBgs)
3. Kopiere die generierte JAR-Datei in den `plugins`-Ordner deines Spigot/Bukkit-Servers. (...\plugins)
4. Starte den Server neu.

## Konfiguration

- Die Konfiguration erfolgt über die automatisch generierte `config.yml`.
- Spieler und Ränge werden persistent gespeichert.

- Erste Nutzung: "/goupscmd <Username> gamemmaster" für die Konsole, alle weiteren Commands können ingame ausgeführt werden.

## Kommandos

| Befehl                       | Beschreibung                                       |
|------------------------------|----------------------------------------------------|
| `/start`                     | Startet den Spiel-Countdown                        |
| `/pause`                     | Pausiert oder setzt das Spiel fort                 |
| `/setup <n>`                 | Setzt Startposition für Spieler n                  |
| `/groups ...`                | Verwalte Spielergruppen und Ränge                  |
| `/alert <msg>`               | Sende Nachricht an alle Spieler                    |
| `/vanish`                    | Unsichtbarkeit für Gamemaster                      |
| `/spec <Spieler>`            | Teleportiere zu einem Spieler als Spectator        |
| `/disq <Spieler> <Grund>`    | Disqualifiziert einen Spieler                      |
| ...                          | Weitere Kommandos siehe plugin.yml (kommt später)  |

## Entwickler

- Autor: denizlp

## Hinweise

- Das Plugin ist für Minecraft 1.21.4 (Spigot/Bukkit) entwickelt.
- Einige Funktionen sind speziell auf das SuRo-Projekt zugeschnitten.
- Leiten kann dies nur ein "Gamemaster". Diese Rolle kann durch einen Command in der Konsole oder über die config.yml einem bzw. mehreren Spielern zugewiesen werden.
- Als Spec(tator) kann man nur zuschauen, als "Default" spielen. Jeder Spieler ohne Rolle wird sofort vom Server gekickt.

- **Dies ist nicht die fertige Version. Commands wurden auf Wunsch einiger YouTuber/Streamer hinzugefügt, werden jedoch aufgrund des Zufallprinzips (AcidRain, 2SpielerKampf)
  vermutlich verworfen. Einige Funktionen sind auch noch nicht Betriebsbereit, werden überarbeitet oder gar neu hinzugefügt.**

## Lizenz

Dieses Projekt ist für die öffentliche Betrachtung und Nutzung freigegeben.
