package com.meizu.voiceassistant.util;

/**
 * Created by weichangtan on 16/9/8.
 */
public class IatResult {
    private boolean hasNLP;
    private String result;
    private String NLP;
    private String NLPanswer;
    private int NLPtype;
    private String NLPname;


    public boolean isHasNLP() {
        return hasNLP;
    }

    public void setHasNLP(boolean hasNLP) {
        this.hasNLP = hasNLP;
    }

    public String getNLP() {
        return NLP;
    }

    public void setNLP(String NLP) {
        this.NLP = NLP;
    }

    public String getNLPanswer() {
        return NLPanswer;
    }

    public void setNLPanswer(String NLPanswer) {
        this.NLPanswer = NLPanswer;
    }

    public String getNLPname() {
        return NLPname;
    }

    public void setNLPname(String NLPname) {
        this.NLPname = NLPname;
    }

    public int getNLPtype() {
        return NLPtype;
    }

    public void setNLPtype(int NLPtype) {
        this.NLPtype = NLPtype;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
