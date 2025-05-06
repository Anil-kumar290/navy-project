package com.adobe.aem.guides.navy.core.models;

import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class GlobalHeaderTest {

    private final AemContext context = new AemContext();
    private GlobalHeader globalHeader;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(GlobalHeader.class, NavigationItem.class, IconSetting.class);

        Resource resource = context.create().resource("/content/example",
                "logoUrl", "http://logo-url");

        context.create().resource("/content/example/navigations/navigation1",
                "navigationText", "Home",
                "navigationLink", "http://home-link");

        context.create().resource("/content/example/iconSettings/iconSetting1",
                "iconText", "Home Icon",
                "iconUrl", "http://icon-url",
                "iconImage", "http://icon-image-url");

        globalHeader = resource.adaptTo(GlobalHeader.class);
    }

    @Test
    void testGetLogoUrl() {
        assertEquals("http://logo-url", globalHeader.getLogoUrl());
    }

    @Test
    void testGetNavigations() {
        List<NavigationItem> navigations = globalHeader.getNavigations();
        assertNotNull(navigations);
        assertFalse(navigations.isEmpty());
        NavigationItem item = navigations.get(0);
        assertEquals("Home", item.getNavigationText());
        assertEquals("http://home-link", item.getNavigationLink());
    }

    @Test
    void testGetIconSettings() {
        List<IconSetting> iconSettings = globalHeader.getIconSettings();
        assertNotNull(iconSettings);
        assertFalse(iconSettings.isEmpty());
        IconSetting icon = iconSettings.get(0);
        assertEquals("Home Icon", icon.getIconText());
        assertEquals("http://icon-url", icon.getIconUrl());
        assertEquals("http://icon-image-url", icon.getIconImage());
    }
}
