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
package org.springframework.grpc.server.service;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;

/**
 * Encapsulates enough information to construct an actual {@link ServerServiceDefinition}.
 *
 * @param service the bindable service
 * @param serviceInfo optional additional information about the service (e.g.
 * interceptors)
 * @author Chris Bono
 */
public record GrpcServiceSpec(BindableService service, @Nullable GrpcServiceInfo serviceInfo) {
	public GrpcServiceSpec {
		Assert.notNull(service, "service must not be null");
	}

}
