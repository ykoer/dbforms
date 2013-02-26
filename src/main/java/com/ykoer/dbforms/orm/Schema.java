package com.ykoer.dbforms.orm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="DBF_SCHEMA")
@XmlRootElement
@NamedQueries ({
    @NamedQuery(name="Schema.findAll", query="select s from Schema s order by s.name"),
    @NamedQuery(name="Schema.findByDatabaseId", query="select s from Schema s where s.database.id = :databaseId order by s.name"),
    @NamedQuery(name="Schema.find", query="select s from Schema s where s.id = :id "),
    @NamedQuery(name="Schema.delete", query="delete from Schema s where s.id = :id")
})
public class Schema extends AbstractGrid {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="db_id")
    private Database database;

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String name;

    @Column(name="group_name")
    private String group;

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinTable(name="DBF_SCHEMA_QUERIES",
               joinColumns=@JoinColumn(name="schema_id", referencedColumnName = "id"),
               inverseJoinColumns=@JoinColumn(name="query_id", referencedColumnName = "id")
    )
    private Set<Query> queries;

    @Transient
    private boolean flag = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @JsonIgnore
    public Set<Query> getQueries() {
        return this.queries;
    }

    public void setQueries(Set<Query> queries) {
        this.queries=queries;
    }


    public boolean isFlag() {
        return flag;
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @PreRemove
    private void removeSchemaFromQueries() {
        if (queries != null) {
            for (Query query: queries) {
                query.getSchemas().remove(this);
            }
        }
    }
}
