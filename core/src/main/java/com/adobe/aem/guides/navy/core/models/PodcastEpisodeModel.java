package com.adobe.aem.guides.navy.core.models;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PodcastEpisodeModel {

    public static final Logger LOG = LoggerFactory.getLogger(PodcastEpisodeModel.class);
    public static final String PODCAST_TAG_ID = "id";
    public static final String PODCAST_TAG_TITLE = "title";
    @Inject
    private ResourceResolver resourceResolver;

    @Self
    private Resource resource;

    @Inject
    @Named("podcastTitle")
    private String episodeHeadline;

    @Inject
    @Named("podcastDescription")
    private String episodeDescription;

    @Inject
    @Named("podcastCategory")
    private String episodeCategoryTagID;

    @Inject
    @Named("duration")
    private String episodeDuration;

    @Inject
    @Named("podcastEpisode")
    private String episodeNumberTagID;

    @Inject
    @Named("audioFile")
    private String episodeAudioFile;

    @Inject
    @Named("publishedDate")
    private Calendar publishedDate;

    @Inject
    @Named("promotionalImage")
    private String episodePromotionalImage;

    @Inject
    @Named("cq:tags")
    private String[] tags;
    private List<Map<String, String>> tagsPodcast = new ArrayList<>();

    private Link link;

    private String episodeCategory;

    private String episodeNumber;

    private com.hugeinc.mkgaa.base.core.models.Tag month;
    private com.hugeinc.mkgaa.base.core.models.Tag year;

    @PostConstruct
    protected void init() {
        TagManager tagManager = resource.getResourceResolver().adaptTo(TagManager.class);
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Locale locale = Locales.language(pageManager.getContainingPage(resource));

        episodeNumber = episodeNumberTagID;
        episodeCategory = episodeCategoryTagID;

        setPublishedDateTags(locale);
        setTagsPodcast(tagManager, locale);
    }

    private void setPublishedDateTags(Locale locale) {
        if (null != this.publishedDate) {
            LocalDateTime publishedDateTime = LocalDateTime.ofInstant(this.publishedDate.toInstant(),
                    this.publishedDate.getTimeZone().toZoneId());

            this.month = new com.hugeinc.mkgaa.base.core.models.Tag(
                    Integer.toString(publishedDateTime.getMonth().getValue()),
                    publishedDateTime.getMonth().getDisplayName(TextStyle.FULL, locale));
            this.year = new com.hugeinc.mkgaa.base.core.models.Tag(Integer.toString(publishedDateTime.getYear()));
        }
    }

    private void setTagsPodcast(TagManager tagManager, Locale locale) {
        if (null != tags && tags.length > 0) {
            for (String tag : tags) {
                try {
                    Tag tagPodcast = tagManager.resolve(tag);
                    Map<String, String> map = new HashMap<>();
                    map.put(PODCAST_TAG_TITLE, tagPodcast.getTitle(locale));
                    map.put(PODCAST_TAG_ID, tagPodcast.getTagID());
                    tagsPodcast.add(map);
                } catch (Exception e) {
                    LOG.warn("Failed to resolve tag '{}' in PodcastEpisodeModel:{}", tag);
                }
            }
        }
    }

    public String getEpisodeHeadline() {
        return episodeHeadline;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public String getEpisodeCategoryTagID() {
        return episodeCategoryTagID;
    }

    public String getEpisodeCategory() {
        return episodeCategory;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeNumberTagID() {
        return episodeNumberTagID;
    }

    public String getEpisodeDuration() {
        return episodeDuration;
    }

    public String getEpisodeAudioFile() {
        return episodeAudioFile;
    }

    public Calendar getPublishedDate() {
        return publishedDate;
    }

    public Link getLink() {
        return link;
    }

    public String[] getTags() {
        return tags;
    }

    public String getEpisodePromotionalImage() {
        return episodePromotionalImage;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setEpisodeCategory(String episodeCategory) {
        this.episodeCategory = episodeCategory;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber.replace("Episode ", "");
    }

    public List<Map<String, String>> getTagsPodcast() {
        return tagsPodcast;
    }

    public com.hugeinc.mkgaa.base.core.models.Tag getMonth() {
        return this.month;
    }

    public com.hugeinc.mkgaa.base.core.models.Tag getYear() {
        return this.year;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof PodcastEpisodeModel))
            return false;

        PodcastEpisodeModel otherObject = (PodcastEpisodeModel) other;

        return otherObject.link.equals(this.link);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(173, 13).append(this.link).append(this.episodeHeadline).toHashCode();
    }
}
