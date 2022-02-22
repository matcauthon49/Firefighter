import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;

public class Firefighter {
    int V;

    Graph game;
    int time;

    boolean[] safe;
    boolean[] burning;

    Queue<Integer> currently_burning;

    Firefighter(int V) {
        this.V = V;
        init();
    }

    Firefighter() {
        this.V = (int)(Math.random() * 900) + 100;
        //this.V = (int)(Math.random() * 50) + 50;
        init();
    }

    void init() {
        // int E = (int)(Math.random() * (V * (V-1) / 2 - V + 1)) + V - 1;
        // int E = (int)(Math.random() * V) + V - 1;
        int E = V - 1;

        game = new Graph(V, E);
    }

    void reset_game() {
        time = 0;

        safe = new boolean[V];
        burning = new boolean[V];

        currently_burning = new LinkedList<Integer>();
        
        burning[0] = true;
        currently_burning.add(0);

        //System.err.println(this);
        //System.err.println(this.game);
    }

    void simulate_turn(int safe_node_choice) {
        safe[safe_node_choice] = true;

        Queue<Integer> next_burning = new LinkedList<Integer>();

        for (int cnt_node : currently_burning) {
            for (int neighbour : game.adjacency_list[cnt_node]) {
                if (!safe[neighbour] && !burning[neighbour]) {
                    next_burning.add(neighbour);
                    burning[neighbour] = true;
                }
            }
        }

        currently_burning = next_burning;
    }

    int get_random_safe_node_choice() {
        int choice = 0;
        
        do {
            choice = (int)(Math.random() * V);
        } while (safe[choice] || burning[choice]);
        
        return choice;
    }

    boolean game_over() {
        int unplayable_nodes = 0;

        for (int i = 0; i < V; i++) {
            unplayable_nodes += (safe[i] || burning[i]) ? 1 : 0;
        }

        return unplayable_nodes == V;
    }

    void play_game() {
        reset_game();
        while (!game_over()) {
            //System.err.println(this);
            time += 1;
            simulate_turn(get_random_safe_node_choice());
        }
        //System.err.println(this);
    }

    int safe_node_count() {
        int count = 0;
        for (boolean safe_cnt : safe) {
            count += safe_cnt ? 1 : 0;
        }
        return count;
    }

    @Override
    public String toString() {
        String game_info = "";
        game_info += "time = " + time + "\n";

        game_info += "safe nodes = [";
        for (int i = 0; i < V; i++) {
            if (safe[i]) game_info += i + ", ";
        }
        game_info += "]\n";

        game_info += "burning nodes = [";
        for (int i = 0; i < V; i++) {
            if (burning[i]) game_info += i + ", ";
        }
        game_info += "]\n";

        game_info += "currently burning = {";
        for (int node : currently_burning) {
            game_info += node + ", ";
        }
        game_info += "}\n";

        return game_info;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        Firefighter game_instance = new Firefighter();
        // System.err.println(game_instance.game);
        System.err.println(game_instance.game.V + " " + game_instance.game.E);
        
        LinkedList<Integer> safe_nodes = new LinkedList<Integer>();
        int min_saved = Integer.MAX_VALUE;
        int max_saved = Integer.MIN_VALUE;

        int runs = 10000;
        for (int i = 0; i < runs; i++) {
            game_instance.play_game();

            int safe_nodes_cnt = game_instance.safe_node_count();
            safe_nodes.add(safe_nodes_cnt);
            min_saved = Math.min(min_saved, safe_nodes_cnt);
            max_saved = Math.max(max_saved, safe_nodes_cnt);

            // System.err.println("Result: (" + (System.currentTimeMillis() - time) + "ms, " + safe_nodes_cnt + " safe nodes, " + game_instance.time + "turns)");
            time = System.currentTimeMillis();
        }

        Collections.sort(safe_nodes);
        System.err.println("Median nodes saved = " + safe_nodes.get(safe_nodes.size() / 2));
        System.err.println("Max nodes saved = " + max_saved);
        System.err.println("Min nodes saved = " + min_saved);

    }
}

class Graph {
    int V;
    int E;
    LinkedList<Integer> adjacency_list[];

    Graph(int V, int E) {
        this.V = V;
        this.E = E;

        adjacency_list = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adjacency_list[i] = new LinkedList<Integer>();
        }

        generate_random_tree();
        add_random_edges(E - V + 1);
    }

    void generate_random_tree() {
        for (int i = 1; i < V; i++) {
            int parent = (int)(Math.random()*i);
            add_edge(i, parent);
        }
    }

    void add_random_edges(int edges) {
        for (int i = 0; i < edges; i++) {
            int V_1 = (int)(Math.random() * V);
            int V_2 = (int)(Math.random() * V);
            if (V_1 == V_2 || adjacency_list[V_1].contains(V_2)) { i--; continue; }
            add_edge(V_1, V_2);
        }
    }

    void add_edge(int V_1, int V_2) {
        adjacency_list[V_1].add(V_2);
        adjacency_list[V_2].add(V_1);
    }

    @Override
    public String toString() {
        String graph = "";
        graph += "V = " + V + ", E = " + E +"\n";

        for (int i = 0; i < V; i++) {
            graph += "Neighbours[" + i +"] = {";
            for (int neighbour : adjacency_list[i]) {
                graph += neighbour + ", ";
            }
            graph += "}\n";
        }

        return graph;
    }
}
