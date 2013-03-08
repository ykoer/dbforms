package com.ykoer.dbforms.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.Form;

import com.ykoer.dbforms.orm.Database;
import com.ykoer.dbforms.orm.Group;
import com.ykoer.dbforms.orm.Query;
import com.ykoer.dbforms.orm.Schema;
import com.ykoer.dbforms.dao.DatabaseDAO;
import com.ykoer.dbforms.jqgrid.JQGridParams;
import com.ykoer.dbforms.jqgrid.JQGridResponse;


/**
 * JAX-RS Example
 *
 * This class produces a RESTful service to manage the DB configurations.
 */
@Path("/config")
@RequestScoped
public class DBResourceRESTService {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    @EJB
    DatabaseDAO dao;

    /************************************************************************
     *
     * DATABASE
     *
     ************************************************************************/

    @GET
    @Path("/db/all")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse listAllDatabases(@Form JQGridParams params) {
        Long totalRecords = dao.getDatabaseCount();
        return new JQGridResponse(dao.getAllDatabases(params), params.getPage(), params.getRows(), totalRecords);
    }

    @GET
    @Path("/db/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Database getDatabase(@PathParam("id") long id) {
        return dao.getDatabase(id);
    }

    @POST
    @Path("/db")
    public int setDatabase(Database database) {

        if ("edit".equals(database.getOper()) || "add".equals(database.getOper()))
            dao.updateDatabase(database);
        else if ("del".equals(database.getOper()))
            dao.deleteDatabase(database);
        return 1;
    }

    /************************************************************************
     *
     * SCHEMA
     *
     ************************************************************************/

    @GET
    @Path("/schemas/{dbId}")
    public JQGridResponse getSchemasBydbId(@PathParam ("dbId") long dbId, @Form JQGridParams params) {
        Long totalRecords = dao.getSchemaCount(dbId);
        return new JQGridResponse(dao.getSchemasByDatabaseId(dbId, params), params.getPage(), params.getRows(), totalRecords);
    }

    @GET
    @Path("/schemas/query/{queryId}")
    public JQGridResponse getSchemasByQueryId(@PathParam ("queryId") long queryId) {
        return new JQGridResponse(dao.getSchemasByQueryId(queryId));
    }

    @GET
    @Path("/schema/{id}")
    public Schema getSchema(@PathParam("id") long id) {
        return dao.getSchema(id);
    }

    @POST
    @Path("/schema/{dbId}")
    public int setSchema(@PathParam ("dbId") long dbId,
                          Schema schema) {

        Database database = dao.getDatabase(dbId);
        schema.setDatabase(database);

        if ("edit".equals(schema.getOper()) || "add".equals(schema.getOper()))
            dao.updateSchema(schema);
        else if ("del".equals(schema.getOper()))
            dao.deleteSchema(schema);
        return 1;
    }

    /************************************************************************
     *
     * QUERY
     *
     ************************************************************************/

    @GET
    @Path("/queries")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse getQueries(@QueryParam ("dbId") long dbId,
                                     @QueryParam ("schemaId") long schemaId,
                                     @Form JQGridParams params) {

        Long totalRecords = dao.getQueryCount(dbId,schemaId);
        return new JQGridResponse(dao.getQueries(dbId, schemaId, params), params.getPage(), params.getRows(), totalRecords);
    }

    @GET
    @Path("/query/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Query getQuery(@PathParam("id") long id) {
        return dao.getQuery(id);
    }

    @POST
    @Path("/query")
    public int setQuery(@QueryParam ("schemaIds") List<Long> schemaIds,
                         Query query) {

        if ("edit".equals(query.getOper()) || "add".equals(query.getOper()))
            dao.updateQuery(query, schemaIds);
        else if ("del".equals(query.getOper()))
            dao.deleteQuery(query);
        return 1;
    }

    /************************************************************************
     *
     * GROUP
     *
     ************************************************************************/

    @GET
    @Path("/groups")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse listAllGroups() {
        return new JQGridResponse(dao.getAllGroups());
    }

    @POST
    @Path("/group")
    public int setGroup(Group group) {

        if ("edit".equals(group.getOper()) || "add".equals(group.getOper()))
            dao.updateGroup(group);
        else if ("del".equals(group.getOper()))
            dao.deleteGroup(group);
        return 1;
    }
}
