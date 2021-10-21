package Miscelaneous;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * https://www.baeldung.com/java-string-valid-date
 *
 * Clase para la validacion de fechas pasadas por String y dado un formato determinado.
 * "dd/mm/AAAA YY/MM/DD..etc
 *
 * Explicacion de uso:
 * DateTimeFormatter parses a text in two phases.
 * In Phase 1, it parses the text into various date and time fields based on the configuration.
 * In Phase 2, it resolves the parsed fields into a date and/or time object.
 *
 * The ResolverStyle attribute controls phase 2. It is an enum having three possible values:
 *
 * LENIENT – resolves dates and times leniently
 * SMART – resolves dates and times in an intelligent manner
 * STRICT – resolves dates and times strictly
 *
 *
 * Ejemplo Uso:
 * DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US)
 *     .withResolverStyle(ResolverStyle.STRICT);
 * DateValidator validator = new DateValidatorUsingDateTimeFormatter(dateFormatter);
 *
 * assertTrue(validator.isValid("2019-02-28"));
 * assertFalse(validator.isValid("2019-02-30"));
 */
public class DateValidatorByDateTimeFormatter implements DateValidator {

    private DateTimeFormatter dateFormatter;

    public DateValidatorByDateTimeFormatter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean isValid(String dateStr) {
        try {
            this.dateFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public Date StringToDate(String fecha) throws ParseException {
        //String sDate1="31/12/1998";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        System.out.println(date1);
        return date1;
    }

}
