package com.adobe.aem.guides.navy.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;

import com.google.gson.JsonObject;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/mycustomservlet",
        "sling.servlet.methods=GET"
})
public class Sample extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        if (name == null || name.isEmpty()) {
            name = "DefaultUser"; // This means "name" was NOT sent correctly
        }

        // Log the received name
        System.out.println("Received name parameter: " + name);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", "Hello, " + name + "! This is a dynamic response.");
        jsonResponse.addProperty("status", "success");

        response.getWriter().write(jsonResponse.toString());
    }
}
