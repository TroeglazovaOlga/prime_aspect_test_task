import org.springframework.web.bind.annotation.*;
import parserservice.Parser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
public class Controller {

    @PostMapping("/parse")
    public Map<String, Set<String>> parse(@RequestBody String content) throws IOException {
        Parser parser = new Parser();
        Map<String, Set<String>> result;
        result = parser.parse(content);
        return result;
    }
}