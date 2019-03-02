package ua.training.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.training.controller.util.managers.ContentManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Util used for validation user inputs.
 * @author Oleksii Shevchenko
 */
@Component
public class ValidationUtil {
    private ContentManager contentManager;
    private LocaleUtil localeUtil;

    @Autowired
    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Autowired
    public void setLocaleUtil(LocaleUtil localeUtil) {
        this.localeUtil = localeUtil;
    }

    /**
     * Method validates user inputs listed in {@code params} and contained in {@code request} using regex from resource
     * bundle. If the the input is not valid the localized message setted into request about it.
     * @param request The http request containing the inputs.
     * @param params List of inputs to validate.
     * @return Is all inputs are valid.
     */
    public boolean makeValidation(HttpServletRequest request, List<String> params) {
        boolean flag = true;
        for (String param : params) {
            flag &= (!isEmpty(request, param) && isValid(request, param));
        }
        return flag;
    }


    private boolean isValid(HttpServletRequest request, String param) {
        ResourceBundle bundle = ResourceBundle.getBundle("regex", localeUtil.getLocale(request));
        if (isMatch(request.getParameter(param), bundle.getString("regex." + param))) {
            return true;
        } else {
            contentManager.setLocalizedMessage(request, param + "Wrong", "content.message.not.match." + param);
            return false;
        }
    }

    private boolean isMatch(String target, String regex) {
        return Objects.nonNull(target) && target.matches(regex);
    }

    private boolean isEmpty(HttpServletRequest request, String param) {
        return Objects.isNull(request.getParameter(param)) || request.getParameter(param).isEmpty();
    }
}
