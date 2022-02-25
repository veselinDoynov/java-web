package com.customer.web.entity.version;

import javax.persistence.*;

@Entity
@Table(name = "versions", schema = "java-version")
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public Version() {
    }

    public Version(String version) {
        this.version = version;
    }

    @Column(name = "version")
    private String version;

}
