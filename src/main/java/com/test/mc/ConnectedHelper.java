package com.test.mc;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectedHelper {
    private static ConnectedHelper instance = null;

    private ConnectedHelper() {
    }

    public static ConnectedHelper getInstance() {
        if (instance == null) {
            instance = new ConnectedHelper();
        }
        return instance;
    }

    /***
     * This method reads the input city.txt file and creates an origin to destination map
     * @return
     * @throws IOException
     */
    public Map<String, String> readConnections() throws IOException {
        Map<String, String> map = new HashMap<>();
        ClassLoader classLoader = ConnectedHelper.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("city.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] cities = line.split(",");
            String origin = cities[0];
            String destination = cities[1];
            map.put(origin, destination);
        }
        br.close();

        return map;

    }

    /***
     * This method creates a destination to origin map
     * @param originToDestinationMap
     * @return
     */
    public Map<String, String> destinationToOriginMap(Map<String, String> originToDestinationMap) {
    return originToDestinationMap.entrySet()
            .stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    /***
     * this method establishes the relationahip between all possible origins and destinations
     * @param originToDestinationMap
     * @param destinationToOriginMap
     * @return
     */
    public Map<String, List<String>> findAllPossibleDestinations(Map<String, String> originToDestinationMap,
                                                                 Map<String, String> destinationToOriginMap) {
        Map<String, List<String>> combinedMap = new HashMap<>();
        for(Map.Entry<String, String> originEntry: originToDestinationMap.entrySet()) {
            List<String> list = new ArrayList<>();
            list.add(originEntry.getValue());
            String val = destinationToOriginMap.get(originEntry.getKey());
            list.add(val) ;
            combinedMap.put(originEntry.getKey(), list);
        }
        return combinedMap;
    }

    /***
     * this method determines is the origin and destination are connected
     * @param origin
     * @param destination
     * @param originToAllPossibleDestinationsMap
     * @return
     */

    public String isConnected(String origin, String destination, Map<String,
            List<String>> originToAllPossibleDestinationsMap) {
        for (Map.Entry<String, List<String>> connection: originToAllPossibleDestinationsMap.entrySet()) {
            if (origin.equals(connection.getKey()) && connection.getValue().contains(destination)) {
                return "Yes";
            }
            if (connection.getValue().contains(destination) && connection.getValue().contains(origin)) {
                return "Yes";
            }
        }
        return "No";
    }
}
