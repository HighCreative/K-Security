package cloud.swiftnode.ksecurity.abstraction.convertor;

import cloud.swiftnode.ksecurity.abstraction.StringConverter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class StringToIpConverter extends StringConverter<String> {
    public StringToIpConverter(String str) {
        super(str);
    }

    @Override
    public String convert() {
        return str.substring(str.indexOf("/") + 1);
    }
}
