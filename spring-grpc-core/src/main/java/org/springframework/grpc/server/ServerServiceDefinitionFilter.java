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
package org.springframework.grpc.server;

import io.grpc.ServerServiceDefinition;

/**
 * Strategy to determine whether a {@link ServerServiceDefinition} should be added to a
 * {@link GrpcServerFactory server factory}.
 *
 * @author Andrey Litvitski
 */
@FunctionalInterface
public interface ServerServiceDefinitionFilter {

	/**
	 * Determine whether the given {@link ServerServiceDefinition} should be added to the
	 * given {@link GrpcServerFactory server factory}.
	 * @param serviceDefinition the gRPC service definition under consideration.
	 * @param serverFactory the server factory in use.
	 * @return {@code true} if the service should be included; {@code false} otherwise.
	 */
	boolean filter(ServerServiceDefinition serviceDefinition, GrpcServerFactory serverFactory);

}
