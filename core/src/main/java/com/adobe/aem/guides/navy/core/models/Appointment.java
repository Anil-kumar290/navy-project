package com.adobe.aem.guides.navy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Appointment {

    @ValueMapValue
    private String uniqueRefLabel;

    @ValueMapValue
    private String placeholder;

    @ValueMapValue
    private String buttonLabel;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String richtext;

    @ValueMapValue
    private String dateTitle2;

    @ValueMapValue
    private String dateInfo2;

    @ValueMapValue
    private String message2;

    @ValueMapValue
    private String name2;

    @ValueMapValue
    private String selectdate2;

    @ValueMapValue
    private String icon2;

    @ValueMapValue
    private String richtext3;

    @ValueMapValue
    private String appointmentTitle;

    @ValueMapValue
    private String appointmentDate;

    @ValueMapValue
    private String appointmentTime;

    @ValueMapValue
    private String appointmentReference;

    @ValueMapValue
    private String appointmentMessage;

    @ValueMapValue
    private String icon3;

    @ValueMapValue
    private String icon4;

    @ValueMapValue
    private String richtext4;

    @ValueMapValue
    private String dateTitle;

    @ValueMapValue
    private String dateInfo;

    @ValueMapValue
    private String selectdate;

    @ValueMapValue
    private String message;

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String icon;

    @ValueMapValue
    private String image3;

    @ValueMapValue
    private String image4;

    @ValueMapValue
    private String richtext2;

    @ValueMapValue
    private String errorTitle;

    @ValueMapValue
    private String retryButtonText;

    @ValueMapValue
    private String errorDescription;

    @ValueMapValue
    private String image7;

    public String getUniqueRefLabel() {
        return uniqueRefLabel;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getRichtext() {
        return richtext;
    }

    public String getDateTitle2() {
        return dateTitle2;
    }

    public String getDateInfo2() {
        return dateInfo2;
    }

    public String getMessage2() {
        return message2;
    }

    public String getName2() {
        return name2;
    }

    public String getSelectdate2() {
        return selectdate2;
    }

    public String getIcon2() {
        return icon2;
    }

    public String getRichtext3() {
        return richtext3;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getAppointmentReference() {
        return appointmentReference;
    }

    public String getAppointmentMessage() {
        return appointmentMessage;
    }

    public String getIcon3() {
        return icon3;
    }

    public String getIcon4() {
        return icon4;
    }

    public String getRichtext4() {
        return richtext4;
    }

    public String getDateTitle() {
        return dateTitle;
    }

    public String getDateInfo() {
        return dateInfo;
    }

    public String getSelectdate() {
        return selectdate;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }

    public String getRichtext2() {
        return richtext2;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getRetryButtonText() {
        return retryButtonText;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getImage7() {
        return image7;
    }
}
