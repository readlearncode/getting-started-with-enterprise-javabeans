package com.readlearncode.service;

import com.readlearncode.model.NotificationEvent.NotificationEvent;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Singleton
public class NotificationService {

    @Inject
    private Event<NotificationEvent> events;

    List<NotificationEvent> notificationEvents = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        notificationEvents.add(new NotificationEvent("Sale Now On!", "25% discount on Java books"));
        notificationEvents.add(new NotificationEvent("Black Friday Deals", "1000's discounts on books"));
        notificationEvents.add(new NotificationEvent("clearance Sale", "Everything must go!"));
    }

    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void doWork() {
        events.fire(notificationEvents.get(new Random().nextInt(notificationEvents.size())));
    }

}