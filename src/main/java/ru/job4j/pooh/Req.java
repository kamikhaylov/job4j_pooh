package ru.job4j.pooh;

import ru.job4j.pooh.service.Code;

public class Req {
    private static final String POST = "POST";
    private static final String GET = "GET";
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] contentSplit = content.split(System.lineSeparator());
        String[] firstSplit = contentSplit[0].split(" ");
        String[] paramsSplit = firstSplit[1].split("/");
        String httpRequestType = content.startsWith(Code.POST.getTitle()) ? Code.POST.getTitle() : Code.GET.getTitle();
        String poohMode = paramsSplit[1];
        String source = paramsSplit[2];
        String param;
            if (httpRequestType.equals(Code.POST.getTitle())) {
                param = contentSplit[contentSplit.length - 1];
            } else {
                param = paramsSplit.length > 3 ? paramsSplit[3] : "";
            }
        return new Req(httpRequestType, poohMode, source, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
