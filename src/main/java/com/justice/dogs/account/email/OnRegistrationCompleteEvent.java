package com.justice.dogs.account.email;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.justice.dogs.account.user.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent { 
    private String appUrl;
    private Locale locale;
    private User user;

    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }
    
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
