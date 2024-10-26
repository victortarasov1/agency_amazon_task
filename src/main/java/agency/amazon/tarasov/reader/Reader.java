package agency.amazon.tarasov.reader;

public interface Reader {
    <T> T readData(String path, Class<T> clazz);

}
