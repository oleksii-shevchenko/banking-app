package ua.training.tag;

import ua.training.tag.util.PatternManager;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatTag extends SimpleTagSupport {
    private LocalDateTime time;
    private String localeTag;

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setLocaleTag(String localeTag) {
        this.localeTag = localeTag;
    }

    @Override
    public void doTag() throws IOException {
        Locale locale = Locale.forLanguageTag(localeTag);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PatternManager.getPattern("pattern.time", locale), locale);
        getJspContext().getOut().write(time.format(formatter));
    }
}
