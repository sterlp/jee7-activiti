package org.sterl.jee.activiti.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.activiti.cdi.CdiJtaProcessEngineConfiguration;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.repository.Deployment;

//@ApplicationScoped TODO not in use -- using xml for the bootstrap
public class ActivitiConfig {
    
    @Resource
    private ManagedThreadFactory threadFactory;
    @Resource(mappedName = "jdbc/postgresql")
    private DataSource dataSource;
    @Resource
    private TransactionManager transactionManager;
    
    private ProcessEngine processEngine;

    @PostConstruct
    void setup() {
        CdiJtaProcessEngineConfiguration activitiConfig = new CdiJtaProcessEngineConfiguration();
        // for test we just allow here the schema update
        activitiConfig.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        
        // data source and JEE transaction manager
        activitiConfig.setDataSource(dataSource);
        activitiConfig.setTransactionManager(transactionManager);
        activitiConfig.setTransactionsExternallyManaged(true);
        
        // apply JEE managed executor
        ManagedAsyncJobExecutor asyncExecutor = new ManagedAsyncJobExecutor();
        asyncExecutor.setThreadFactory(threadFactory);
        activitiConfig.setAsyncExecutor(asyncExecutor);
        
        // to use thde default LocalProcessEngineLookup -- we have to use this default name
        activitiConfig.setProcessEngineName(ProcessEngines.NAME_DEFAULT);
        processEngine = activitiConfig.buildProcessEngine();
        ProcessEngines.registerProcessEngine(processEngine);
        ProcessEngines.setInitialized(true);
        
        // do deployments
        Deployment deploy = processEngine.getRepositoryService().createDeployment()
            .addClasspathResource("org/sterl/jee/activiti/sample/control/SampleProcess.bpmn").deploy();
        System.out.println("Deployed process " + deploy);
    }
}