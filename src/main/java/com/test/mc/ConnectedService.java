package com.test.mc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component
public class ConnectedService {
    private ConnectedHelper helper = ConnectedHelper.getInstance();
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
        //return originToDestinationMap + "<br>" + destinationToOriginMap + "<br>" + originToAllPossibleDestinationsMap.toString();
       //return "hello " + origin + " -- " + destination;
    }
}
