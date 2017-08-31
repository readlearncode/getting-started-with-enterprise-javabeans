package com.readlearncode.view;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */

import com.readlearncode.model.NotificationEvent.NotificationEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.push.EventBusFactory;

import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class NotifyView {

    private final static String CHANNEL = "/notify";

    public void initialize() {}

    public void send(@Observes NotificationEvent notificationEvent) {
        if(notificationEvent != null) {
            EventBusFactory.getDefault().eventBus().publish(CHANNEL,
                    new FacesMessage(StringEscapeUtils.escapeHtml3(notificationEvent.getTitle()),
                            StringEscapeUtils.escapeHtml3(notificationEvent.getMessage())));
        }
    }

}