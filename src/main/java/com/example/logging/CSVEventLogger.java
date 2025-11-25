package com.jeopardy.logging;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;

public class CSVEventLogger implements EventListener {
    private final PrintWriter writer;
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    public CSVEventLogger(PrintWriter writer) {
        this.writer = writer;
        writer.println(
                "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play");
        writer.flush();
    }

    @Override
    public void onEvent(EventRecord event) {
        String line = toCsvLine(event);
        writer.println(line);
        writer.flush();
    }

    private String toCsvLine(EventRecord e) {
        String caseId = safe(e.getCaseId());
        String playerId = safe(e.getPlayerId());
        String activity = safe(e.getActivity());
        String timestamp = e.getTimestamp() == null ? "" : ISO.format(e.getTimestamp());
        String category = safe(e.getCategory());
        String qValue = e.getQuestionValue() == null ? "" : e.getQuestionValue().toString();
        String answer = safe(e.getAnswerGiven());
        String result = safe(e.getResult());
        String score = e.getScoreAfterPlay() == null ? "" : e.getScoreAfterPlay().toString();

        return String.join(",",
                csvEscape(caseId),
                csvEscape(playerId),
                csvEscape(activity),
                csvEscape(timestamp),
                csvEscape(category),
                csvEscape(qValue),
                csvEscape(answer),
                csvEscape(result),
                csvEscape(score));
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String csvEscape(String s) {
        if (s == null || s.isEmpty())
            return "";
        boolean needQuotes = s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r");
        if (needQuotes) {
            String replaced = s.replace("\"", "\"\"");
            return "\"" + replaced + "\"";
        }
        return s;
    }
}
