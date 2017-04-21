package com.abel.hwes;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private static ThreadLocal<AppContext> appContextMap = new ThreadLocal<AppContext>();
    private Map<String, Object> Objects = new HashMap<String, Object>();
    private static String contextPath;

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String contextPath) {
        if (AppContext.contextPath == null) {
            AppContext.contextPath = contextPath;
        }
    }

    private AppContext() {

    }

    public static AppContext getAppContext() {
        AppContext context = appContextMap.get();
        if (context == null) {
            context = new AppContext();
            appContextMap.set(context);
        }
        return appContextMap.get();
    }

    public Map<String, Object> getObjects() {
        return Objects;
    }

    public void setObjects(Map<String, Object> Objects) {
        if (Objects == null) {
            Objects = new HashMap<String, Object>();
        }
        this.Objects = Objects;
    }

    public void clear() {
        AppContext context = appContextMap.get();
        if (context != null) {
            context.Objects.clear();
        }
        context = null;
    }

    public void addObject(String key, Object value) {
        Objects.put(key, value);
    }

    public Object getObject(String key) {
        return Objects.get(key);
    }

    public void removeObject(String key) {
        Objects.remove(key);
    }
}
