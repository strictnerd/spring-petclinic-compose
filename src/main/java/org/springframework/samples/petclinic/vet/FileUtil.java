package org.springframework.samples.petclinic.vet;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {
    @Timed(value = "timerName")
    public void start() {
        System.out.println("Timed");
    }
}
