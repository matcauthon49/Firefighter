class graph:

    def __init__(self, adjacency_dict):
        self.length = len(adjacency_dict)
        self.adjacency_list = adjacency_dict
        self.on_fire = {1}
        self.currently_burning = {1}
        self.protected = set()

    def protect(self,i):
        self.protected.add(i)

    def burn(self):
        new_burning = set()
        
        while (self.currently_burning != set()):
            burning_node = self.currently_burning.pop()

            for i in self.adjacency_list[burning_node]:
                if (i not in self.protected) and (i not in self.on_fire):
                    new_burning.add(i)
                    self.on_fire.add(i)

        self.currently_burning = new_burning

    def return_protected(self):
        return self.protected

    def return_on_fire(self):
        return self.on_fire
