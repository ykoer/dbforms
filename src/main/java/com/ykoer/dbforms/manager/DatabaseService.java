package com.ykoer.dbforms.manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ykoer.dbforms.orm.Clause;
import com.ykoer.dbforms.orm.Database;
import com.ykoer.dbforms.orm.Group;
import com.ykoer.dbforms.orm.Query;
import com.ykoer.dbforms.orm.Schema;


@Stateless
@LocalBean
public class DatabaseService {

    @PersistenceContext
    private EntityManager em;
    
    
    /************************************************************************
     * 
     * DATABASE
     * 
     ************************************************************************/
    
    @SuppressWarnings("unchecked")
    public List<Database> getAllDatabases() {
        return em.createNamedQuery("Database.findAll").getResultList();
    }

    public Database getDatabase(Long id) {
        return (Database)em.createNamedQuery("Database.find").setParameter("id", id).getSingleResult();
    }
    
    public void updateDatabase(Database database) {
        em.merge(database);
    }

    public void deleteDatabase(Database database) {
        em.createNamedQuery("Database.delete").setParameter("id", database.getId()).executeUpdate();
    }
    
    /************************************************************************
     * 
     * Schema
     * 
     ************************************************************************/
    
    @SuppressWarnings("unchecked")
    public List<Schema> getSchemasByQueryId(Long queryId) {
        
        List<Schema> schemas  = em.createNamedQuery("Schema.findAll").getResultList();
        
        for (Schema schema : schemas) {
            for (Query query : schema.getQueries()) {
                if (query.getId() == queryId) {
                    schema.setFlag(true);
                }
            }
        }
        return schemas;
    }
    
    @SuppressWarnings("unchecked")
    public List<Schema> getSchemasByDatabaseId(Long databaseId) {
        return em.createNamedQuery("Schema.findByDatabaseId").setParameter("databaseId", databaseId).getResultList();
    }
    
    public Schema getSchema(Long id) {
        return (Schema)em.createNamedQuery("Schema.find").setParameter("id", id).getSingleResult();
    }
    
    public void updateSchema(Schema schema) {
        em.merge(schema);
    }
    
    public void deleteSchema(Schema schema) {
        em.createNamedQuery("Schema.delete").setParameter("id", schema.getId()).executeUpdate();
    }
    
    /************************************************************************
     * 
     * Query
     * 
     ************************************************************************/
    
    @SuppressWarnings("unchecked")
    public List<Query> getAllQueries() {
        return em.createNamedQuery("Query.findAll").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Query> getQueriesBySchemaId(Long schemaId) {
        return em.createNamedQuery("Query.findBySchemaId").setParameter("schemaId", schemaId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Query> getQueriesByDatabaseId(Long databaseId) {
        return em.createNamedQuery("Query.findByDatabaseId").setParameter("databaseId", databaseId).getResultList();
    }
    
    public Query getQuery(Long id) {
        return (Query)em.createNamedQuery("Query.find").setParameter("id", id).getSingleResult();
    }
    
    public void updateQuery(Query query, List<Long> schemaIds) {
        
        if (schemaIds != null && schemaIds.size()>0) {
            for (Long schemaId : schemaIds) {
                if (schemaId>0) {
                    Schema schema = getSchema(schemaId);
                    schema.getQueries().add(query);
                    em.merge(schema);
                } else {
                    em.merge(query);
                }
            }
        } 
    }
    
    public void deleteQuery(Query query) {
        em.createNamedQuery("Query.delete").setParameter("id", query.getId()).executeUpdate();
    }
    
    /************************************************************************
     * 
     * Clause
     * 
     ************************************************************************/
    
    @SuppressWarnings("unchecked")
    public List<Clause> getAllClauses(Long queryId) {
        return em.createNamedQuery("Clause.findAll").setParameter("schemaId", queryId).getResultList();
    }
    
    public Clause getClause(Long id) {
        return (Clause)em.createNamedQuery("Clause.find").setParameter("id", id).getSingleResult();
    }
    
    public void updateClause(Clause clause) {
        em.merge(clause);
    }
    
    public void deleteClause(Clause clause) {
        em.createNamedQuery("Clause.delete").setParameter("id", clause.getId()).executeUpdate();
    }
    
    /************************************************************************
     * 
     * Groups
     * 
     ************************************************************************/
    
    @SuppressWarnings("unchecked")
    public List<Group> getAllGroups() {
        return em.createNamedQuery("Group.findAll").getResultList();
    }
    
    public void updateGroup(Group group) {
        em.merge(group);
    }

    public void deleteGroup(Group group) {
        em.createNamedQuery("Group.delete").setParameter("id", group.getId()).executeUpdate();
    }

}
