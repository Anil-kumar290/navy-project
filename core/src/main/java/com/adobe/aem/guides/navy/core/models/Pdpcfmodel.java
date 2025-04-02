package com.adobe.aem.guides.navy.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.adobe.cq.dam.cfm.ContentElement;

@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Pdpcfmodel {

    private static final Logger LOG = LoggerFactory.getLogger(Pdpcfmodel.class);

    @ValueMapValue
    private String themeselector;

    @ValueMapValue
    private String heroimage;

    @ValueMapValue
    private String imagealt;

    @ValueMapValue
    private String cfmodel; // Path to Content Fragment

    private String modelnumber;
    private String producttitle;

    @Self
    private Resource resource;

    @PostConstruct
    protected void init() {
        if (cfmodel == null || cfmodel.isEmpty()) {
            LOG.warn("Content Fragment path is empty or null.");
            return;
        }

        LOG.info("Fetching Content Fragment from path: {}", cfmodel);
        Resource cfResource = resource.getResourceResolver().getResource(cfmodel);

        if (cfResource == null) {
            LOG.error("Content Fragment not found at path: {}", cfmodel);
            return;
        }

        ContentFragment contentFragment = cfResource.adaptTo(ContentFragment.class);

        if (contentFragment == null) {
            LOG.error("Failed to adapt resource to Content Fragment at path: {}", cfmodel);
            return;
        }

        modelnumber = getFragmentData(contentFragment, "modelNumber");
        producttitle = getFragmentData(contentFragment, "productTitle");

        LOG.info("Extracted modelNumber: {}, productTitle: {}", modelnumber, producttitle);
    }

    private String getFragmentData(ContentFragment contentFragment, String fieldName) {
        try {
            ContentElement element = contentFragment.getElement(fieldName);
            if (element != null) {
                FragmentData data = element.getValue();
                return (data != null) ? data.getValue().toString() : "";
            }
        } catch (Exception e) {
            LOG.error("Error retrieving field '{}' from Content Fragment: {}", fieldName, e.getMessage());
        }
        return "";
    }

    public String getThemeselector() {
        return themeselector;
    }

    public String getHeroimage() {
        return heroimage;
    }

    public String getImagealt() {
        return imagealt;
    }

    public String getCfmodel() {
        return cfmodel;
    }

    public String getModelnumber() {
        return modelnumber;
    }

    public String getProducttitle() {
        return producttitle;
    }
}
