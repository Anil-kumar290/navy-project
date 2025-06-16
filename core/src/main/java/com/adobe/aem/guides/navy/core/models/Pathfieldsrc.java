package com.adobe.aem.guides.navy.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class)
public class Pathfieldsrc {

    private static final Logger LOG = LoggerFactory.getLogger(Pathfieldsrc.class);

    @ValueMapValue
    @Default(values = "")
    private String selectPath;

    @ScriptVariable
    private Resource resource;

    public List<FolderItem> getChildren() {
        List<FolderItem> items = new ArrayList<>();

        LOG.debug("Selected path from dialog: {}", selectPath);

        if (selectPath == null || selectPath.isEmpty()) {
            LOG.warn("No path selected in dialog.");
            return items;
        }

        Resource folderRes = resource.getResourceResolver().getResource(selectPath);
        if (folderRes != null) {
            LOG.debug("Resolved folder resource: {}", folderRes.getPath());
            collectChildren(folderRes, items);
        } else {
            LOG.warn("Could not resolve resource at path: {}", selectPath);
        }

        LOG.debug("Total children collected: {}", items.size());
        return items;
    }

    private void collectChildren(Resource parent, List<FolderItem> list) {
        for (Resource child : parent.getChildren()) {
            ValueMap vm = child.getValueMap();
            String type = vm.get("jcr:primaryType", "");

            LOG.debug("Child resource: {} | Type: {}", child.getPath(), type);

            if ("nt:folder".equals(type) || "sling:OrderedFolder".equals(type)) {
                FolderItem folderItem = new FolderItem(child.getName(), child.getPath(), "folder");
                collectChildren(child, folderItem.children);
                list.add(folderItem);
                LOG.debug("Added folder: {}", folderItem.path);

            } else if (child.isResourceType("dam/Asset") || "dam:Asset".equals(child.getResourceType())
                    || "dam:Asset".equals(type)) {
                LOG.debug("Detected dam:Asset: {}", child.getPath());
                String imagePath = getOriginalRenditionPath(child);
                FolderItem imageItem = new FolderItem(child.getName(), imagePath, "image");
                list.add(imageItem);
            }

        }
    }

    private String getOriginalRenditionPath(Resource damAsset) {
        Resource rendition = damAsset.getChild("jcr:content/renditions/original");
        if (rendition != null) {
            return rendition.getPath();
        } else {
            LOG.warn("Original rendition not found for asset: {}", damAsset.getPath());
            return damAsset.getPath();
        }
    }

    public static class FolderItem {
        public final String name;
        public final String path;
        public final String type;
        public final List<FolderItem> children;

        public FolderItem(String name, String path, String type) {
            this.name = name;
            this.path = path;
            this.type = type;
            this.children = new ArrayList<>();
        }

        public boolean isFolder() {
            return "folder".equals(type);
        }

        public boolean isImage() {
            return "image".equals(type);
        }
    }
}
