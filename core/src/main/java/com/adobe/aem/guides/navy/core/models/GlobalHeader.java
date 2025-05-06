package com.adobe.aem.guides.navy.core.models;

import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalHeader {

    @ValueMapValue
    private String logoUrl;

    @ChildResource
    private List<NavigationItem> navigations;

    @ChildResource
    private List<IconSetting> iconSettings;

    public String getLogoUrl() {
        return logoUrl;
    }

    public List<NavigationItem> getNavigations() {
        return navigations;
    }

    public List<IconSetting> getIconSettings() {
        return iconSettings;
    }

}