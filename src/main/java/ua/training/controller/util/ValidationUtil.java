package ua.training.controller.util;

import ua.training.controller.util.managers.ContentManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ValidationUtil {
    public boolean makeValidation(HttpServletRequest request, List<String> params) {
        boolean flag = true;
        for (String param : params) {
            flag &= (!isEmpty(request, param) && isValid(request, param));
        }
        return flag;
    }


    private boolean isValid(HttpServletRequest request, String param) {
        ResourceBundle bundle = ResourceBundle.getBundle("regex", LocaleUtil.getLocale(request));
        if (isMatch(request.getParameter(param), bundle.getString("regex." + param))) {
            return true;
        } else {
            ContentManager.setLocalizedMessage(request, param + "Wrong", "content.message.not.match." + param);
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
