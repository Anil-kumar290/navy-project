package com.adobe.aem.guides.navy.core.services;

import com.adobe.aem.guides.navy.core.models.PodcastEpisodeModel;
import com.day.cq.i18n.I18n;
import com.day.cq.tagging.TagManager;
import org.apache.sling.api.resource.ResourceResolver;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface PodcastEpisodeService {

    String CATEGORY_SERVICE_PARAMETER = "category";
    String PAGE_SERVICE_NUMBER_PARAMETER = "page";
    String LIMIT_SERVICE_NUMBER_PARAMETER = "limit";
    String LOCALE_SERVICE_PARAMETER = "locale";
    String PATHNAME_SERVICE_PARAMETER = "path";
    String YEAR_SERVICE_PARAMETER = "year";
    String MONTH_SERVICE_PARAMETER = "month";
    String TOPIC_SERVICE_PARAMETER = "topic";
    String ORDER_SERVICE_PARAM = com.day.cq.wcm.api.NameConstants.PN_ORDER;
    String ORDER_OLDEST_PUBLISHED_DATE = "oldest";
    String ORDER_NEWEST_PUBLISHED_DATE = "newest";
    String ORDER_EPISODES_DESC = "episodes_desc";
    String ORDER_EPISODES_ASC = "episodes_asc";

    /**
     * Function to get podcast episodes paged by category
     * 
     * @param resourceResolver
     * @param pathNameParam    Path name for searching episodes
     * @param categoryFilter   Category name for filter results
     * @param locale           language
     * @param limit            Limit number for results
     * @param currentPageIndex current page index. Used to calculate the offset
     *                         parameter
     * @param externalLink     boolean to adapt episode links into external links
     * @return List of PodcastEpisodeModel
     * @throws MerckException customized Merck exception
     */

    public class PodcastSearchCriteria {
        private String categoryFilter;
        private String orderEpisodes;
        private Locale locale;
        private int limit;
        private int currentPageIndex;
        private boolean externalLink;

        public String getCategoryFilter() {
            return categoryFilter;
        }

        public void setCategoryFilter(String categoryFilter) {
            this.categoryFilter = categoryFilter;
        }

        public String getOrderEpisodes() {
            return orderEpisodes;
        }

        public void setOrderEpisodes(String orderEpisodes) {
            this.orderEpisodes = orderEpisodes;
        }

        public Locale getLocale() {
            return locale;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getCurrentPageIndex() {
            return currentPageIndex;
        }

        public void setCurrentPageIndex(int currentPageIndex) {
            this.currentPageIndex = currentPageIndex;
        }

        public boolean isExternalLink() {
            return externalLink;
        }

        public void setExternalLink(boolean externalLink) {
            this.externalLink = externalLink;
        }

    }

    public List<PodcastEpisodeModel> getEpisodesByCategory(ResourceResolver resourceResolver, String pathNameParam,
            PodcastSearchCriteria criteria, PodcastSearchBuilder queryBuilder) throws MerckException;

    /**
     * Function to filter list by parameters selected.
     *
     * @param podcastsList List of PodcastEpisodeModel
     * @param Map<String,  Set<String>> selected filters
     * @return List filtered of PodcastEpisodeModel
     */
    List<PodcastEpisodeModel> filterByParameters(List<PodcastEpisodeModel> podcastsList,
            Map<String, Set<String>> selectedFilters);

    /**
     * Function to extract filters from an episode list
     *
     * @param tagManager                Tag manager for handling tags
     * @param i18n                      internationalization
     * @param locale                    language
     * @param List<PodcastEpisodeModel> episode list
     * @param Map<String,               Set<String>> selected filters
     * @return
     */
    Object[] extractFiltersFromEpisodeList(TagManager tagManager, I18n i18n, Locale locale,
            List<PodcastEpisodeModel> episodesList, Map<String, Set<String>> selectedFilters);

}
