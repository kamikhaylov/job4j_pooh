package ru.job4j.pooh.service;

import ru.job4j.pooh.Req;
import ru.job4j.pooh.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String param = req.getParam();
        String source = req.getSourceName();
        if (req.httpRequestType().equals(Code.POST.getTitle())) {
            queue.putIfAbsent(source, new ConcurrentLinkedQueue<>());
            queue.get(source).add(param);
            return new Resp(param, Code.STATUS_200.getTitle());
        } else if (req.httpRequestType().equals(Code.GET.getTitle())) {
            if (queue.get(source) != null && !queue.get(source).isEmpty()) {
                return new Resp(queue.get(source).poll(), Code.STATUS_200.getTitle());
            }
        }
        return new Resp("", Code.STATUS_204.getTitle());
    }
}