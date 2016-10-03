package com.meizu.voiceassistant.util;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by weichangtan on 15/9/18.
 */
public class JsonParser {

    public static IatResult parseLocalGrammarResult(String json) {
        IatResult iatResult = new IatResult();
        StringBuilder sb = new StringBuilder();

        try {

            if (json==null){
                iatResult.setHasNLP(false);
                iatResult.setResult(null);
                return iatResult;
            }

            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);


            //用户命令
            if (joResult.has("rc")) {
                iatResult.setHasNLP(true);
                iatResult.setResult(joResult.getString("text"));
            } else {
                iatResult.setHasNLP(false);
                JSONArray words = joResult.getJSONArray("ws");

                for (int i = 0; i < words.length(); i++) {
                    JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                    for (int j = 0; j < items.length(); j++) {
                        JSONObject obj = items.getJSONObject(j);
                        if (obj.getString("w").contains("nomatch")) {
                            iatResult.setResult("没有识别结果");
                        }
                        sb.append(obj.getString("w"));
                    }
                }
                iatResult.setResult(sb.toString());
//                return sb.toString();
            }


            //讯飞语义抽取
            if (joResult.has("rc") && joResult.getString("rc").contains("0")) {

                String typename=null;
                if (joResult.getString("service").contains("baike")) {
                    JSONObject baike = joResult.getJSONObject("answer");
                    iatResult.setNLPanswer(baike.getString("text"));
                    iatResult.setNLPtype(ListData.answer);
                    iatResult.setNLPname("use_baike");
                }
                if (joResult.getString("service").contains("faq")) {
                    JSONObject faq = joResult.getJSONObject("answer");
                    iatResult.setNLPanswer(faq.getString("text"));
                    iatResult.setNLPtype(ListData.answer);
                    iatResult.setNLPname("use_faq");
                }
                if (joResult.getString("service").contains("calc")) {
                    JSONObject calc = joResult.getJSONObject("answer");
                    iatResult.setNLPanswer(calc.getString("text"));
                    iatResult.setNLPtype(ListData.answer);
                    iatResult.setNLPname("use_calc");
                }
                if (joResult.getString("service").contains("datetime")) {
                    JSONObject datetime = joResult.getJSONObject("answer");
                    iatResult.setNLPanswer(datetime.getString("text"));
                    iatResult.setNLPtype(ListData.answer);
                    iatResult.setNLPname("use_datetime");
                }
                if (joResult.getString("service").contains("stock")) {
                    JSONObject semantic = joResult.getJSONObject("semantic");
                    JSONObject slots = semantic.getJSONObject("slots");
                    iatResult.setNLPanswer(slots.getString("category") + slots.getString("code"));
                    iatResult.setNLPtype(ListData.stock);
                    iatResult.setNLPname("use_stock");
                }
                if (joResult.getString("service").contains("translation")) {
                    JSONObject josemantic = joResult.getJSONObject("semantic");
                    JSONObject joslots = josemantic.getJSONObject("slots");
                    iatResult.setNLPanswer(joslots.getString("content"));
                    iatResult.setNLPtype(ListData.english);
                    iatResult.setNLPname("use_translation");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return iatResult;
    }


}
