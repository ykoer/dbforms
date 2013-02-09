/**
 * Copyright (c) 2006 Red Hat, Inc.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Red Hat, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Red Hat.
 */
package com.ykoer.dbforms.util;

import java.util.List;
import java.util.Map;



public class JQGridWrapper {
    
//    public long records=0;
//    public int page=1;
//    public int total=1;
//    public List<?> rows;
//    
//    private int recordsPerPage=20;
//    
//
//    public JQGridWrapper(int recordsPerPage, int page, long records, List<?> rows) {
//        super();
//        this.recordsPerPage = recordsPerPage;
//        this.page = page;
//        this.rows = rows;
//        this.records = records;
//    }
//    
//    public JQGridWrapper(List<Map<String,Object>> rows, List<String> colNames, List<Map<String,Object>> colModel) {
//        super();
//        this.rows = rows;
//        //this.colNames = colNames;
//        //this.colModel = colModel;
//    }
//
//    public JQGridWrapper(List<?> rows) {
//        super();
//        this.rows = rows;
//    }
//
//
//    public long getRecords() {
//        return rows.size();
//        //return records;
//    }
//
//    public int getPage() {
//        return 1;
//        //return page;
//    }
//
//    public long getTotal() {
//        return 1;
//        //return getPageCount();
//    }
//
//    public List<?> getRows() {
//        return rows;
//    }
//
//    /**
//     * Calculates the total pages from given records size and records per page
//     * 
//     * @return
//     */
//    private long getPageCount() {
//        return (records + recordsPerPage - 1) / recordsPerPage;
//    }
    
    
    
    public List<?> rows;    
    public List<String> colNames;
    public List<Map<String,Object>> colModel;


    public JQGridWrapper(List<Map<String,Object>> rows, List<String> colNames, List<Map<String,Object>> colModel) {
        super();
        this.rows = rows;
        this.colNames = colNames;
        this.colModel = colModel;
    }
    
    public JQGridWrapper(List<?> rows) {
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