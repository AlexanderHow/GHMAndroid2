package com.td.fr.unice.polytech.ghmandroid.NF;

import android.app.Application;
import android.content.Context;

public class ContextAndRoleHolder {
    private Context context;
    private Integer idRole;
    private Application app;
    private String textMessage;

    public ContextAndRoleHolder(Context context, Integer idRole, Application app, String textMessage) {
        this.context = context;
        this.idRole = idRole;
        this.app = app;
        this.textMessage=textMessage;
    }

    public Context getContextFromHolder() {
        return context;
    }

    public Integer getIdRoleFromHolder() {
        return idRole;
    }

    public Application getApp() {
        return app;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
