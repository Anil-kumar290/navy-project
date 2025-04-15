package com.adobe.aem.guides.navy.core.models;

import com.day.cq.dam.api.Asset;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Videopath {

    @ValueMapValue
    private String videoUrl; // External URL

    @ValueMapValue
    private String videoDamPath; // DAM Asset Path

    @Inject
    private ResourceResolver resourceResolver;

    @Self
    private Resource currentResource;

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoDamUrl() {
        if (StringUtils.isNotBlank(videoDamPath)) {
            Resource assetResource = resourceResolver.getResource(videoDamPath);
            if (assetResource != null) {
                Asset asset = assetResource.adaptTo(Asset.class);
                if (asset != null && asset.getOriginal() != null) {
                    return asset.getOriginal().getPath(); // Correct for Video Rendition
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public boolean isExternalVideo() {
        return StringUtils.isNotBlank(videoUrl);
    }

    public boolean isDamVideo() {
        return StringUtils.isNotBlank(getVideoDamUrl());
    }
}
