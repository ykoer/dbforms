package com.ykoer.dbforms.orm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="DBF_DATABASE", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQueries ({
    @NamedQuery(name="Database.findAll", query="select db from Database db order by db.env,db.name"),
    @NamedQuery(name="Database.find", query="select db from Database db where db.id = :id"),
    @NamedQuery(name="Database.delete", query="delete from Database db where db.id = :id")
})
@XmlRootElement
public class Database extends AbstractGrid {

    // ---------------------------- Members --------------------------------

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long   id;

    @NotNull
    @NotEmpty
    private String hostName;

    @NotNull
    private Long   port;

    private String sid;

    private String serviceName;

    @NotNull
    @NotEmpty
    private String env;

    @NotNull
    @NotEmpty
    private String name;

    @OneToMany(mappedBy="database", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Schema> schemas;


    // ---------------------------- Setters and Getters --------------------------------


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Schema> getSchemas() {
        return schemas;
    }


    public void setSchemas(Set<Schema> schemas) {
        this.schemas = schemas;


    }
}