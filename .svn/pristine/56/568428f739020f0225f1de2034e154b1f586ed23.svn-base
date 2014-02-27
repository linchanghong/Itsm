package com.sccl.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sccl.framework.entity.TableEntity;


/**
 * TestFlow entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_TESTFLOW", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="TESTFLOW",schema="ITSM")
public class TestFlow extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 2336816672728811709L;
	private Long id;
     private String name;
     private Long wage;
     private String remark;
     private Long sendId;


    // Constructors

    /** default constructor */
    public TestFlow() {
    }

	/** minimal constructor */
    public TestFlow(Long id) {
        this.id = id;
    }
    
    /** full constructor */
    public TestFlow(Long id, String name, Long wage, String remark, Long sendId) {
        this.id = id;
        this.name = name;
        this.wage = wage;
        this.remark = remark;
        this.sendId = sendId;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(generator = "GEN_TESTFLOW", strategy = GenerationType.SEQUENCE)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="NAME", length=50)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="WAGE", precision=22, scale=0)
    public Long getWage() {
        return this.wage;
    }
    
    public void setWage(Long wage) {
        this.wage = wage;
    }
    
    @Column(name="REMARK", length=1000)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="SENDID", precision=22, scale=0)

    public Long getSendId() {
        return this.sendId;
    }
    
    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }
   








}