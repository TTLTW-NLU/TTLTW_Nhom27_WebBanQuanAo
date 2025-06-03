package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;

public class Resource implements Serializable {
    Integer id;
    String resourceName;

    public Resource() {
    }

    public Resource(Integer id, String resourceName) {
        this.id = id;
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", resourceName='" + resourceName + '\'' +
                '}';
    }
}
