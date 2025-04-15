package com.adobe.aem.guides.navy.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Upstream {
    @ValueMapValue
    private String textfield;
    @ValueMapValue
    private String pathfield;
    @ValueMapValue
    private String richtext;
    @ChildResource
    private List<Upstreamchild> multi;

    public String getTextfield() {
        return textfield;
    }

    public String getPathfield() {
        return pathfield;
    }

    public String getRichtext() {
        return richtext;
    }

    public List<Upstreamchild> multi() {
        return multi;
    }
}
