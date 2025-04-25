package com.adobe.aem.guides.navy.core.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=GoRest User Servlet",
        "sling.servlet.paths=/services/gorest/users",
        "sling.servlet.methods=GET"
})
public class Buttonservlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        response.setContentType("application/json");

        try {
            URL url = new URL("https://gorest.co.in/public/v2/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JsonArray jsonArray = JsonParser.parseString(content.toString()).getAsJsonArray();
            response.getWriter().write(jsonArray.toString());

        } catch (Exception e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("{\"error\":\"Failed to fetch users\"}");
            } catch (Exception ioEx) {
                ioEx.printStackTrace();
            }
        }
    }
}
