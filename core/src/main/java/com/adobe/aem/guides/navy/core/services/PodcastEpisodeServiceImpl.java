package com.adobe.aem.guides.navy.core.services;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import com.adobe.aem.guides.navy.core.models.PodcastEpisodeModel;
import com.day.cq.i18n.I18n;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = PodcastEpisodeService.class)
public class PodcastEpisodeServiceImpl implements PodcastEpisodeService {
    private static final String PODCAST_TOPICS_ID_TAG = "topics:";
    private static final String PODCAST_TOPIC_FILTER = "topic";
    private static final String PODCAST_TOPIC_PARAMETER_NAME = "topic:";
    private static final String PODCAST_MONTH_FILTER = "month";
    private static final String PODCAST_YEAR_FILTER = "year";
    private static final String PODCAST_DATE_FILTER = "date";
    private static final String TOPICS_TITLE = "Topics";
    private static final String YEARS_TITLE = "Years";
    private static final String MONTHS_TITLE = "Months";
    private static final String DATE_TITLE = "Date";
    private Set<String> months;
    private Set<String> years;
    private Set<String> topics;
    private String orderEpisodes;

    @Override

    public List<PodcastEpisodeModel> getEpisodesByCategory(ResourceResolver resourceResolver, String pathNameParam,
            PodcastSearchCriteria criteria, PodcastSearchBuilder queryBuilder) throws MerckException {

        int offsetResults = criteria.getLimit() * criteria.getCurrentPageIndex();
        List<PodcastEpisodeModel> podcastsList = queryBuilder.getEpisodesListByCategory(resourceResolver, pathNameParam,
                criteria.getCategoryFilter(), criteria.getLocale(), 0, offsetResults, criteria.isExternalLink());

        orderPodcastsList(podcastsList, orderEpisodes);

        return podcastsList;
    }

    public void orderPodcastsList(List<PodcastEpisodeModel> podcastsList, String orderEpisodes) {

        switch (orderEpisodes) {
            case ORDER_OLDEST_PUBLISHED_DATE:
                podcastsList.sort((p1, p2) -> ComparisonChain.start()
                        .compare(p1.getPublishedDate(), p2.getPublishedDate(), Ordering.natural().nullsLast())
                        .result());
                break;

            case ORDER_EPISODES_DESC:
                podcastsList.sort((p1, p2) -> ComparisonChain.start()
                        .compare(p1.getEpisodeNumber(), p2.getEpisodeNumber(), Ordering.natural().reverse().nullsLast())
                        .result());
                break;

            case ORDER_EPISODES_ASC:
                podcastsList.sort((p1, p2) -> ComparisonChain.start()
                        .compare(p1.getEpisodeNumber(), p2.getEpisodeNumber(), Ordering.natural().nullsLast())
                        .result());
                break;

            default:
                break;
        }
    }

    @Override
    public Object[] extractFiltersFromEpisodeList(TagManager tagManager, I18n i18n, Locale locale,
            List<PodcastEpisodeModel> episodesList, Map<String, Set<String>> selectedFilters) {
        extractFiltersFromList(episodesList);

        List<PodcastSearchFilterParent> podcastFilters = buildFilters(tagManager, i18n, locale,
                selectedFilters.get(PODCAST_TOPIC_FILTER), selectedFilters.get(PODCAST_YEAR_FILTER),
                selectedFilters.get(PODCAST_MONTH_FILTER));

        PodcastSearchFilterParent parentDate = new PodcastSearchFilterParent.Builder()
                .id(PODCAST_DATE_FILTER)
                .title(DATE_TITLE)
                .parameterName(PODCAST_DATE_FILTER).build();

        Set<PodcastSearchFilter> childParentSearchFilterSet = parentDate.getPodcastSearchFilters();
        List<PodcastSearchFilterParent> allFilters = new ArrayList<>();

        for (PodcastSearchFilterParent par : podcastFilters) {
            if (par.getTitle().equals(YEARS_TITLE) || par.getTitle().equals(MONTHS_TITLE)) {
                PodcastSearchFilter childFilter = new PodcastSearchFilter.Builder()
                        .value(par.getId())
                        .label(par.getTitle())
                        .podcastSearchFilters(par.getPodcastSearchFilters()).build();
                childParentSearchFilterSet.add(childFilter);
            } else {
                allFilters.add(par);
            }
        }

        allFilters.add(parentDate);

        return allFilters.toArray();
    }

    private void extractFiltersFromList(List<PodcastEpisodeModel> podcastsList) {
        months = new HashSet<>();
        years = new HashSet<>();
        topics = new HashSet<>();

        for (PodcastEpisodeModel episode : podcastsList) {
            if (episode.getTagsPodcast() != null && !episode.getTagsPodcast().isEmpty()) {
                for (Map<String, String> tag : episode.getTagsPodcast()) {
                    if (tag.get(PODCAST_TAG_ID).startsWith(PODCAST_TOPICS_ID_TAG)) {
                        topics.add(tag.get(PODCAST_TAG_ID));
                    }
                }
            }

            if (episode.getPublishedDate() != null && episode.getMonth() != null && episode.getYear() != null) {
                months.add(episode.getMonth().getTagId());
                years.add(episode.getYear().getTagId());
            }
        }
    }

