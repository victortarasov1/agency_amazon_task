package agency.amazon.tarasov.reader;

import agency.amazon.tarasov.exception.DataParsingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JsonReader implements Reader {
    private final ObjectMapper mapper;
    @Override
    public <T> T readData(String path, Class<T> clazz) {
        try(var stream = new FileInputStream(path)){
            return mapper.readerFor(clazz).readValue(stream);
        } catch (IOException ex) {
            throw new DataParsingException(ex);
        }
    }
}
