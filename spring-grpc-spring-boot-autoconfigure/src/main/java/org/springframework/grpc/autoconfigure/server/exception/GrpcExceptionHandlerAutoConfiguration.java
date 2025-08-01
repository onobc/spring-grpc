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
package org.springframework.grpc.autoconfigure.server.exception;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.autoconfigure.server.ConditionalOnGrpcServerEnabled;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.grpc.server.exception.CompositeGrpcExceptionHandler;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.grpc.server.exception.GrpcExceptionHandlerInterceptor;

import io.grpc.Grpc;

@AutoConfiguration
@ConditionalOnGrpcServerEnabled
@ConditionalOnClass(Grpc.class)
@ConditionalOnBean(GrpcExceptionHandler.class)
@ConditionalOnMissingBean(GrpcExceptionHandlerInterceptor.class)
@ConditionalOnProperty(prefix = "spring.grpc.server.exception-handler", name = "enabled", havingValue = "true",
		matchIfMissing = true)
public class GrpcExceptionHandlerAutoConfiguration {

	@GlobalServerInterceptor
	@Bean
	public GrpcExceptionHandlerInterceptor globalExceptionHandlerInterceptor(
			ObjectProvider<GrpcExceptionHandler> exceptionHandler) {
		return new GrpcExceptionHandlerInterceptor(new CompositeGrpcExceptionHandler(
				exceptionHandler.orderedStream().toArray(GrpcExceptionHandler[]::new)));
	}

}
