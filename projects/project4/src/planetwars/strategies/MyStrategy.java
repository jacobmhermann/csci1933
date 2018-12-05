/*
    By Jacob Hermann, herma655@umn.edu

    Utilizes Lists to store information on particular visible planets throughout the system.
    Utilizes Queues to store a breadth first search of the planet's system.
    Utilizes Stacks to store potential attacks onto neighboring planets.

 */


package planetwars.strategies;

import planetwars.publicapi.*;
import java.util.*;

public class MyStrategy implements IStrategy {

    private int arraySize;

    public MyStrategy() { }


    /**
     * Method where students can observe the state of the system and schedule events to be executed.
     *
     * @param planets            The current state of the system.
     * @param planetOperations   Helper methods students can use to interact with the system.
     * @param eventsToExecute    Queue students will add to in order to schedule events.
     */
    @Override
    public void takeTurn(List<IPlanet> planets, IPlanetOperations planetOperations, Queue<IEvent> eventsToExecute) {

        arraySize = planets.size() + 1;

        // create lists of interest
        List<IVisiblePlanet> visiblePlanets = findVisiblePlanets(planets);
        List<IVisiblePlanet> myPlanets = createList(visiblePlanets, Owner.SELF);
        List<IVisiblePlanet> neutralVisiblePlanets = createList(visiblePlanets, Owner.NEUTRAL);
        List<IVisiblePlanet> theirVisiblePlanets = createList(visiblePlanets, Owner.OPPONENT);
        List<IVisiblePlanet> frontLines = findFrontLines(myPlanets, theirVisiblePlanets);
        List<IVisiblePlanet> mostContended = findMostContended(frontLines, theirVisiblePlanets);
        Long[] planetPopulations = createPopulationArray(myPlanets);

        for (IVisiblePlanet myPlanet : myPlanets) {

            long planetPop = planetPopulations[myPlanet.getId()];
            planetPop -= processIncomingShuttles(myPlanet);


            // planning attacks
            Stack<Target> targets = findTargets(myPlanet, planetPop, theirVisiblePlanets, neutralVisiblePlanets);
            while (! targets.isEmpty()) {
                Target attack = targets.pop();
                IVisiblePlanet targetPlanet = attack.getTargetPlanet();
                long numAttackers = attack.getNumAttackers();
                planetPop -= numAttackers;
                eventsToExecute.add(planetOperations.transferPeople(myPlanet,targetPlanet,numAttackers));
            }

            // planning behind-the-scenes movements
            if (! frontLines.isEmpty()) {
                Queue<IPlanet> distanceMap = breadthFirstSearch(mostContended.get(0), planets);
                IEdge edge = null;

                if (frontLines.size() == 1 && planetPop >= myPlanet.getSize()*.5) {
                    edge = findRoute(myPlanet, distanceMap);
                } else if (planetPop >= myPlanet.getSize()*.75){
                    edge = findRoute(myPlanet, distanceMap);
                }

                if (edge != null) {
                    IPlanet targetPlanet = getPlanet(edge.getDestinationPlanetId(), planets);
                    long numAttackers = planetPopulations[myPlanet.getId()] / 2;
                    eventsToExecute.add(planetOperations.transferPeople(myPlanet, targetPlanet, numAttackers));
                }
            }
        }
    }


    /**
     * Finds the planets within the system that are visible to the student's team.
     *
     * @param planets   The current state of the system.
     * @return   List of planets that are visible to the student.
     */
    private List<IVisiblePlanet> findVisiblePlanets(List<IPlanet> planets) {
        List<IVisiblePlanet> visiblePlanets = new ArrayList<>(arraySize);
        for (IPlanet planet: planets) {
            if (planet instanceof IVisiblePlanet) {
                visiblePlanets.add((IVisiblePlanet) planet);
            }
        }
        return visiblePlanets;
    }


    /**
     * Creates a new list of planets owned by the given team.
     *
     * @param planetList   List of visible planets in the system.
     * @param owner        The team whose list is being created.
     * @return   List of planets which are of interest.
     */
    private List<IVisiblePlanet> createList(List<IVisiblePlanet> planetList, Owner owner) {
        List<IVisiblePlanet> newList = new ArrayList<>(arraySize);
        for (IVisiblePlanet planet : planetList) {
            if (planet.getOwner() == owner) {
                newList.add(planet);
            }
        }
        return newList;
    }


