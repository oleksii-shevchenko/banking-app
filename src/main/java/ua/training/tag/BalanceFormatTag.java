package ua.training.tag;

import ua.training.model.entity.Currency;
import ua.training.tag.util.AccountUtil;
import ua.training.tag.util.FormatManager;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.math.BigDecimal;

public class BalanceFormatTag extends SimpleTagSupport {
    private BigDecimal balance;
    private Currency currency;

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void doTag() throws IOException {
        AccountUtil util = new AccountUtil();
        getJspContext().getOut().write(FormatManager.mapFormat("content.tag.balance.format",
                util.getFormattedBalance(balance),
                util.getCurrencySign(currency))
        );
    }
}
