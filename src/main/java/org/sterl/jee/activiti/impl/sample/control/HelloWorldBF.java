package org.sterl.jee.activiti.impl.sample.control;

import java.util.concurrent.atomic.AtomicLong;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import org.sterl.jee.activiti.impl.sample.dao.SampleDAO;
import org.sterl.jee.activiti.impl.sample.model.SampleBE;

@Named // that activiti finds this EJB
@Singleton // show that this is a singleton
public class HelloWorldBF {
    private final AtomicLong COUNT = new AtomicLong(0);

    @Inject RuntimeService runtimeService;
    @Inject SampleDAO sampleDAO;
    
    public long hello() {
        final long c = COUNT.incrementAndGet();
        sampleDAO.create(SampleBE.builder()
                .time(System.currentTimeMillis())
                .text("Staring process count " + c)
                .build());
        System.out.println("Hello called " + c + " times in HelloWorldBF ...");
        return c;
    }

    public long getCallCount() {
        return COUNT.get();
    }

    public void startProcess() {
        // use here the id you defined in the process designer
        ProcessInstance process = runtimeService.startProcessInstanceByKey("myProcess");
        System.out.println("Process " + process.getName() + " started with ID: " + process.getId());
    }
}