    private List<PodcastSearchFilterParent> buildFilters(TagManager tagManager, I18n i18n, Locale locale,
            Set<String> selectedTopicsFilter, Set<String> selectedYearsFilter, Set<String> selectedMonthsFilter) {
        Set<PodcastSearchFilterParent> searchFilters = new TreeSet<>();

        if (!topics.isEmpty()) {
            for (String topic : topics) {
                Tag resolvedTag = tagManager.resolve(topic);
                addTopicsFilter(searchFilters, selectedTopicsFilter, resolvedTag, locale);
            }
        }

        if (!years.isEmpty()) {
            for (String year : years) {
                addYearsFilter(searchFilters, selectedYearsFilter, year);
            }
        }

        if (!months.isEmpty()) {
            for (String month : months) {
                addMonthsFilter(searchFilters, selectedMonthsFilter, i18n, locale, month);
            }
        }

        return searchFilters.stream().collect(Collectors.toList());
    }

    private void addTopicsFilter(Set<PodcastSearchFilterParent> searchFilters, Set<String> selectedFilters,
            Tag resolvedTag, Locale locale) {
        final PodcastSearchFilterParent parentFilter = new PodcastSearchFilterParent.Builder()
                .id(PODCAST_TOPIC_PARAMETER_NAME)
                .title(TOPICS_TITLE)
                .parameterName(PODCAST_TOPIC_FILTER).build();
        boolean selected = selectedFilters.contains(resolvedTag.getTagID());

        PodcastSearchFilter childFilter = new PodcastSearchFilter.Builder()
                .value(resolvedTag.getTagID())
                .label(resolvedTag.getTitle(locale))
                .selected(selected).build();

        addFilter(searchFilters, parentFilter, childFilter);
    }

    private void addMonthsFilter(Set<PodcastSearchFilterParent> searchFilters, Set<String> selectedFilters, I18n i18n,
            Locale locale, String monthID) {
        final PodcastSearchFilterParent parentFilter = new PodcastSearchFilterParent.Builder()
                .id(PODCAST_MONTH_FILTER)
                .title(MONTHS_TITLE)
                .parameterName(PODCAST_MONTH_FILTER).build();
        boolean selected = selectedFilters.contains(monthID);

        String labelMonth = DateCategory.displayMonthName(Month.of(Integer.parseInt(monthID)), i18n, locale);

        PodcastSearchFilter childFilter = new PodcastSearchFilter.Builder()
                .value(monthID)
                .label(labelMonth)
                .selected(selected).build();

        addFilter(searchFilters, parentFilter, childFilter);
    }

    private void addYearsFilter(Set<PodcastSearchFilterParent> searchFilters, Set<String> selectedFilters,
            String yearID) {
        final PodcastSearchFilterParent parentFilter = new PodcastSearchFilterParent.Builder()
                .id(PODCAST_YEAR_FILTER)
                .title(YEARS_TITLE)
                .parameterName(PODCAST_YEAR_FILTER).build();
        boolean selected = selectedFilters.contains(yearID);

        PodcastSearchFilter childFilter = new PodcastSearchFilter.Builder()
                .value(yearID)
                .label(yearID)
                .selected(selected).build();

        addFilter(searchFilters, parentFilter, childFilter);
    }

    /**
     * Function to add a parent filter into a general search filter set
     * 
     * @param searchFilters
     * @param parentFilter
     * @param childFilter
     */
    private void addFilter(Set<PodcastSearchFilterParent> searchFilters, PodcastSearchFilterParent parentFilter,
            PodcastSearchFilter childFilter) {

        if (searchFilters.contains(parentFilter)) {
            Optional<PodcastSearchFilterParent> podcastSearchFilterParent = searchFilters.stream()
                    .filter(childItem -> childItem.equals(parentFilter)).findFirst();
            Set<PodcastSearchFilter> childSearchFilterSet = podcastSearchFilterParent.isPresent()
                    ? podcastSearchFilterParent.get().getPodcastSearchFilters()
                    : null;

            if (null != childSearchFilterSet && !childSearchFilterSet.contains(childFilter)) {
                childSearchFilterSet.add(childFilter);
            }
        } else {
            Set<PodcastSearchFilter> childSearchFilterSet = parentFilter.getPodcastSearchFilters();
            childSearchFilterSet.add(childFilter);
            searchFilters.add(parentFilter);
        }
    }

    @Override
    public List<PodcastEpisodeModel> filterByParameters(List<PodcastEpisodeModel> podcastsList,
            Map<String, Set<String>> selectedFilters) {
        List<PodcastEpisodeModel> result = podcastsList;

        if (null != selectedFilters) {
            Set<String> topicsFilter = selectedFilters.get(PODCAST_TOPIC_FILTER);
            if (null != topicsFilter && !topicsFilter.isEmpty()) {
                result = result.stream()
                        .filter(podcast -> podcast.getTagsPodcast().stream()
                                .anyMatch(tag -> topicsFilter.contains(tag.get(PODCAST_TAG_ID))))
                        .collect(Collectors.toList());
            }

            Set<String> yearsFilter = selectedFilters.get(PODCAST_YEAR_FILTER);
            if (null != yearsFilter && !yearsFilter.isEmpty()) {
                result = result.stream()
                        .filter(podcast -> podcast.getPublishedDate() != null && podcast.getYear() != null
                                && yearsFilter.contains(podcast.getYear().getTagId()))
                        .collect(Collectors.toList());
            }

            Set<String> monthsFilter = selectedFilters.get(PODCAST_MONTH_FILTER);
            if (null != monthsFilter && !monthsFilter.isEmpty()) {
                result = result.stream()
                        .filter(podcast -> podcast.getPublishedDate() != null && podcast.getMonth() != null
                                && monthsFilter.contains(podcast.getMonth().getTagId()))
                        .collect(Collectors.toList());
            }
        }

        return result;
    }
}
