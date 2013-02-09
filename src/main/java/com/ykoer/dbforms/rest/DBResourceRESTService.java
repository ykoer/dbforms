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

import com.ykoer.dbforms.manager.DatabaseService;
import com.ykoer.dbforms.orm.Clause;
import com.ykoer.dbforms.orm.Database;
import com.ykoer.dbforms.orm.Group;
import com.ykoer.dbforms.orm.Query;
import com.ykoer.dbforms.orm.Schema;
import com.ykoer.dbforms.util.JQGridWrapper;


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
    @Path("/dbs")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridWrapper listAllDatabases() {
        return new JQGridWrapper(databaseService.getAllDatabases());
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
    @Path("/schemas/{databaseId}")
    public JQGridWrapper getSchemasByDatabaseId(@PathParam ("databaseId") long databaseId) {
        return new JQGridWrapper(databaseService.getSchemasByDatabaseId(databaseId));
    }
    
    @GET
    @Path("/schemas/query/{queryId}")
    public JQGridWrapper getSchemasByQueryId(@PathParam ("queryId") long queryId) {
        return new JQGridWrapper(databaseService.getSchemasByQueryId(queryId));
    }
    
    @GET
    @Path("/schema/{id}")
    public Schema getSchema(@PathParam("id") long id) {
        return databaseService.getSchema(id);
    }
    
    @POST
    @Path("/schema/{databaseId}")
    public int setSchema(@PathParam ("databaseId") long databaseId,
                          Schema schema) {
        
        Database database = databaseService.getDatabase(databaseId);
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
    @Path("/queries")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridWrapper listAllQueries() {
        return new JQGridWrapper(databaseService.getAllQueries());
    }
    
    @GET
    @Path("/queries/schemaId={schemaId}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridWrapper getQueriesBySchemaId(@PathParam ("schemaId") long schemaId) {
        return new JQGridWrapper(databaseService.getQueriesBySchemaId(schemaId));
    }
    
    @GET
    @Path("/queries")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public JQGridWrapper getQueries(@QueryParam ("databaseId") long databaseId,
                                    @QueryParam ("schemaId") long schemaId) {
        
        if (schemaId == 0)
            return new JQGridWrapper(databaseService.getQueriesByDatabaseId(databaseId));
        else
            return new JQGridWrapper(databaseService.getQueriesBySchemaId(schemaId));
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

        if ("edit".equals(query.getOper()) || "add".equals(query.getOper()))
            databaseService.updateQuery(query, schemaIds);
        else if ("del".equals(query.getOper()))
            databaseService.deleteQuery(query);
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
    public JQGridWrapper listAllClauses(@PathParam ("queryId") long queryId) {
        return new JQGridWrapper(databaseService.getAllClauses(queryId));
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
    public JQGridWrapper listAllGroups() {
        return new JQGridWrapper(databaseService.getAllGroups());
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
