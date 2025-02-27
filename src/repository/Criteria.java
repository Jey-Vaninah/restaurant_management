package repository;

public class Criteria {
    private final String column;
    private final Object value;

    public Object getValue() {
        return value;
    }

    public String getColumn() {
        return column;
    }

    public Criteria(String column, Object value) {
        this.column = column;
        this.value = value;
    }
}

//operateur > < =
//operateur logique and na or