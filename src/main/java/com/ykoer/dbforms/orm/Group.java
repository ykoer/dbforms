package com.ykoer.dbforms.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="DBF_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@XmlRootElement
@NamedQueries ({
    @NamedQuery(name="Group.findAll", query="select g from Group g"),
    @NamedQuery(name="Group.find", query="select g from Group g where g.id = :id"),
    @NamedQuery(name="Group.delete", query="delete from Group g where g.id = :id")
})
public class Group extends AbstractGrid {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @Column(name="ui_order")
    private Integer order;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}