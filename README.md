# Firefighter
Firefighter simulates the graph game Firefighter, introduced by Bert Hartnell in 1995.

## Rules of Firefighter

1. Begin with a graph. `time = 0`.
2. A single vertex on the graph is burning.
3. The player gets to choose a vertex to protect. This vertex cannot catch fire.
4. Every vertex adjacent to the burning vertices catches fire, unless it is protected. `time += 1`.
5. The game ends when every vertex is either burning or protected.

## Possible Objectives

1. Minimize the time taken to end the game.
2. Maximize the number of safe vertices.
3. Some kind of points system, ie. `points = burning_vertices + time`. Minimize `points`.
