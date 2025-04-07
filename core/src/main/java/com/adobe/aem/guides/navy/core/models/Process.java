package com.adobe.aem.guides.navy.core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Process.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = {
        "ncsecu/components/test" })
public class Process {

    @ChildResource
    private List<TimeLineItem> timelinedetails;

    @SlingObject
    private SlingHttpServletRequest request;

    public List<TimeLineItem> getTimelinedetails() {
        return timelinedetails;
    }

    public Map<String, String> getFinalParams() {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> filteredParameters = new HashMap<>();
        for (TimeLineItem item : timelinedetails) {
            String key = item.title; // Extract the key from TimeLineItem
            if (parameterMap.containsKey(key)) {
                filteredParameters.put(key, parameterMap.get(key)[0]); // Take the first value
            }
        }
        return filteredParameters;
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class TimeLineItem {
        @Inject
        String title;

        public String getTitle() {
            return title;
        }
    }

}
