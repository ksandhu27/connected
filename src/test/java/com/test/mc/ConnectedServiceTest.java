package com.test.mc;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ConnectedServiceTest extends TestCase {
    private Map<String, String> originMap;
    private Map<String, String> destinationToOriginMap;
    private Map<String, List<String>> originToAllPossibleDestinationsMap;
    private ConnectedService service = new ConnectedService();
    @Before
    public void beforeEachTest() {
        originMap = new HashMap<>();
        originMap.put("Boston", "New York");
        originMap.put("Philadelphia", "Newark");
        originMap.put("Newark", "Boston");
        originMap.put("Trenton", "Albany");

        destinationToOriginMap = new HashMap<>();
        destinationToOriginMap.put("New York","Boston");
        destinationToOriginMap.put("Newark","Philadelphia");
        destinationToOriginMap.put("Boston","Newark");
        destinationToOriginMap.put("Albany","Trenton");

        originToAllPossibleDestinationsMap = new HashMap<>();
        originToAllPossibleDestinationsMap.put("Boston", Arrays.asList("New York", "Newark"));
        originToAllPossibleDestinationsMap.put("Philadelphia", Arrays.asList("Newark"));
        originToAllPossibleDestinationsMap.put("Newark", Arrays.asList("Boston", "Philadelphia"));
        originToAllPossibleDestinationsMap.put("Trenton", Arrays.asList("Albany"));

    }

    public void testDetermineConnectivity() throws IOException, ApiError {
        ConnectedHelper helper;
        helper = Mockito.mock(ConnectedHelper.class);
        when(helper.readConnections()).thenReturn(originMap);
        when(helper.destinationToOriginMap(originMap)).thenReturn(destinationToOriginMap);
        when(helper.findAllPossibleDestinations(originMap, destinationToOriginMap)).thenReturn(originToAllPossibleDestinationsMap);
        when(helper.isConnected("Boston", "Philadelphia", originToAllPossibleDestinationsMap)).thenReturn("Yes");
        String isConnected = service.determineConnectivity("Boston", "Philadelphia");
        Assert.assertEquals(isConnected, "Yes");
    }
}