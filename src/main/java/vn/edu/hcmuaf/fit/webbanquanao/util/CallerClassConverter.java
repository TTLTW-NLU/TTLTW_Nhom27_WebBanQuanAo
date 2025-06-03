package vn.edu.hcmuaf.fit.webbanquanao.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class CallerClassConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            String cn = element.getClassName();
            if (!cn.contains("UserLogsService") &&
                    !cn.contains("Logger") &&
                    !cn.contains("logback") &&
                    !cn.contains("java.lang.Thread") &&
                    !cn.equals(this.getClass().getName())) {

                // Lấy tên class đơn giản (không full package)
                String simpleClassName = cn.substring(cn.lastIndexOf('.') + 1);
                return simpleClassName + "." + element.getMethodName() + ":" + element.getLineNumber();
            }
        }
        return "unknown";
    }
}
