package com.ykoer.dbforms.jqgrid;

import java.util.List;
import java.util.Map;



public class JQGridResponse {

    private List<?> rows;
    private List<String> colNames;
    private List<Map<String,Object>> colModel;


    public JQGridResponse(List<Map<String,Object>> rows, List<String> colNames, List<Map<String,Object>> colModel) {
        super();
        this.rows = rows;
        this.colNames = colNames;
        this.colModel = colModel;
    }

    public JQGridResponse(List<?> rows) {
        super();



        this.rows = rows;
        this.colNames = null;
        this.colModel = null;
    }



    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public List<String> getColNames() {
        return colNames;
    }


    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }


    public List<Map<String, Object>> getColModel() {
        return colModel;
    }


    public void setColModel(List<Map<String, Object>> colModel) {
        this.colModel = colModel;
    }

}
