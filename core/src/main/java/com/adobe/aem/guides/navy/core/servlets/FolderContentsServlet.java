package com.adobe.aem.guides.navy.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Recursive Folder Contents Servlet",
        "sling.servlet.methods=GET",
        "sling.servlet.paths=/bin/foldercontents"
})
public class FolderContentsServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String folderPath = request.getParameter("path");
        JSONObject result = new JSONObject();

        if (folderPath != null && !folderPath.isEmpty()) {
            ResourceResolver resolver = request.getResourceResolver();
            Resource folderRes = resolver.getResource(folderPath);

            if (folderRes != null) {
                JSONArray items = collectItems(folderRes);
                result.put("items", items);
            }
        }

        response.setContentType("application/json");
        response.getWriter().write(result.toString());
    }

    private JSONArray collectItems(Resource parent) {
        JSONArray items = new JSONArray();

        for (Resource child : parent.getChildren()) {
            ValueMap props = child.getValueMap();
            String primaryType = props.get("jcr:primaryType", "");
            JSONObject item = new JSONObject();

            item.put("name", child.getName());
            item.put("path", child.getPath());
            item.put("type", primaryType);

            // Image asset handling
            if ("dam:Asset".equals(primaryType)) {
                String assetPath = child.getPath();
                item.put("isImage", true);
                item.put("imageSrc", assetPath);
            }

            // Recursively collect subfolders
            if ("nt:folder".equals(primaryType) || "sling:OrderedFolder".equals(primaryType)) {
                JSONArray children = collectItems(child); // 🔁 recurse here
                item.put("children", children); // ✅ attach recursively
            }

            items.put(item);
        }

        return items;
    }
}
