import random
from matplotlib import pyplot

game_graph = {
    1:[2, 3],
    2:[1, 4, 6],
    3:[1],
    4:[2, 5, 6],
    5:[4],
    6:[2, 4]
}

def sim_rand():

    protected_vertices = set()
    unprotected_vertices = {2, 3, 4, 5, 6}
    burning_vertices = {1}

    while (unprotected_vertices != set()):

        selected_vertex = random.sample(unprotected_vertices, 1)[0]
        unprotected_vertices.remove(selected_vertex)
        protected_vertices.add(selected_vertex)

        for i in burning_vertices.copy():
            for j in game_graph[i]:
                if j in unprotected_vertices:

                    burning_vertices.add(j)
                    unprotected_vertices.remove(j)

    return len(protected_vertices)

sims = [sim_rand() for i in range(1000)]

pyplot.hist(sims, bins=range(6), normed=True)
pyplot.xlabel('Number of Turns to win')
pyplot.ylabel('Fraction of games')
pyplot.title('Simulated Lengths of Chutes & Ladders Games')
