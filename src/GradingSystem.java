public enum GradingSystem {
BINARY("binary"), PARTIALLY("partially"), PARTIALLY_WITH_NEGATIVE_POINTS("paritally with negative points");
private final String name;

    GradingSystem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
