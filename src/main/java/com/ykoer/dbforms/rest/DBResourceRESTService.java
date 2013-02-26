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

import com.ykoer.dbforms.manager.DatabaseService;
import com.ykoer.dbforms.orm.Clause;
import com.ykoer.dbforms.orm.Database;
import com.ykoer.dbforms.orm.Group;
import com.ykoer.dbforms.orm.Query;
import com.ykoer.dbforms.orm.Schema;
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
    DatabaseService databaseService;

    /************************************************************************
     *
     * DATABASE
     *
     ************************************************************************/

    @GET
    @Path("/db/all")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse listAllDatabases(@Form JQGridParams params) {
        return new JQGridResponse(databaseService.getAllDatabases(params));
    }

    @GET
    @Path("/db/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Database getDatabase(@PathParam("id") long id) {
        return databaseService.getDatabase(id);
    }

    @POST
    @Path("/db")
    public int setDatabase(Database database) {

        if ("edit".equals(database.getOper()) || "add".equals(database.getOper()))
            databaseService.updateDatabase(database);
        else if ("del".equals(database.getOper()))
            databaseService.deleteDatabase(database);
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
        return new JQGridResponse(databaseService.getSchemasByDatabaseId(dbId, params));
    }

    @GET
    @Path("/schemas/query/{queryId}")
    public JQGridResponse getSchemasByQueryId(@PathParam ("queryId") long queryId) {
        return new JQGridResponse(databaseService.getSchemasByQueryId(queryId));
    }

    @GET
    @Path("/schema/{id}")
    public Schema getSchema(@PathParam("id") long id) {
        return databaseService.getSchema(id);
    }

    @POST
    @Path("/schema/{dbId}")
    public int setSchema(@PathParam ("dbId") long dbId,
                          Schema schema) {

        Database database = databaseService.getDatabase(dbId);
        schema.setDatabase(database);

        if ("edit".equals(schema.getOper()) || "add".equals(schema.getOper()))
            databaseService.updateSchema(schema);
        else if ("del".equals(schema.getOper()))
            databaseService.deleteSchema(schema);
        return 1;
    }

    /************************************************************************
     *
     * QUERY
     *
     ************************************************************************/

    @GET
    @Path("/queries2")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse listAllQueries() {
        return new JQGridResponse(databaseService.getAllQueries());
    }

    @GET
    @Path("/queries")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse getQueries(@QueryParam ("dbId") long dbId,
                                    @QueryParam ("schemaId") long schemaId) {

        return new JQGridResponse(databaseService.getQueries(dbId, schemaId));
    }

    @GET
    @Path("/query/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Query getQuery(@PathParam("id") long id) {
        return databaseService.getQuery(id);
    }

    @POST
    @Path("/query")
    public int setQuery(@QueryParam ("schemaIds") List<Long> schemaIds,
                         Query query) {

        System.out.println(schemaIds.get(0));
        System.out.println(query.getName());
        System.out.println(query.getOper());

        if ("edit".equals(query.getOper()) || "add".equals(query.getOper()))
            databaseService.updateQuery(query, schemaIds);
//        else if ("del".equals(query.getOper()))
//            databaseService.deleteQuery(query);
        return 1;
    }

    /************************************************************************
     *
     * CLAUSE
     *
     ************************************************************************/

    @GET
    @Path("/clauses/{queryId}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridResponse listAllClauses(@PathParam ("queryId") long queryId) {
        return new JQGridResponse(databaseService.getAllClauses(queryId));
    }

    @GET
    @Path("/clause/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Clause getClause(@PathParam("id") long id) {
        return databaseService.getClause(id);
    }

    @POST
    @Path("/clause/{queryId}")
    public int setClause(@PathParam ("queryId") long queryId,
                          Clause clause) {

        Query query = databaseService.getQuery(queryId);
        clause.setQuery(query);

        if ("edit".equals(clause.getOper()) || "add".equals(clause.getOper()))
            databaseService.updateClause(clause);
        else if ("del".equals(clause.getOper()))
            databaseService.deleteClause(clause);
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
        return new JQGridResponse(databaseService.getAllGroups());
    }

    @POST
    @Path("/group")
    public int setGroup(Group group) {

        if ("edit".equals(group.getOper()) || "add".equals(group.getOper()))
            databaseService.updateGroup(group);
        else if ("del".equals(group.getOper()))
            databaseService.deleteGroup(group);
        return 1;
    }
}
