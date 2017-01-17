package plugin.analyser;

import java.util.HashMap;
import java.util.Map;

import org.sonar.api.measures.Metric;

import plugin.SoftAuditMetrics;

public class MetricCalculator {

    public static Map<Metric<?>, Double> calculate(Map<Metric<?>, Double> measures) {
        Map<Metric<?>, Double> result = new HashMap<Metric<?>, Double>();
        result.put(SoftAuditMetrics.OBP,
                (measures.get(SoftAuditMetrics.CLA) * 4) + (measures.get(SoftAuditMetrics.MET) * 3)
                        + (measures.get(SoftAuditMetrics.INT) * 2) + measures.get(SoftAuditMetrics.VAR));
        result.put(SoftAuditMetrics.DCO,
                ((measures.get(SoftAuditMetrics.PRE) * 2)
                        + measures.get(SoftAuditMetrics.ARG) + (measures.get(SoftAuditMetrics.PAR) * 0.5))
                        / (measures.get(SoftAuditMetrics.STM) + measures.get(SoftAuditMetrics.REF)));
        result.put(SoftAuditMetrics.DFC,
                1 - ((measures.get(SoftAuditMetrics.VAR) * 2) / measures.get(SoftAuditMetrics.REF)));
        result.put(SoftAuditMetrics.ICO, 
                measures.get(SoftAuditMetrics.FFC) / measures.get(SoftAuditMetrics.FUC));
        result.put(SoftAuditMetrics.CFC,
                (measures.get(SoftAuditMetrics.BRA)
                        - (measures.get(SoftAuditMetrics.IFS) + measures.get(SoftAuditMetrics.SWI)
                                + measures.get(SoftAuditMetrics.LOP) + measures.get(SoftAuditMetrics.RET)))
                        / measures.get(SoftAuditMetrics.BRA));
        result.put(SoftAuditMetrics.COC,
                (measures.get(SoftAuditMetrics.IFS) + measures.get(SoftAuditMetrics.SWI)
                        + measures.get(SoftAuditMetrics.CAS) + measures.get(SoftAuditMetrics.LOP) + 1)
                        / (measures.get(SoftAuditMetrics.MET) * 4));
        result.put(SoftAuditMetrics.BRC,
                ((measures.get(SoftAuditMetrics.FFC) * 2) + (measures.get(SoftAuditMetrics.RET) * 2)
                        + measures.get(SoftAuditMetrics.FUC)) / measures.get(SoftAuditMetrics.STM));
        result.put(SoftAuditMetrics.LCM,
                ((measures.get(SoftAuditMetrics.STY) / measures.get(SoftAuditMetrics.STM))
                        + (measures.get(SoftAuditMetrics.DTY)
                                / (measures.get(SoftAuditMetrics.VAR) + measures.get(SoftAuditMetrics.CON))))
                        / 2);
        result.put(SoftAuditMetrics.ACM,
                (result.get(SoftAuditMetrics.DCO) + result.get(SoftAuditMetrics.DFC) + result.get(SoftAuditMetrics.ICO)
                        + result.get(SoftAuditMetrics.CFC) + result.get(SoftAuditMetrics.COC)
                        + result.get(SoftAuditMetrics.BRC) + result.get(SoftAuditMetrics.LCM)) / 7);
        result.put(SoftAuditMetrics.MOD,
                ((((measures.get(SoftAuditMetrics.CLA) * 4) + (measures.get(SoftAuditMetrics.MET) * 2))
                        / ((measures.get(SoftAuditMetrics.IMP) * 4) + measures.get(SoftAuditMetrics.VAR)))
                        + (1 - (measures.get(SoftAuditMetrics.FFC)
                                / (measures.get(SoftAuditMetrics.FUC) + measures.get(SoftAuditMetrics.MET))))
                        + ((measures.get(SoftAuditMetrics.STM) / measures.get(SoftAuditMetrics.SRC))
                                / measures.get(SoftAuditMetrics.OMS)))
                        / 3);
        result.put(SoftAuditMetrics.TST,
                ((1 - ((measures.get(SoftAuditMetrics.BRA) * 2) / measures.get(SoftAuditMetrics.STM)))
                        + (1 - ((measures.get(SoftAuditMetrics.PRE) * 2) / measures.get(SoftAuditMetrics.REF)))) / 2);
        result.put(SoftAuditMetrics.REU, 
                measures.get(SoftAuditMetrics.RUM) / measures.get(SoftAuditMetrics.MET));
        result.put(SoftAuditMetrics.SEC,
                (measures.get(SoftAuditMetrics.SST) / 1.2) / measures.get(SoftAuditMetrics.STM));
        result.put(SoftAuditMetrics.FLE, 1 - ((measures.get(SoftAuditMetrics.LIT) + measures.get(SoftAuditMetrics.CON))
                / measures.get(SoftAuditMetrics.STM)));
        result.put(SoftAuditMetrics.COF,
                1 - (((measures.get(SoftAuditMetrics.SED) * 2) + (measures.get(SoftAuditMetrics.MAD) * 1.5)
                        + measures.get(SoftAuditMetrics.MED) + (measures.get(SoftAuditMetrics.MID) * 0.5))
                        / measures.get(SoftAuditMetrics.STM)));
        result.put(SoftAuditMetrics.MAM, ((1 - result.get(SoftAuditMetrics.ACM)) + ((result.get(SoftAuditMetrics.MOD)
                + result.get(SoftAuditMetrics.SEC) + result.get(SoftAuditMetrics.TST) + result.get(SoftAuditMetrics.REU)
                + result.get(SoftAuditMetrics.FLE) + (result.get(SoftAuditMetrics.COF) / 2)) / 6)) / 2);
        result.put(SoftAuditMetrics.AQM,
                (result.get(SoftAuditMetrics.MOD) + result.get(SoftAuditMetrics.TST) + result.get(SoftAuditMetrics.REU)
                        + result.get(SoftAuditMetrics.SEC) + result.get(SoftAuditMetrics.FLE)
                        + result.get(SoftAuditMetrics.COF) + result.get(SoftAuditMetrics.MAM)) / 7);
        return result;
    }

}
