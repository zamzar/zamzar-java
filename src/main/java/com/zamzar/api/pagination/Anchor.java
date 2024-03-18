package com.zamzar.api.pagination;

/**
 * Represents a reference to a position in a paginated list of resources.
 *
 * @param <T> the type of identifier used for items in the list (e.g., Integer or String)
 */
public class Anchor<T> {

    protected final T ref;
    protected final Orientation orientation;

    public static <T> Anchor<T> after(T ref) {
        return new Anchor<>(ref, Orientation.AFTER);
    }

    public static <T> Anchor<T> before(T ref) {
        return new Anchor<>(ref, Orientation.BEFORE);
    }

    protected Anchor(T ref, Orientation orientation) {
        this.ref = ref;
        this.orientation = orientation;
    }

    public T getAfterParameterValue() {
        return orientation == Orientation.AFTER ? ref : null;
    }

    public T getBeforeParameterValue() {
        return orientation == Orientation.BEFORE ? ref : null;
    }

    @Override
    public String toString() {
        if (orientation == Orientation.AFTER) {
            return "after " + ref;
        } else {
            return "before " + ref;
        }
    }

    public enum Orientation {
        AFTER,
        BEFORE;
    }

}