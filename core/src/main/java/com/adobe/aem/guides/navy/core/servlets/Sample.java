package com.adobe.aem.guides.navy.core.servlets;

import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/mycustomservlet", // Path-based servlet
        "sling.servlet.methods=GET" // Supports only GET requests
})
public class Sample extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Hardcoded JSON response
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Hello from AEM Servlet!");
        jsonResponse.addProperty("status", "success");

        response.getWriter().write(jsonResponse.toString());
    }
}
