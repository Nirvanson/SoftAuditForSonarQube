package plugin.model;

public class IndicatorThreshold {
    public final static int THRESHOLD_25 = 0;
    public final static int THRESHOLD_50 = 1;
    public final static int THRESHOLD_75 = 2;
    public final static int THRESHOLD_90 = 3;
    public final static int THRESHOLD_100 = 4;

    private final Integer id;
    private final String severity;
    private final String name;
    private Double[] thresholds;

    public IndicatorThreshold(Integer id, String severity, String name) {
        this.id = id;
        this.severity = severity;
        this.name = name;
        this.thresholds = new Double[5];
    }

    public Integer getId() {
        return this.id;
    }

    public String getSeverity() {
        return this.severity;
    }

    public String getName() {
        return this.name;
    }

    public void setThreshold(int position, Double threshold) {
        this.thresholds[position] = threshold;
    }

    public Integer calculateLevel(Double value) {
        if (value == 0) {
            return 5;
        }
        switch (severity) {
            case "Blocker":
                return calculateLevelForBlocker(value);
            case "Critical":
                return calculateLevelForCritical(value);
            case "Major":
                return calculateLevelForMajor(value);
            case "Minor":
                return calculateLevelForMinor(value);
            default:
                return calculateLevelForInfo(value);
        }
    }

    public String getPositioning(Double value) {
        if (value <= thresholds[THRESHOLD_100]) {
            return "Better or as good as the best project";
        }
        if (value <= thresholds[THRESHOLD_90]) {
            return "Better than 90% of the projects";
        }
        if (value <= thresholds[THRESHOLD_75]) {
            return "Better than 75% of the projects";
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return "Better than 50% of the projects";
        }
        if (value <= thresholds[THRESHOLD_25]) {
            return "Better than 25% of the projects";
        }
        return "Worse than 75% of the projects";
    }

    private Integer calculateLevelForBlocker(Double value) {
        if (value <= thresholds[THRESHOLD_100]) {
            return 5;
        }
        if (value <= thresholds[THRESHOLD_90]) {
            return 4;
        }
        if (value <= thresholds[THRESHOLD_75]) {
            return 3;
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return 2;
        }
        return 1;
    }

    private Integer calculateLevelForCritical(Double value) {
        if (value <= thresholds[THRESHOLD_100]) {
            return 5;
        }
        if (value <= thresholds[THRESHOLD_75]) {
            return 4;
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return 3;
        }
        if (value <= thresholds[THRESHOLD_25]) {
            return 2;
        }
        return 1;
    }

    private Integer calculateLevelForMajor(Double value) {
        if (value <= thresholds[THRESHOLD_90]) {
            return 5;
        }
        if (value <= thresholds[THRESHOLD_75]) {
            return 4;
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return 3;
        }
        return 2;
    }

    private Integer calculateLevelForMinor(Double value) {
        if (value <= thresholds[THRESHOLD_90]) {
            return 5;
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return 4;
        }
        if (value <= thresholds[THRESHOLD_25]) {
            return 3;
        }
        return 2;
    }

    private Integer calculateLevelForInfo(Double value) {
        if (value <= thresholds[THRESHOLD_90]) {
            return 5;
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return 4;
        }
        return 3;
    }
}
