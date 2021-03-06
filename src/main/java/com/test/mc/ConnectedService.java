package com.test.mc;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component
public class ConnectedService {
    private ConnectedHelper helper = ConnectedHelper.getInstance();

    /***
     * Primary service method determining if the origin and destnation are connected
     * @param origin
     * @param destination
     * @return
     * @throws ApiError
     * @throws IOException
     */
    public String determineConnectivity(String origin, String destination) throws ApiError, IOException {
        if (origin.isEmpty()) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "origin must not be empty");
        }
        if (destination.isEmpty()) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "destination must not be empty");
        }
        Map<String, String> originToDestinationMap = helper.readConnections();
        Map<String, String> destinationToOriginMap = helper.destinationToOriginMap(originToDestinationMap);
        Map<String, List<String>> originToAllPossibleDestinationsMap = helper.findAllPossibleDestinations(originToDestinationMap, destinationToOriginMap);
        return helper.isConnected(origin, destination, originToAllPossibleDestinationsMap);
    }
}
