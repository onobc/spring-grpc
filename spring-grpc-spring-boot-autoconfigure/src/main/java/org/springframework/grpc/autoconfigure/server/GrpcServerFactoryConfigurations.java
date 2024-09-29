/*
 * Copyright 2012-2023 the original author or authors.
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

package org.springframework.grpc.autoconfigure.server;

import java.util.List;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.netty.NettyServerBuilder;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.BaseGrpcServerFactory;
import org.springframework.grpc.server.GrpcServerFactory;
import org.springframework.grpc.server.InProcessGrpcServerFactory;
import org.springframework.grpc.server.NettyGrpcServerFactory;
import org.springframework.grpc.server.ServerBuilderCustomizer;
import org.springframework.grpc.server.ShadedNettyGrpcServerFactory;

/**
 * Configurations for {@link GrpcServerFactory gRPC server factories}.
 *
 * @author Chris Bono
 */
@Configuration(proxyBeanMethods = false)
class GrpcServerFactoryConfigurations {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder.class)
	@ConditionalOnMissingBean(GrpcServerFactory.class)
	@EnableConfigurationProperties(GrpcServerProperties.class)
	static class ShadedNettyServerFactoryConfiguration {

		@Bean
		ShadedNettyGrpcServerFactory shadedNettyGrpcServerFactory(GrpcServerProperties properties,
				ObjectProvider<BindableService> grpcServicesProvider) {
			ShadedNettyServerFactoryPropertyMapper mapper = new ShadedNettyServerFactoryPropertyMapper(properties);
			List<ServerBuilderCustomizer<io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder>> builderCustomizers = List
				.of(mapper::customizeNettyServerBuilder);
			ShadedNettyGrpcServerFactory factory = new ShadedNettyGrpcServerFactory(properties.getAddress(),
					properties.getPort(), builderCustomizers);
			grpcServicesProvider.orderedStream().map(BindableService::bindService).forEach(factory::addService);
			return factory;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(NettyServerBuilder.class)
	@ConditionalOnMissingBean(GrpcServerFactory.class)
	@EnableConfigurationProperties(GrpcServerProperties.class)
	static class NettyServerFactoryConfiguration {

		@Bean
		NettyGrpcServerFactory nettyGrpcServerFactory(GrpcServerProperties properties,
				ObjectProvider<BindableService> grpcServicesProvider) {
			NettyServerFactoryPropertyMapper mapper = new NettyServerFactoryPropertyMapper(properties);
			List<ServerBuilderCustomizer<NettyServerBuilder>> builderCustomizers = List
					.of(mapper::customizeNettyServerBuilder);
			NettyGrpcServerFactory factory = new NettyGrpcServerFactory(properties.getAddress(),
					properties.getPort(), builderCustomizers);
			grpcServicesProvider.orderedStream().map(BindableService::bindService).forEach(factory::addService);
			return factory;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ServerBuilder.class)
	@ConditionalOnMissingBean(GrpcServerFactory.class)
	@EnableConfigurationProperties(GrpcServerProperties.class)
	static class ServiceProviderServerFactoryConfiguration {

		@Bean
		<T extends ServerBuilder<T>> BaseGrpcServerFactory<T> serviceProviderGrpcServerFactory(GrpcServerProperties properties,
				ObjectProvider<BindableService> grpcServicesProvider) {
			BaseServerFactoryPropertyMapper mapper = new BaseServerFactoryPropertyMapper(properties);
			List<ServerBuilderCustomizer<T>> builderCustomizers = List.of(mapper::customizeServerBuilder);
			BaseGrpcServerFactory factory = new BaseGrpcServerFactory(properties.getAddress(),
					properties.getPort(), builderCustomizers);
			grpcServicesProvider.orderedStream().map(BindableService::bindService).forEach(factory::addService);
			return factory;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(InProcessServerBuilder.class)
	@ConditionalOnProperty(prefix = "spring.grpc.server", name = "in-process-name")
	@ConditionalOnMissingBean(GrpcServerFactory.class)
	@EnableConfigurationProperties(GrpcServerProperties.class)
	static class InProcessServerFactoryConfiguration {

		@Bean
		InProcessGrpcServerFactory inProcessGrpcServerFactory(GrpcServerProperties properties,
				ObjectProvider<BindableService> grpcServicesProvider) {
			InProcessServerFactoryPropertyMapper mapper = new InProcessServerFactoryPropertyMapper(properties);
			InProcessGrpcServerFactory factory = new InProcessGrpcServerFactory(properties.getAddress(),
					properties.getPort(), List.of(mapper::customizeBuilder));
			grpcServicesProvider.orderedStream().map(BindableService::bindService).forEach(factory::addService);
			return factory;
		}

	}

}
