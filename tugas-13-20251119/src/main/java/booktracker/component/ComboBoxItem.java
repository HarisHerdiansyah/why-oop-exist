package booktracker.component;

public class ComboBoxItem {
    private final String label;
    private final Object value;

    public ComboBoxItem(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }
}
