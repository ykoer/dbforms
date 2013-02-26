package com.ykoer.dbforms.jqgrid;

import javax.ws.rs.QueryParam;


public class JQGridParams {

    @QueryParam("rows")
    private int rows;

    @QueryParam("page")
    private int page;

    @QueryParam("sidx")
    private String sidx;

    @QueryParam("sord")
    private String sord;

    @QueryParam("totalrows")
    private int totalrows;


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public int getTotalrows() {
        return totalrows;
    }

    public void setTotalrows(int totalrows) {
        this.totalrows = totalrows;
    }

    /**
     * Calculates the the startIndex by a given page and rows per page.
     * The index will start with 0.
     */
    public int getStartIndex() {
        return (page - 1) * rows;
    }
}