    /**
     * Identifies which of the student's planets have neighboring enemies.
     *
     * @param myPlanets             List of planets controlled by the student.
     * @param theirVisiblePlanets   List of planets controlled by the enemy.
     * @return   Queue of students' planets with bordering enemies.
     */
    private List<IVisiblePlanet> findFrontLines(List<IVisiblePlanet> myPlanets, List<IVisiblePlanet> theirVisiblePlanets) {
        List <IVisiblePlanet> frontLines = new ArrayList<>(arraySize);
        for (IVisiblePlanet myPlanet : myPlanets) {
            int numEnemies = findNumEnemies(myPlanet, theirVisiblePlanets);
            if (numEnemies > 0) {
                frontLines.add(myPlanet);
            }
        }
        return frontLines;
    }


    /**
     * Identifies which of the student's planets have the most neighboring enemies.
     *
     * @param frontLines            Queue of student's planets with bordering enemies.
     * @param theirVisiblePlanets   List of planets controlled by the enemy.
     * @return   Queue of students' planets with the most bordering enemies.
     */
    private List<IVisiblePlanet> findMostContended(List<IVisiblePlanet> frontLines, List<IVisiblePlanet> theirVisiblePlanets) {
        int maxEnemies = -1;
        List<IVisiblePlanet> mostContended = new ArrayList<>(arraySize);
        for (IVisiblePlanet myPlanet : frontLines) {
            int numEnemies = findNumEnemies(myPlanet, theirVisiblePlanets);
            if (numEnemies > maxEnemies) {
                if (! mostContended.isEmpty()) {
                    mostContended.clear();
                }
                mostContended.add(myPlanet);
            } else if (numEnemies == maxEnemies) {
                mostContended.add(myPlanet);
            }
        }
        return mostContended;
    }


    /**
     * Creates an array of each planet's population, using the planet ID as an index.
     *
     * @param myPlanets   List of planets controlled by the student.
     * @return   An array containing all of the planets' populations.
     */
    private Long[] createPopulationArray(List<IVisiblePlanet> myPlanets) {
        Long[] planetPopulations = new Long[arraySize];
        for (IVisiblePlanet planet : myPlanets) {
            planetPopulations[planet.getId()] = planet.getPopulation();
        }
        return planetPopulations;
    }


    /**
     * Counts the number of neighboring enemies a planet has.
     *
     * @param myPlanet              The student's planet.
     * @param theirVisiblePlanets   List of planets controlled by the enemy.
     * @return   The number of neighboring enemies.
     */
    private int findNumEnemies(IVisiblePlanet myPlanet, List<IVisiblePlanet> theirVisiblePlanets) {
        int numEnemies = 0;
        Set<IEdge> edges = myPlanet.getEdges();
        for (IEdge edge : edges) {
            for (IVisiblePlanet enemyPlanet : theirVisiblePlanets) {
                if (edge.getDestinationPlanetId() == enemyPlanet.getId()) {
                    numEnemies++;
                }
            }
        }
        return numEnemies;
    }


    /**
     * Creates a stack of opponent planets for the student to attack.
     *
     * @param myPlanet         The student's planet.
     * @param planetPop        The population of the student's planet, adjusted for losses to incoming attacks.
     * @param theirPlanets     List of planets controlled by the enemy.
     * @param neutralPlanets   List of planets controlled by the enemy.
     * @return   Stack of Targets, which have a target planet and quantity of attackers.
     */
    private Stack<Target> findTargets(IVisiblePlanet myPlanet, long planetPop, List<IVisiblePlanet> theirPlanets,
                                      List<IVisiblePlanet> neutralPlanets) {
        Stack<Target> targetPlanets = new Stack<>();
        long myPop = planetPop;
        List<IVisiblePlanet> enemyNeighbors = findNeighbors(myPlanet, theirPlanets);
        List<IVisiblePlanet> neutralNeighbors = findNeighbors(myPlanet, neutralPlanets);
        for (IVisiblePlanet neighbor : enemyNeighbors) {
            long theirPop = neighbor.getPopulation();
            if (myPop > theirPop * 1.2) {
                long numAttackers = myPop/2;
                Target targetPlanet = new Target(neighbor, numAttackers);
                myPop -= numAttackers;
                targetPlanets.push(targetPlanet);
            }
        }
        for (IVisiblePlanet neighbor : neutralNeighbors) {
            if (myPop >= 2) {
                long numAttackers = (myPop/10) + 1;
                Target targetPlanet = new Target(neighbor, numAttackers);
                myPop -= numAttackers;
                targetPlanets.push(targetPlanet);
            }
        }
        return targetPlanets;
    }


