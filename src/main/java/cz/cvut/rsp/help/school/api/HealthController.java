package cz.cvut.rsp.help.school.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
public class HealthController {

    @GetMapping(value = "/healthz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> healthz() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "ok");
        return ResponseEntity.ok(map);
    }

}
