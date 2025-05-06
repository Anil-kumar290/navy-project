package com.adobe.aem.guides.navy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IconSetting {

    @ValueMapValue
    private String iconText;

    @ValueMapValue
    private String iconUrl;

    @ValueMapValue
    private String iconImage;

    public String getIconText() {
        return iconText;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getIconImage() {
        return iconImage;
    }

}
