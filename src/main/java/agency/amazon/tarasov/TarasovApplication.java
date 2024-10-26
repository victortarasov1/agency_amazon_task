package agency.amazon.tarasov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class TarasovApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarasovApplication.class, args);
	}

}
