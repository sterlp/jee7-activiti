package org.sterl.jee.activiti.impl.sample.dao;

import javax.ejb.Stateless;

import org.sterl.jee.activiti.impl.sample.model.SampleBE;

@Stateless
public class SampleDAO extends AbstractDao<Long, SampleBE> {
    public SampleDAO() {
        super(SampleBE.class);
    }
}
