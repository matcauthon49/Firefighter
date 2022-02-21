from game_parameters import fgraph
from game_parameters import play_firefighter

import csv
import random

results = []
game = {}

reader = csv.reader(open('tree.csv', 'r'))
next(reader, None)

for row in reader:
   k, v = row
   w = list(map(int, (v.strip("[]")).split(", ")))
   game[int(k)] = w

game_graph = fgraph(game)

for i in range(2):
    x = play_firefighter(game_graph)
    results.append(x[2])

print(results)