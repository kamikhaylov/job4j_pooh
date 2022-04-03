package ru.job4j.pooh.service;

import ru.job4j.pooh.Req;
import ru.job4j.pooh.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        String sourse = req.getSourceName();
        String param = req.getParam();
        if ((req.httpRequestType().equals(Code.POST.getTitle()) && topics.get(sourse) != null)) {
            topics.get(sourse).values().forEach(name -> name.offer(param));
            return new Resp(param, Code.STATUS_200.getTitle());
        } else if (req.httpRequestType().equals(Code.GET.getTitle())) {
            topics.putIfAbsent(sourse, new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(param, new ConcurrentLinkedQueue<>());
            String text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            return text == null ? new Resp("",Code.STATUS_204.getTitle()) : new Resp(text, Code.STATUS_200.getTitle());
        }
        return new Resp("",  Code.STATUS_204.getTitle());
    }
}