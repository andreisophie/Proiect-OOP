package input;

public final class FiltersInput {
    private SortInput sort;
    private FilterContainsInput contains;

    public FiltersInput() {
    }

    public SortInput getSort() {
        return sort;
    }

    public void setSort(final SortInput sort) {
        this.sort = sort;
    }

    public FilterContainsInput getContains() {
        return contains;
    }

    public void setContains(final FilterContainsInput contains) {
        this.contains = contains;
    }
}
