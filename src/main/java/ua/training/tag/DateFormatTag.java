package ua.training.tag;

import ua.training.tag.util.PatternManager;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatTag extends SimpleTagSupport {
    private LocalDate date;
    private String localeTag;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocaleTag(String localeTag) {
        this.localeTag = localeTag;
    }

    @Override
    public void doTag() throws IOException {
        Locale locale = Locale.forLanguageTag(localeTag);
        System.out.println(locale.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PatternManager.getPattern("pattern.day", locale), locale);
        System.out.println(formatter);
        getJspContext().getOut().write(date.format(formatter));
    }
}
