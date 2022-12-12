package input;

public class ActionsInput {
    private String type;
    private String page;
    private String feature;
    private String movie;
    private CredentialsInput credentials;
    
    public ActionsInput() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsInput credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "ActionsInput [type=" + type + ", page=" + page + ", feature=" + feature + ", credentials=" + credentials
                + "]";
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    
}
