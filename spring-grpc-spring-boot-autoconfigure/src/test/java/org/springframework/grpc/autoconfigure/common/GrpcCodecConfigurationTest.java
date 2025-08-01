/*
 * Copyright 2024-present the original author or authors.
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

package org.springframework.grpc.autoconfigure.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.grpc.autoconfigure.common.codec.GrpcCodecConfiguration;

import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;

/**
 * Tests for {@link GrpcCodecConfiguration}.
 *
 * @author Andrei Lisa
 */
public class GrpcCodecConfigurationTest {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
		.withConfiguration(AutoConfigurations.of(GrpcCodecConfiguration.class));

	@Test
	void testCompressorRegistryBean() {
		contextRunner.run(context -> assertThat(context).hasSingleBean(CompressorRegistry.class));
	}

	@Test
	void testDecompressorRegistryBean() {
		contextRunner.run(context -> assertThat(context).hasSingleBean(DecompressorRegistry.class));
	}

}
