package com.ykoer.dbforms.orm;

import javax.persistence.Transient;


public abstract class AbstractGrid {
    
    @Transient
    private String oper;

    public String getOper() {
        return oper;
    }
    
    public void setOper(String oper) {
        this.oper = oper;
    }
}
