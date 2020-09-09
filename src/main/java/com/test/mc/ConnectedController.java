package com.test.mc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ConnectedController {
@Autowired
private ConnectedService service;
        private String origin;
        private String destination;

        @RequestMapping(
         value = "/connected",
         params = {"origin", "destination" },
         method = GET)
        @ResponseBody
        public String connected(@RequestParam(value = "origin", required=true) String origin, @RequestParam( value = "destination", required=true) String destination) {
                this.origin = origin;
                this.destination = destination;
                try {
                        return service.determineConnectivity(origin, destination);
                } catch (IOException ex) {
                        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "city.txt file not found");
                } catch (ApiError apiError) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "origin/destination must not be empty");
                } catch (Exception ex) {
                        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
                }
        }
}
