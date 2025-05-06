package com.adobe.aem.guides.navy.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class NavigationItemTest {

    private final AemContext context = new AemContext();
    private NavigationItem navigationItem;

    @BeforeEach
    void setUp() {
        Resource resource = context.create().resource("/content/navigation-item",
                "navigationText", "About Us",
                "navigationLink", "/content/about-us");

        navigationItem = resource.adaptTo(NavigationItem.class);
    }

    @Test
    void testGetNavigationText() {
        assertNotNull(navigationItem);
        assertEquals("About Us", navigationItem.getNavigationText());
    }

    @Test
    void testGetNavigationLink() {
        assertNotNull(navigationItem);
        assertEquals("/content/about-us", navigationItem.getNavigationLink());
    }

}
