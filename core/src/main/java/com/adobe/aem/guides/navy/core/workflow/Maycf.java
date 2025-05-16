package com.adobe.aem.guides.navy.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.workflow.exec.WorkItem;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component(service = WorkflowProcess.class, property = {
        "process.label=Update CF Metadata from Workflow"
})
public class Maycf implements WorkflowProcess {

    private static final String SERVICE_USER = "anil";
    private static final String FRAGMENT_PATH = "/content/dam/april-cf-model1/nagendra-cf"; // Update to your actual CF
                                                                                            // path

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public void execute(com.adobe.granite.workflow.exec.WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2)
            throws WorkflowException {
        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, SERVICE_USER);

        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(authInfo)) {

            Resource fragmentResource = resolver.getResource(FRAGMENT_PATH);
            if (fragmentResource != null) {
                updateFragmentMetadata(fragmentResource);
                resolver.commit();
                System.out.println("✅ Content Fragment updated at: " + FRAGMENT_PATH);
            } else {
                System.out.println("⚠️ Content Fragment not found at: " + FRAGMENT_PATH);
            }

        } catch (LoginException e) {
            System.err.println("LoginException: Check service user mapping and permissions.");
            e.printStackTrace();
        } catch (PersistenceException e) {
            System.err.println("PersistenceException: Could not commit changes.");
            e.printStackTrace();
        }
    }

    private void updateFragmentMetadata(Resource fragmentResource) {
        Resource metadata = fragmentResource.getChild("jcr:content/metadata");
        if (metadata != null) {
            ModifiableValueMap properties = metadata.adaptTo(ModifiableValueMap.class);
            if (properties != null) {
                properties.put("dc:title", "Updated Title from Workflow");
                properties.put("dc:description", "Updated Description from Workflow");
                System.out.println("🔁 Metadata properties set.");
            } else {
                System.err.println("❌ Could not adapt to ModifiableValueMap.");
            }
        } else {
            System.err.println("❌ Metadata node not found at jcr:content/metadata.");
        }
    }
}
