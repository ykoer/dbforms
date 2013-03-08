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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="DBF_QUERY", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@XmlRootElement
@NamedQueries ({
    @NamedQuery(name="Query.findAll", query="select q from Query q"),
    //@NamedQuery(name="Query.findByDatabaseId", query="select q from Query q where :databaseId member of q.schemas.database"),
    //@NamedQuery(name="Query.findBySchemaId", query="select q from Query q where :schemaId member of q.schemas"),
    @NamedQuery(name="Query.find", query="select q from Query q where q.id = :id "),
    @NamedQuery(name="Query.delete", query="delete from Query q where q.id = :id"),
    @NamedQuery(name="Query.countAll", query="select count(q) FROM Query q")
})
public class Query extends AbstractGrid {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long   id;

    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name="group_id",nullable=false)
    private Group group;

    //private String groupName;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Column(name="sql_query")
    private String sql;

    @Column(name="meta_data")
    private String metaData;

    @ManyToMany(mappedBy = "queries", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Schema> schemas;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @JsonIgnore
    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    @JsonIgnore
    public Set<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Set<Schema> schemas) {
        this.schemas = schemas;
    }

    @PreRemove
    private void removeQueryFromSchemas() {
        if (schemas != null) {
            for (Schema schema: schemas) {
                schema.getQueries().remove(this);
            }
        }
    }
}
