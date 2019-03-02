package ua.training.tag;

import ua.training.model.entity.Account;
import ua.training.tag.util.FormatManager;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;

public class StatusFormatTag extends SimpleTagSupport {
    private Account account;
    private String localeTag;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setLocaleTag(String localeTag) {
        this.localeTag = localeTag;
    }

    @Override
    public void doTag() throws IOException {
        String status = account.getStatus().name().toLowerCase();
        getJspContext().getOut().write(FormatManager.mapFormat("content.tag.status." + status,
                        FormatManager.getLocalizedMessage("content.accounts.status." + status, Locale.forLanguageTag(localeTag))));
    }
}
