import random
import pandas as pd

def random_tree_generator(v):

    adjacency_list = {
        i:[] for i in range(1,v+3) # tree will have v+2 nodes
    }
    prufer = [
        random.randint(1, v+1) for i in range(1, v+1)
    ]
    check_list = {
        i for i in range(1, v+3)
    }

    for i in range(v):

        v_1 = min(check_list.difference(prufer))
        v_2 = prufer[i]

        adjacency_list[v_1].append(v_2)
        adjacency_list[v_2].append(v_1)

        check_list.remove(v_1)
        prufer[i] = -1

    v_1 = check_list.pop()
    v_2 = check_list.pop()

    adjacency_list[v_1].append(v_2)
    adjacency_list[v_2].append(v_1)

    return(adjacency_list)

generated_tree = random_tree_generator(random.randint(100, 1000))

tree_series = pd.Series(generated_tree)
tree_series.to_csv("tree.csv")
