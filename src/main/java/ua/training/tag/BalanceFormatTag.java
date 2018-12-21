package ua.training.tag;

import ua.training.model.entity.Account;
import ua.training.tag.util.AccountUtil;
import ua.training.tag.util.FormatManager;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class BalanceFormatTag extends SimpleTagSupport {
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void doTag() throws IOException {
        AccountUtil util = new AccountUtil();
        getJspContext().getOut().write(FormatManager.mapFormat("content.tag.balance.format",
                util.getFormattedBalance(account),
                util.getCurrencySign(account))
        );
    }
}
