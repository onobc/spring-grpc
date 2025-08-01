/*
 * Copyright 2023-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.grpc.autoconfigure.server.health;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.boot.task.SimpleAsyncTaskSchedulerBuilder;

/**
 * Tests for {@link ActuatorHealthAdapterInvoker}.
 */
class ActuatorHealthAdapterInvokerTests {

	@Test
	void healthAdapterInvokedOnSchedule() {
		ActuatorHealthAdapter healthAdapter = mock();
		ActuatorHealthAdapterInvoker invoker = new ActuatorHealthAdapterInvoker(healthAdapter,
				new SimpleAsyncTaskSchedulerBuilder(), Duration.ofSeconds(5), Duration.ofSeconds(3));
		try {
			invoker.afterPropertiesSet();
			Awaitility.await()
				.between(Duration.ofSeconds(6), Duration.ofSeconds(12))
				.untilAsserted(() -> verify(healthAdapter, atLeast(2)).updateHealthStatus());
		}
		finally {
			invoker.destroy();
		}

	}

}
