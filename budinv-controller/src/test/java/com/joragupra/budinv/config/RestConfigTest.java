package com.joragupra.budinv.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestConfigTest {

	@Test
	void canBeInstantiated() {
		RestConfig config = new RestConfig();
		assertNotNull(config);
	}
}
