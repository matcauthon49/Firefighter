import random

class fgraph:

    def __init__(self, adjacency_dict):
        self.length = len(adjacency_dict)
        self.adjacency_list = adjacency_dict
        self.on_fire = {1}
        self.currently_burning = {1}
        self.protected = set()
        self.playable = self.length - 1

    def protect(self,i):
        self.protected.add(i)
        self.playable -= 1

    def burn(self):
        new_burning = set()
        
        while (self.currently_burning != set()):
            burning_node = self.currently_burning.pop()

            for i in self.adjacency_list[burning_node]:
                if (i not in self.protected) and (i not in self.on_fire):
                    new_burning.add(i)
                    self.on_fire.add(i)
                    self.playable -= 1

        self.currently_burning = new_burning

    def protect_random_vertex(self):
        unprotected = ({i for i in range(1, self.length+1)} - self.on_fire)-self.protected
        
        if (unprotected != set()):
            vertex_to_protect = random.choice(list(unprotected))
            self.protect(vertex_to_protect)

    def return_protected(self):
        return self.protected

    def return_on_fire(self):
        return self.on_fire
    
    def reset(self):
        self.on_fire = {1}
        self.currently_burning = {1}
        self.protected = set()
        self.playable = self.length - 1


def play_firefighter(game_fgraph):

    while (game_fgraph.playable != 0):
        game_fgraph.protect_random_vertex()
        game_fgraph.burn()

    return (
        game_fgraph.protected, 
        game_fgraph.on_fire, 
        len(game_fgraph.protected), 
        len(game_fgraph.on_fire)
    )
