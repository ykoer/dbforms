package com.ykoer.dbforms.pojo;

import java.util.Map;


public class Row {
    
    private Map<String,Object> columns;

    public Map<String,Object> getColumns() {
        return columns;
    }

    public void setColumns(Map<String,Object> columns) {
        this.columns = columns;
    }
}
