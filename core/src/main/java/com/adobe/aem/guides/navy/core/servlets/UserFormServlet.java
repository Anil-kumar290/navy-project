package com.adobe.aem.guides.navy.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
        "sling.servlet.methods=GET",
        "sling.servlet.paths=/bin/userformservlet"
})
public class UserFormServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        response.setContentType("text/plain");
        response.getWriter().write("Received via GET - Name: " + name + ", Email: " + email + ", Mobile: " + mobile);
    }
}
