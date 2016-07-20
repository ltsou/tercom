/**
 * LGPL License
 * <p>
 * TERtest.java v1
 * Matthew Snover (snover@cs.umd.edu)
 * <p>
 * TERtest.java v2, added SGML input support.
 * Shuguang Wang (swang@bbn.com)
 */
package com.org.ter;

import java.util.Map;

import com.org.ter.core.CostFunction;
import com.org.ter.core.TerScorer;
import com.org.ter.core.Alignment;
import com.org.ter.Parameters.OPTIONS;

public class TER {
    TerScorer calc;
    CostFunction costfunc;

    public TER() {
        this(new String[0]);
    }

    public TER(String[] args) {
        // 1. process arguments
        Parameters para = new Parameters();
        // paras include Boolean, Double, and Integer
        Map<OPTIONS, Object> paras = para.getOpts(args);
        Object val = paras.get(Parameters.OPTIONS.NORMALIZE);
        boolean normalized = (Boolean) val;
        val = paras.get(Parameters.OPTIONS.CASEON);
        boolean caseon = (Boolean) val;
        val = paras.get(Parameters.OPTIONS.NOPUNCTUATION);
        boolean nopunct = (Boolean) val;
        val = paras.get(Parameters.OPTIONS.BEAMWIDTH);
        int beam_width = (Integer) val;
        val = paras.get(Parameters.OPTIONS.SHIFTDIST);
        int shift_dist = (Integer) val;

        this.costfunc = new CostFunction();
        this.costfunc._delete_cost = (Double) paras.get(Parameters.OPTIONS.DELETE_COST);
        this.costfunc._insert_cost = (Double) paras.get(Parameters.OPTIONS.INSERT_COST);
        this.costfunc._shift_cost = (Double) paras.get(Parameters.OPTIONS.SHIFT_COST);
        this.costfunc._match_cost = (Double) paras.get(Parameters.OPTIONS.MATCH_COST);
        this.costfunc._substitute_cost = (Double) paras.get(Parameters.OPTIONS.SUBSTITUTE_COST);

        // set options to compute TER
        this.calc = new TerScorer();
        this.calc.setNormalize(normalized);
        this.calc.setCase(caseon);
        this.calc.setPunct(nopunct);
        this.calc.setBeamWidth(beam_width);
        this.calc.setShiftDist(shift_dist);

    }

    public Alignment computeTER(String ref, String hyp) {
        return calc.TER(hyp, ref, costfunc);
    }
}
