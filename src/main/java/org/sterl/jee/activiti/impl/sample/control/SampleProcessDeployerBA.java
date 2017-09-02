package org.sterl.jee.activiti.impl.sample.control;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

@Singleton
@Startup
public class SampleProcessDeployerBA {

    @Inject
    RepositoryService repositoryService;

    @PostConstruct
    void start() {
        // this will deploy the same process over and over again ...
        Deployment deploy = repositoryService.createDeployment()
                .name("SampleProcess")
                .key("SampleProcess") // note this has no effect, the ID of the bpmn file is used "myProcess"
                .addClasspathResource("org/sterl/jee/activiti/impl/sample/control/SampleProcess.bpmn")
                .deploy();
        System.out.println("Deployed process ID: " + deploy.getId() + " KEY " + deploy.getKey());
        
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId()).singleResult();
        System.out.println(
                "Found process definition key["
                + processDefinition.getKey()
                + "] with id ["
                + processDefinition.getId() 
                + "]");
    }
}
