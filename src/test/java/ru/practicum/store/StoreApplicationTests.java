package ru.practicum.store;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.store.config.EnablePostgresTest;

@SpringBootTest
@EnablePostgresTest
@ActiveProfiles("test")
class StoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
