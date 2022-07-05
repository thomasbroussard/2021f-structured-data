import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class TestReadFromFile {

    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(Path.of("plain-text-demo/months.txt"));

        System.out.println(list);

        URL url = new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/plain-text/months.txt");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedInputStream inputStream1 = new BufferedInputStream(inputStream);
        String str = new String(inputStream1.readAllBytes());
        String[] split = str.split(System.getProperty("line.separator"));
        List<String> listFromUrl = Arrays.asList(split);

        System.out.println(listFromUrl);
        inputStream.close();




    }
}
