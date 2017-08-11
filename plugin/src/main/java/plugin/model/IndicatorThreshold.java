package plugin.model;

import java.util.List;
import java.util.Map;

public class IndicatorThreshold {
    public final static int THRESHOLD_10 = 0;
    public final static int THRESHOLD_20 = 1;
    public final static int THRESHOLD_30 = 2;
    public final static int THRESHOLD_40 = 3;
    public final static int THRESHOLD_50 = 4;
    public final static int THRESHOLD_60 = 5;
    public final static int THRESHOLD_70 = 6;
    public final static int THRESHOLD_80 = 7;
    public final static int THRESHOLD_90 = 8;
    public final static int THRESHOLD_100 = 9;

    private final Integer id;
    private final String severity;
    private final String name;
    private Double[] thresholds;

    public IndicatorThreshold(Integer id, String severity, String name) {
        this.id = id;
        this.severity = severity;
        this.name = name;
        this.thresholds = new Double[10];
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

    public Integer calculateLevel(Double value, Map<String, List<Integer>> borders) {
        List<Integer> bordersToUse = borders.get(severity);
        if (value == 0 || bordersToUse.get(0) < 0 || value <= thresholds[bordersToUse.get(0)]) {
            return 5;
        }
        if (bordersToUse.get(1) < 0 || value <= thresholds[bordersToUse.get(1)]) {
            return 4;
        }
        if (bordersToUse.get(2) < 0 || value <= thresholds[bordersToUse.get(2)]) {
            return 3;
        }
        if (bordersToUse.get(3) < 0 || value <= thresholds[bordersToUse.get(3)]) {
            return 2;
        }
        return 1;
    }

    public String getPositioning(Double value) {
        if (value <= thresholds[THRESHOLD_100]) {
            return "Better or as good as the best project";
        }
        if (value <= thresholds[THRESHOLD_90]) {
            return "Better than 90% of the projects";
        }
        if (value <= thresholds[THRESHOLD_80]) {
            return "Better than 80% of the projects";
        }
        if (value <= thresholds[THRESHOLD_70]) {
            return "Better than 70% of the projects";
        }
        if (value <= thresholds[THRESHOLD_60]) {
            return "Better than 60% of the projects";
        }
        if (value <= thresholds[THRESHOLD_50]) {
            return "Better than 50% of the projects";
        }
        if (value <= thresholds[THRESHOLD_40]) {
            return "Better than 40% of the projects";
        }
        if (value <= thresholds[THRESHOLD_30]) {
            return "Better than 30% of the projects";
        }
        if (value <= thresholds[THRESHOLD_20]) {
            return "Better than 20% of the projects";
        }
        if (value <= thresholds[THRESHOLD_10]) {
            return "Better than 10% of the projects";
        }
        return "Worse than 10% of the projects";
    }
}
