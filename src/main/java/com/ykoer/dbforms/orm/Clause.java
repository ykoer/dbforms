package com.ykoer.dbforms.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="DBF_CLAUSE", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@XmlRootElement
@NamedQueries ({
    @NamedQuery(name="Clause.findAll", query="select c from Clause c where c.query.id = :queryId order by c.name"),
    @NamedQuery(name="Clause.find", query="select c from Clause c where c.id = :id "),
    @NamedQuery(name="Clause.delete", query="delete from Clause c where c.id = :id")
})
public class Clause extends AbstractGrid {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="query_id",nullable=false)
    private Query query; 
    
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Column(name="html_type")
    private String htmlType;
    
    @NotNull
    @NotEmpty
    @Column(name="data_type")
    private String dataTye;
    
    @NotNull
    @NotEmpty
    private String title;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getHtmlType() {
        return htmlType;
    }

    public void setHtmlType(String htmlType) {
        this.htmlType = htmlType;
    }
    
    public String getDataTye() {
        return dataTye;
    }

    public void setDataTye(String dataTye) {
        this.dataTye = dataTye;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }    
}
