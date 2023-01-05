package input;

public final class ActionsInput {
    private String type;
    private String page;
    private String feature;
    private String movie;
    private CredentialsInput credentials;
    private String startsWith;
    private FiltersInput filters;
    private String count;
    private String rate;
    private String subscribedGenre;
    private MovieInput addedMovie;
    private String deletedMovie;

    public ActionsInput() {
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public FiltersInput getFilters() {
        return filters;
    }

    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }

    public String getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = count;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(final String rate) {
        this.rate = rate;
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    public MovieInput getAddedMovie() {
        return addedMovie;
    }

    public void setAddedMovie(MovieInput addedMovie) {
        this.addedMovie = addedMovie;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public void setDeletedMovie(String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    @Override
    public String toString() {
        return "ActionsInput [type=" + type
            + ", page=" + page
            + ", feature=" + feature
            + ", credentials=" + credentials
            + "]";
    }
}
