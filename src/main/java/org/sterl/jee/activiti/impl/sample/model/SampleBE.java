package org.sterl.jee.activiti.impl.sample.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SampleBE implements Serializable {

    @GeneratedValue
    @Id
    private Long id;
    private long time;
    private String text;
}
