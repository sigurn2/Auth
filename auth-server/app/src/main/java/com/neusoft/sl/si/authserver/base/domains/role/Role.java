package com.neusoft.sl.si.authserver.base.domains.role;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.springframework.core.annotation.AnnotationUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;
import com.neusoft.sl.si.authserver.base.domains.app.App;
import com.neusoft.sl.si.authserver.base.domains.org.Organization;
import com.neusoft.sl.si.authserver.base.domains.resource.Menu;
import com.neusoft.sl.si.authserver.base.domains.resource.Resource;

/**
 * 表示一个角色
 * 
 * <pre>
 * 唯一区别角色的业务主键是"角色名称"，因此角色创建以后不允许修改角色名称
 * </pre>
 * 
 * @author wuyf
 * 
 * @modify by mojf 增加角色关联的系统
 * 
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 3)
@Table(name = "T_ROLE")
public abstract class Role extends UuidEntityBase<Role, String> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 454375936833528029L;

    /**
     * 角色名
     */
    @Column(unique = true)
    private String name;

    /**
     * 角色描述
     */
    @Column(name = "DESCRIPT")
    private String desc;

    /** 角色拥有的资源 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "RESOURCE_ID") })
    private Set<Resource> resources = new HashSet<Resource>();

    /** 角色拥有的应用 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "V_ROLE_APP", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "APP_ID") })
    @OrderBy("sortby asc")
    private Set<App> apps = new HashSet<App>();

    /** 角色所属的机构 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_ROLE_ORG", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ORG_ID") })
    private Set<Organization> orgs = new HashSet<Organization>();

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    @Override
    @JsonIgnoreProperties
    public String getPK() {
        return name;
    }

    /**
     * 创建Role
     * 
     * @param name
     * @param desc
     */
    public Role(String name, String desc) {
        Validate.notBlank(name, "角色名称不能为空");
        this.name = name;
        this.desc = desc;
    }

    public Role() {
        super();
    }

    /**
     * 获取用户类型
     * 
     * @return
     */
    public String getRoleTypeString() {
        DiscriminatorValue discriminator = AnnotationUtils.getAnnotation(this.getClass(), DiscriminatorValue.class);
        if (discriminator != null) {
            return discriminator.value();
        } else {
            return "";
        }
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the resources
     */
    public Set<Resource> getResources() {
        return Collections.unmodifiableSet(resources);
    }

    /**
     * 获取菜单
     * 
     * @return
     */

    public Set<Menu> getMenus() {
        Set<Menu> result = new HashSet<Menu>();
        for (Resource rs : resources) {
            // 按类型匹配
            if (rs instanceof Menu) {
                result.add((Menu) rs);
            }
        }
        return Collections.unmodifiableSet(result);
    }

    /**
     * 增加资源
     * 
     * @param resource
     */
    public void addResource(Resource resource) {
        Validate.notNull(resource, "新增资源不能为空");
        resources.add(resource);
    }

    /**
     * 删除资源
     * 
     * @param resource
     */
    public void removeResource(Resource resource) {
        Validate.notNull(resource, "删除资源不能为空");
        resources.remove(resource);
    }

    /**
     * 删除所有资源
     */
    public void removeAllResources() {
        resources = new HashSet<Resource>();
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }

    public Set<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(Set<Organization> orgs) {
        this.orgs = orgs;
    }


    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
