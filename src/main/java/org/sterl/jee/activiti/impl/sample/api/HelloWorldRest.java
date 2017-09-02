package org.sterl.jee.activiti.impl.sample.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sterl.jee.activiti.impl.sample.control.HelloWorldBF;

@Path("/hello")
public class HelloWorldRest {
    @Inject
    private HelloWorldBF helloWorldBF;
    
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String get() {
        return "Hello world process executed " + helloWorldBF.getCallCount() + " times.";
    }
    
    @POST
    public void start() {
        helloWorldBF.startProcess();
    }
}