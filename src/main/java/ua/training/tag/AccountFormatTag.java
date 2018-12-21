package ua.training.tag;

import ua.training.model.entity.Account;
import ua.training.model.entity.DepositAccount;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AccountFormatTag extends SimpleTagSupport {
    private static Map<String, Function<Account, String>> formats;

    static {
        formats = new HashMap<>();
        formats.put(DepositAccount.class.getSimpleName(), null);
    }

    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();
    }
}
