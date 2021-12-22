package puzzles;

import Utils.Utils;

import java.util.*;
import java.util.stream.Stream;

public class DayTwelve {

    List<String> input;
    Map<String, Cave> caves = new HashMap<>();
    List<CaveRoute> caveRoutes = new ArrayList<>();

    public long puzzleOne() {
        input = Utils.parseFile("src/resources/12.txt");

        createCaveMap();
        runPuzzle(true);

        return caveRoutes.size();
    }

    public long puzzleTwo() {
        input = Utils.parseFile("src/resources/12.txt");

        createCaveMap();
        runPuzzle(false);

        return caveRoutes.size();
    }

    private void createCaveMap() {
        caves = new HashMap<>();
        input.forEach(line -> {
            String[] split = line.split("-");
            String caveName = split[0];
            String caveConnection = split[1];

            if (!caves.containsKey(caveName)) {
                caves.put(caveName, new Cave(Character.isUpperCase(caveName.charAt(0)), caveName, caveConnection));
            } else {
                caves.get(caveName).connections.add(caveConnection);
            }
        });

        input.forEach(line -> {
            String[] split = line.split("-");
            String caveName = split[1];
            String caveConnection = split[0];

            if (!caves.containsKey(caveName)) {
                caves.put(caveName, new Cave(Character.isUpperCase(caveName.charAt(0)), caveName, caveConnection));
            } else {
                caves.get(caveName).connections.add(caveConnection);
            }
        });

        caves.forEach((name, cave) -> cave.connections.remove("start"));
    }

    private void runPuzzle(boolean isPuzzleOne) {
        Cave startCave = caves.get("start");
        CaveRoute cr = new CaveRoute();
        cr.caves.add(startCave.name);
        caveRoutes.add(cr);

        caveRoutes = startCave.connections.stream().map(connection -> {
            CaveRoute caveRoute1 = new CaveRoute();
            caveRoute1.caves = new ArrayList<>(cr.caves);
            caveRoute1.caves.add(connection);
            return caveRoute1;
        }).toList();

        while (!caveRoutes.stream().allMatch(cRoute -> cRoute.isDeadEnded || cRoute.isEnded)) {
            caveRoutes = caveRoutes.stream()
                    .flatMap(caveRoute -> {
                        if (caveRoute.isDeadEnded || caveRoute.isEnded) {
                            return Stream.of(caveRoute);
                        }

                        String currentLocation = caveRoute.caves.get(caveRoute.caves.size() - 1);

                        if (currentLocation.equals("end")) {
                            caveRoute.isEnded = true;
                            return Stream.of(caveRoute);
                        }

                        List<String> connections = caves.get(currentLocation).connections;

                        if (connections.isEmpty()) {
                            caveRoute.isDeadEnded = true;
                            return Stream.of(caveRoute);
                        }

                        if (isPuzzleOne) {
                            return addNewConnectionsToStreamForPuzzleOne(connections, caveRoute);
                        } else {
                            return addNewConnectionsToStreamForPuzzleTwo(connections, caveRoute);
                        }
                    }).toList();
            System.out.println(caveRoutes);
        }
    }

    private Stream<CaveRoute> addNewConnectionsToStreamForPuzzleOne(List<String> connections, CaveRoute caveRoute) {
        return connections.stream()
                .filter(connection -> caves.get(connection).isLarge || !caveRoute.caves.contains(connection))
                .map(connection -> {
                    CaveRoute caveRoute1 = new CaveRoute();
                    caveRoute1.caves = new ArrayList<>(caveRoute.caves);
                    caveRoute1.caves.add(connection);
                    return caveRoute1;
                });
    }

    private Stream<CaveRoute> addNewConnectionsToStreamForPuzzleTwo(List<String> connections, CaveRoute caveRoute) {
        return connections.stream()
                .filter(connection -> caves.get(connection).isLarge || !caveRoute.caves.contains(connection) || !caveRoute.hasVisitedASmallCaveTwice)
                .map(connection -> {
                    CaveRoute caveRoute1 = new CaveRoute();
                    caveRoute1.caves = new ArrayList<>(caveRoute.caves);
                    if (!caves.get(connection).isLarge && caveRoute.caves.contains(connection)) {
                        caveRoute1.hasVisitedASmallCaveTwice = true;
                    }
                    caveRoute1.caves.add(connection);
                    return caveRoute1;
                });
    }

    private static class CaveRoute {
        List<String> caves = new ArrayList<>();
        boolean isDeadEnded = false;
        boolean isEnded = false;
        boolean hasVisitedASmallCaveTwice = false;

        @Override
        public String toString() {
            return "CaveRoute{" +
                    "caves=" + caves +
                    ", isDeadEnded=" + isDeadEnded +
                    ", isEnded=" + isEnded +
                    ", hasVisitedASmallCaveTwice=" + hasVisitedASmallCaveTwice +
                    '}';
        }
    }

    private static class Cave {
        boolean isLarge;
        boolean hasVisited;
        String name;
        List<String> connections = new ArrayList<>();

        public Cave(boolean isLarge, String name, String connection) {
            this.isLarge = isLarge;
            this.hasVisited = false;
            this.name = name;
            connections.add(connection);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cave cave = (Cave) o;
            return Objects.equals(name, cave.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
