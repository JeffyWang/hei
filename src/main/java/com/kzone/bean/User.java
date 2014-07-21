package com.kzone.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffy on 14-4-24.
 */
@Entity
@Table(name = "k_user")
public class User implements Serializable {
    private static final long serialVersionUID = 7650194935594742094L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "uuid", nullable = true, columnDefinition = "varchar(128) default ''")
    private String uuid;
    @Column(name = "phone_number", nullable = true, columnDefinition = "varchar(128) default ''")
    private String phoneNumber;
    @Column(name = "username", nullable = true, columnDefinition = "varchar(128) default ''", unique = true)
    private String username;
    @Column(name = "os_type", nullable = true, columnDefinition = "varchar(128) default ''")
    private String osType;
    @Column(name = "os_version", nullable = true, columnDefinition = "varchar(128) default ''")
    private String osVersion;
    @Column(name = "app_version", nullable = true, columnDefinition = "varchar(128) default ''")
    private String appVersion;
    @Column(name = "app_name", nullable = true, columnDefinition = "varchar(128) default ''")
    private String appName;
    @Column(name = "token", nullable = true, columnDefinition = "varchar(128) default ''")
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    public User() {
        super();
        this.createTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
