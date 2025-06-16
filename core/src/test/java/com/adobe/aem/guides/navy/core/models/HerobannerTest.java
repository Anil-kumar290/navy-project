package com.adobe.aem.guides.navy.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AemContextExtension.class)
class HerobannerTest {

    private final AemContext context = new AemContext();
    private static final String TITLE = "Test Hero Title";
    private static final String DESCRIPTION = "This is a test description.";
    private static final String IMAGE = "/content/dam/test/hero.jpg";

    private Herobanner heroBanner;

    @BeforeEach
    void setUp() {
        context.create().resource("/content/test-hero",
                "title", TITLE,
                "description", DESCRIPTION,
                "image", IMAGE);

        Resource resource = context.resourceResolver().getResource("/content/test-hero");
        heroBanner = resource.adaptTo(Herobanner.class);
    }

    @Test
    void testGetTitle() {
        assertEquals(TITLE, heroBanner.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals(DESCRIPTION, heroBanner.getDescription());
    }

    @Test
    void testGetImage() {
        assertEquals(IMAGE, heroBanner.getImage());
    }

    @Test
    void testOptionalFieldsWhenNotPresent() {
        // simulate a resource without any properties
        context.create().resource("/content/empty-hero");
        Herobanner emptyHeroBanner = context.resourceResolver()
                .getResource("/content/empty-hero")
                .adaptTo(Herobanner.class);

        assertNull(emptyHeroBanner.getTitle());
        assertNull(emptyHeroBanner.getDescription());
        assertNull(emptyHeroBanner.getImage());
    }
}
