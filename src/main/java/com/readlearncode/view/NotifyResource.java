package com.readlearncode.view;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

import javax.faces.application.FacesMessage;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@PushEndpoint("/notify")
public class NotifyResource {

    @OnMessage(encoders = { JSONEncoder.class })
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }
}