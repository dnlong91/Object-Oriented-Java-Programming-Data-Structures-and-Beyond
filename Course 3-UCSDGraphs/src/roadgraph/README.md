__Class:__ MapGraph

__Modifications made to MapGraph (what and why):__
- I added only 1 member variable to this class which is a HashMap called `vertices`. This HashMap maps each GeographicPoint object with an associating MapNode. 
- I completed all the required methods for this week with the help of a new class named `MapNode`.

__Class name:__ MapNode

__Purpose and description of class:__
- Define a MapNode object which is constituted of a GeographicPoint named `location` and a List of MapEdge objects called `edges`.
- The functionalities of this class include adding edges to the `edges` list, getting the number of edges, getting the neighbors node of a MapNode, and returning the GeographicPoint representing a MapNode.
- In order for this class to work, I had to create another class called `MapEdge`.

__Class name:__ MapEdge

__Purpose and description of class:__
- Define a MapEdge object which is constituted of 2 GeographicPoint objects namely `start` and `end` representing 2 ends of a directed edge, 2 String objects for `streetName` and `streetType`, and finally, a double for `distance`.
- Currently, this class does not have much. I have only created 2 getters for getting the `start` and `end` points of an edge.

__Overall Design Justification (4-6 sentences):__

- Overall, a `MapGraph` object is a set of all vertices. Each vertex is a `MapNode` which contains all `MapEdge` objects starting from it. I broke it down into 3 classes for easy handling and clear implementation of functionalities. Each class takes care of its own functionalities as described above so that I won't end up with a bunch of spaghetti codes.