    /**
     * Creates a list of neighboring planets of a given type.
     *
     * @param myPlanet    The student's planet.
     * @param otherList   The planets being searched for as neighbors.
     * @return   List of neighboring planets.
     */
    private List<IVisiblePlanet> findNeighbors(IVisiblePlanet myPlanet, List<IVisiblePlanet> otherList) {
        List<IVisiblePlanet> neighbors = new ArrayList<>(arraySize);
        Set<IEdge> edges = myPlanet.getEdges();
        for (IEdge edge : edges) {
            for (IVisiblePlanet otherPlanet : otherList) {
                if (edge.getDestinationPlanetId() == otherPlanet.getId()) {
                    neighbors.add(otherPlanet);
                }
            }
        }
        return neighbors;
    }


    /**
     * Processes the enemy shuttles heading towards a planet.
     *
     * @param myPlanet   The student's planet.
     * @return   The sum of all incoming enemy shuttles.
     */
    private int processIncomingShuttles(IVisiblePlanet myPlanet) {
        int sum = 0;
        List<IShuttle> incomingShuttles = myPlanet.getIncomingShuttles();
        for (IShuttle shuttle : incomingShuttles) {
            if (shuttle.getOwner() == Owner.OPPONENT) {
                sum += shuttle.getNumberPeople();
            }
        }
        return sum;
    }


    /**
     * Searches the planet system and adds planets to a queue in order of closeness to the most contended planet.
     *
     * @param mostContended   The student's most contended planet.
     * @param planets         The current state of the system.
     * @return   A queue with planets listed in order of distance from the most contended planet.
     */
    private Queue<IPlanet> breadthFirstSearch(IVisiblePlanet mostContended, List<IPlanet> planets) {
        Queue<IPlanet> distanceQueue = new LinkedList<>();
        distanceQueue.add(mostContended);

        while (distanceQueue.size() < planets.size()) {
            Queue<IPlanet> newNeighbors = new LinkedList<>();
            for (IPlanet planet : distanceQueue) {
                for (IEdge edge : planet.getEdges()) {
                    IPlanet neighbor = getPlanet(edge.getDestinationPlanetId(), planets);
                    if (!distanceQueue.contains(neighbor) && !newNeighbors.contains(neighbor)) {
                        newNeighbors.add(neighbor);
                    }
                }
            }
            while (!newNeighbors.isEmpty()) {
                distanceQueue.add(newNeighbors.remove());
            }
        }
        return distanceQueue;
    }


    /**
     * Finds the best edge to proceed towards the most contended planet.
     *
     * @param myPlanet      The student's planet.
     * @param distanceMap   The queue of planets listed in order of distance from the most contended planet.
     * @return   The edge that provides the quickest route to the student's most contended planet.
     */
    private IEdge findRoute(IVisiblePlanet myPlanet, Queue<IPlanet> distanceMap) {
        Set<IEdge> myPlanetEdges = myPlanet.getEdges();
        for (IPlanet planet : distanceMap) {
            for (IEdge edge : myPlanetEdges) {
                if (planet.getId() == edge.getDestinationPlanetId()) {
                    return edge;
                }
            }
        }
        return null;
    }


    /**
     * Finds the planet associated with a given planet ID.
     *
     * @param planetID   A planet's ID number.
     * @param planets    The current state of the system.
     * @return   The planet associated with the given ID.
     */
    private IPlanet getPlanet(int planetID, List<IPlanet> planets) {
        for (IPlanet planet : planets) {
            if (planet.getId() == planetID) {
                return planet;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "Jacob Hermann";
    }

    @Override
    public boolean compete() { return true; }


    /**
     * A class that represents a planet to be attacked and the number of people attacking that planet.
     */
    private class Target {
        private IVisiblePlanet targetPlanet;
        private long numAttackers;

        private Target(IVisiblePlanet planet, long numPeople) {
            targetPlanet = planet;
            numAttackers = numPeople;
        }

        private IVisiblePlanet getTargetPlanet() { return targetPlanet; }
        private long getNumAttackers() { return numAttackers; }
    }
}