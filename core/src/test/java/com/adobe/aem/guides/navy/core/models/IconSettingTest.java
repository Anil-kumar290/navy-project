package com.adobe.aem.guides.navy.core.models;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class IconSettingTest {

    private final AemContext context = new AemContext();
    private IconSetting iconSetting;

    @BeforeEach
    void setUp() {
        Resource resource = context.create().resource("/content/icon-setting",
                "iconText", "Contact",
                "iconUrl", "/content/contact",
                "iconImage", "/content/dam/mazda/logo.svg");

        iconSetting = resource.adaptTo(IconSetting.class);
    }

    @Test
    void testGetIconText() {
        assertNotNull(iconSetting);
        assertEquals("Contact", iconSetting.getIconText());
    }

    @Test
    void testGetIconUrl() {
        assertNotNull(iconSetting);
        assertEquals("/content/contact", iconSetting.getIconUrl());
    }

    @Test
    void testGetIconImage() {
        assertNotNull(iconSetting);
        assertEquals("/content/dam/mazda/logo.svg", iconSetting.getIconImage());
    }

}
