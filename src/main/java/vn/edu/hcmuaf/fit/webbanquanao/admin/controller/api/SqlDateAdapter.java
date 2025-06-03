package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Date;

public class SqlDateAdapter extends TypeAdapter<Date> {
    @Override
    public Date read(JsonReader in) throws IOException {
        String value = in.nextString(); // JSON gửi lên là "2025-05-17"
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Date.valueOf(value); // valueOf nhận chuỗi yyyy-MM-dd
        } catch (IllegalArgumentException e) {
            throw new IOException("Sai định dạng ngày. Định dạng hợp lệ là yyyy-MM-dd", e);
        }
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString()); // Trả về chuỗi "yyyy-MM-dd"
        }
    }
}
