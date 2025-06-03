package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    // ISO-8601 với múi giờ
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            // Format LocalDateTime sang ISO-8601
            out.value(value.format(formatter));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String value = in.nextString();          // VD: "2025-05-17T00:00:00.000Z"
        if (value == null || value.isEmpty()) {
            return null;
        }
        // Cách 1: cắt Z
        if (value.endsWith("Z")) {
            value = value.substring(0, value.length() - 1);
        }
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid ISO date format: " + value, e);
        }
    }
}
