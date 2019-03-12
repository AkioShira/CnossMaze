package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Schischko A.A. on 18.11.2017.
 */
public class MazeException extends Exception{
    static int r1;
    public String getException(String v1) throws CallException {
        if (v1.isEmpty()) {
            throw new CallException();
        }
        int fir0 = v1.indexOf(".");
        int two0 = v1.lastIndexOf(".");
        if (fir0 != two0) {
            throw new CallException();
        }
        // Проверка преобразования
        Pattern p = Pattern.compile("[0-9.]+");
        Matcher m = p.matcher(v1);
        if (!m.matches()) {
            throw new CallException();
        }
        else {
            int parity = Integer.parseInt(v1);
            if(parity%2!=1)
                throw new CallException();
            if(parity<9||parity>99)
                throw new CallException();
        }


        return v1;
    }
}

class CallException extends Exception
{
    public CallException()
    {
        super();
    }

}