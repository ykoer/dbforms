package com.ykoer.dbforms.jqgrid;

import java.util.List;
import java.util.Map;



public class JQGridResponse {

    private List<?> rows;
    private int page;
    private Long records;
    private Long recordsPerPage;
    private List<String> colNames;
    private List<Map<String,Object>> colModel;


    public JQGridResponse(List<Map<String,Object>> rows, List<String> colNames, List<Map<String,Object>> colModel) {
        super();
        this.rows = rows;
        this.colNames = colNames;
        this.colModel = colModel;
        records = new Long(rows.size());
        recordsPerPage = 10L;
        page=1;
    }

    public JQGridResponse(List<?> rows, int currentPage, long recordsPerPage, long totalRecords) {
        super();
        this.rows = rows;
        this.page = currentPage;
        this.records = totalRecords;
        this.recordsPerPage = recordsPerPage;
    }

    public JQGridResponse(List<?> rows) {
        super();
        this.rows = rows;

    }


    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Calculates the total pages from given records size and records per page
     *
     * @return
     */
    public Long getTotal() {
        if (records != null &&  recordsPerPage != null)
            return (records + recordsPerPage - 1) / recordsPerPage;
        else
            return null;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
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
