package com.ykoer.dbforms.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ykoer.dbforms.dao.QueryManager;
import com.ykoer.dbforms.jqgrid.JQGridResponse;
import com.ykoer.dbforms.model.FormField;



/**
 * JAX-RS Example
 *
 * This class produces a RESTful service to manage the DB configurations.
 */
@Path("/query")
@RequestScoped
public class QueryResourceRESTService {

    @EJB
    QueryManager manager;


    @POST
    @Path("/query")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse runSQL(String sql) {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diziler","diziler","password");

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

//            while (rs.next()) {
//                System.out.println("-----------> " + rs.getString(4));
//            }

            return createJQGridData(rs);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates Grid Data from a given resultset
     *
     *
     * @param rs The resultset objec
     * @return JSON or XML formatted jqgrid data
     *
     * <pre>
     * {
     *   "colModel": [
     *     {
     *       "index": "col1",
     *       "name": "col1",
     *       "sorttype": "int"
     *     }
     *   ],
     *   "colNames": [
     *     "col1"
     *   ],
     *   "rows": [
     *     {
     *       "col1": "Value 1"
     *     },
     *     {
     *       "col1": "Value 1",
     *     }
     *   ]
     * }
     * </pre>
     */
    private JQGridResponse createJQGridData(ResultSet rs) {

        List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        List<String> colNames = new ArrayList<String>();
        List<Map<String,Object>> colModel = new ArrayList<Map<String,Object>>();

        try {
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            int j=0;
            while (rs.next()) {
                Map<String,Object> map = new HashMap<String,Object>();
                for (int i = 1; i < numColumns + 1; i++) {
                    String columnName = rsmd.getColumnName(i);

                    if (j == 0) {
                        colNames.add(columnName.toUpperCase());
                        Map<String,Object> m = new HashMap<String,Object>();
                        m.put("name",columnName);
                        m.put("index",columnName);
                        m.put("sorttype",getJsType(rsmd.getColumnType(i)));
                        colModel.add(m);
                    }

                    if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                        map.put(columnName, rs.getArray(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                        map.put(columnName, rs.getInt(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                        map.put(columnName, rs.getBoolean(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        // skip
                    } else if (rsmd.getColumnType(i) == java.sql.Types.LONGVARBINARY) {
                        // skip
                    } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                        map.put(columnName, rs.getDouble(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                        map.put(columnName, rs.getFloat(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                        map.put(columnName, rs.getInt(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                        map.put(columnName, rs.getNString(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
                        map.put(columnName, rs.getString(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                        map.put(columnName, rs.getInt(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                        map.put(columnName, rs.getInt(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                        map.put(columnName, rs.getInt(i));
                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                        map.put(columnName, rs.getTimestamp(i));
                    } else {
                        map.put(columnName, rs.getObject(i));
                    }
                }
                j++;
                rows.add(map);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new JQGridResponse(rows, colNames, colModel);
    }

    /**
     * Maps SQL types to Javascript JQGrid types
     * {@link http://www.docjar.com/html/api/java/sql/Types.java.html}
     *
     * @param sqlType The SQL Type Number
     * @return
     */
    private String getJsType(int sqlType) {

        switch (sqlType) {
        case java.sql.Types.BIGINT:
        case java.sql.Types.INTEGER:
        case java.sql.Types.TINYINT:
        case java.sql.Types.SMALLINT:
            return "int";
        case java.sql.Types.BOOLEAN:
        case java.sql.Types.VARCHAR:
        case java.sql.Types.NVARCHAR:
            return "string";
        case java.sql.Types.DOUBLE:
        case java.sql.Types.FLOAT:
            return "float";
        case java.sql.Types.DATE:
        case java.sql.Types.TIMESTAMP:
            return "date";

        default:
            return null;
        }
    }

    @POST
    @Path("/execute/{schemaId}/{queryId}")
    public JQGridResponse executeQuery(@PathParam("schemaId") long schemaId, 
                                       @PathParam("queryId") long queryId,
                                       @FormParam("field") List<String> queryValues) {


        for (String s:queryValues) {
            System.out.println("--->" + s);
        }

        return null;

    }


    @GET
    @Path("/form/{queryId}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<FormField> getFormData(@PathParam("queryId") long queryId) {

        return manager.getFormData(queryId);
    }
}
