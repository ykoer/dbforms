package com.ykoer.dbforms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ykoer.dbforms.jqgrid.JQGridParams;
import com.ykoer.dbforms.orm.Database;
import com.ykoer.dbforms.orm.Group;
import com.ykoer.dbforms.orm.Query;
import com.ykoer.dbforms.orm.Schema;


@Stateless
@LocalBean
public class DatabaseDAO {

    @PersistenceContext
    private EntityManager em;


    /************************************************************************
     *
     * DATABASE
     *
     ************************************************************************/
    public long getDatabaseCount() {
        return (Long)em.createNamedQuery("Database.countAll").getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Database> getAllDatabases(JQGridParams params) {

        StringBuffer qstr = new StringBuffer(128);

        StringBuilder selectQuery =new StringBuilder(128);
        selectQuery.append("select db from Database db ");

        if(params.getSidx()!=null) {
            qstr.append(" order by ");
            qstr.append(params.getSidx());
            qstr.append(" ");
            qstr.append(params.getSord());
        }
        selectQuery.append(qstr);

        javax.persistence.Query query = em.createQuery(selectQuery.toString());
        query.setFirstResult(params.getStartIndex());
        query.setMaxResults(params.getRows());

        return query.getResultList();
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
    public long getSchemaCount(Long dbId) {
        return (Long)em.createNamedQuery("Schema.countByDbId").setParameter("dbId", dbId).getSingleResult();
    }

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
    public List<Schema> getSchemasByDatabaseId(Long databaseId, JQGridParams params) {

        StringBuffer qstr = new StringBuffer(128);

        StringBuilder selectQuery =new StringBuilder(128);
        selectQuery.append("select s from Schema s where s.database.id = :databaseId ");

        if(params.getSidx()!=null) {
            qstr.append(" order by ");
            qstr.append(params.getSidx());
            qstr.append(" ");
            qstr.append(params.getSord());
        }
        selectQuery.append(qstr);

        javax.persistence.Query query = em.createQuery(selectQuery.toString());
        query.setParameter("databaseId", databaseId);
        query.setFirstResult(params.getStartIndex());
        query.setMaxResults(params.getRows());

        return query.getResultList();
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
    public long getQueryCount(long dbId, long schemaId) {
        
        ArrayList<Object> bindObj=new ArrayList<Object>();
        StringBuffer selectQuery = new StringBuffer(128);
        selectQuery.append("SELECT count(q) " +
                           "FROM Query q LEFT JOIN q.schemas s " +
                           "             LEFT JOIN s.database d " +
                           "WHERE 1=1");

        if (dbId != 0) {
            selectQuery.append(" AND d.id=?").append(Integer.toString(bindObj.size()+1));
            bindObj.add(dbId);
        }
        if (schemaId != 0) {
            selectQuery.append(" AND s.id=?").append(Integer.toString(bindObj.size()+1));
            bindObj.add(schemaId);
        }
        
        javax.persistence.Query q = em.createQuery(selectQuery.toString());
        int i=1;
        for(Object obj:bindObj) {
            System.out.println("obj: " + obj);
            q.setParameter(i++,obj);
        }
        
        return (Long)q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Query> getAllQueries() {
        return em.createNamedQuery("Query.findAll").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Query> getQueries(Long dbId, Long schemaId, JQGridParams params) {


        ArrayList<Object> bindObj=new ArrayList<Object>();
        StringBuffer selectQuery = new StringBuffer(128);
        selectQuery.append("SELECT DISTINCT q " +
                           "FROM Query q LEFT JOIN q.schemas s " +
                           "             LEFT JOIN s.database d " +
                           "WHERE 1=1");

        if (dbId != 0) {
            selectQuery.append(" AND d.id=?").append(Integer.toString(bindObj.size()+1));
            bindObj.add(dbId);
        }
        if (schemaId != 0) {
            selectQuery.append(" AND s.id=?").append(Integer.toString(bindObj.size()+1));
            bindObj.add(schemaId);
        }
        
        if(params.getSidx()!=null) {
            selectQuery.append(" order by ");
            selectQuery.append("q." + params.getSidx());
            selectQuery.append(" ");
            selectQuery.append(params.getSord());
        }

        javax.persistence.Query q = em.createQuery(selectQuery.toString());


        int i=1;
        for(Object obj:bindObj) {
            System.out.println("obj: " + obj);
            q.setParameter(i++,obj);
        }
        q.setFirstResult(params.getStartIndex());
        q.setMaxResults(params.getRows());

        return q.getResultList();
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
        Query q = getQuery(query.getId());
        em.remove(q);
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
