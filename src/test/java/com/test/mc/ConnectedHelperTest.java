package com.test.mc;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectedHelperTest {
    private ConnectedHelper helper = ConnectedHelper.getInstance();
    private Map<String, String> originMap;
    @Before
    public void executedBeforeEach() {
        try {
            originMap = helper.readConnections();
        }catch (IOException ex) {
        }
    }
    @Test
    public void readConnections() {
        Assert.assertEquals(originMap.size(), 4);
        Assert.assertEquals(originMap.get("Newark"), "Boston");
    }

    @Test
    public void destinationToOriginMap() {
        Map<String, String> map = helper.destinationToOriginMap(originMap);
        Assert.assertEquals(map.size(), 4);
        Assert.assertEquals(map.get("Albany"), "Trenton");
    }

    @Test
    public void findAllPossibleDestinations() {
        Map<String, String> destinationToOriginMap = helper.destinationToOriginMap(originMap);
        Map<String, List<String>> combinedMap = helper.findAllPossibleDestinations(originMap, destinationToOriginMap);
        List<String> list = new ArrayList<>();
        list.add("New York");
        list.add("Newark");
        Assert.assertEquals(combinedMap.size(), 4);
        Assert.assertEquals(combinedMap.get("Boston"), list);
    }

    @Test
    public void isConnected() {
        Map<String, String> destinationToOriginMap = helper.destinationToOriginMap(originMap);
        Map<String, List<String>> combinedMap = helper.findAllPossibleDestinations(originMap, destinationToOriginMap);
        String connected = helper.isConnected("Albany", "Newark", combinedMap);
        Assert.assertEquals(connected, "No");
        connected = helper.isConnected("Boston", "Newark", combinedMap);
        Assert.assertEquals(connected, "Yes");
        connected = helper.isConnected("Boston", "Albany", combinedMap);
        Assert.assertEquals(connected, "No");
    }
}