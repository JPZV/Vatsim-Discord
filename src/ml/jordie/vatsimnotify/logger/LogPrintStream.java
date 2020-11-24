package ml.jordie.vatsimnotify.logger;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class LogPrintStream extends PrintStream {
	public LogPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void println(String string) {
        Date date = new Date();
        super.println("[" + date.toString() + "] " + string);
    }
}