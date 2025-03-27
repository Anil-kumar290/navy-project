package com.adobe.aem.guides.navy.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Pagetask {
    public String getPagetype() {
        return pagetype;
    }

    public String getProducttype() {
        return producttype;
    }

    public String getEnable() {
        return enable;
    }

    @ValueMapValue
    private String pagetype;
    @ValueMapValue
    private String producttype;
    @ValueMapValue
    private String enable;
}
