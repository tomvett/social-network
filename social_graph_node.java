public class social_graph_node {

    private String name;
    
    public social_graph_node(String nameIn) {
        name = nameIn;
    }
    
    public String getName() {
        return name;
    }
    
    //by default, this returns the string representation of the class
    //but now it should just return the name associated with this class
    @Override
    public String toString() {
        return name;
    }
}


