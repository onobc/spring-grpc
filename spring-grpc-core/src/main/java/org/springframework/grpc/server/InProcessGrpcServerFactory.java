/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.grpc.server;

import java.util.List;

import io.grpc.inprocess.InProcessServerBuilder;

public class InProcessGrpcServerFactory extends BaseGrpcServerFactory<InProcessServerBuilder> {

	public InProcessGrpcServerFactory(String address, int port, List<ServerBuilderCustomizer<InProcessServerBuilder>> serverBuilderCustomizers) {
		super(address, port, serverBuilderCustomizers);
	}

	@Override
	protected InProcessServerBuilder newServerBuilder() {
		return InProcessServerBuilder.forPort(getPort()); // TODO fix this
	}
}
