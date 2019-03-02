package ua.training.controller.util.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.training.controller.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The util designed to work with localized messages from resource bundle.
 * @author Oleksii Shevchenko
 */
@Component
public class ContentManager {
    private LocaleUtil localeUtil;
    private Map<Locale, ResourceBundle> bundleMap;

    @Autowired
    @Qualifier("contentBundlesMap")
    public void setBundleMap(Map<Locale, ResourceBundle> bundleMap) {
        this.bundleMap = bundleMap;
    }

    @Autowired
    public void setLocaleUtil(LocaleUtil localeUtil) {
        this.localeUtil = localeUtil;
    }

    public String getLocalizedContent(String key, Locale locale) {
        ResourceBundle bundle = bundleMap.get(locale);
        return bundle.containsKey(key) ? bundle.getString(key) : "";
    }

    public void setLocalizedMessage(HttpServletRequest request, String attributeKey, String messageKey) {
        request.setAttribute(attributeKey, getLocalizedContent(messageKey,  localeUtil.getLocale(request)));
    }
}
