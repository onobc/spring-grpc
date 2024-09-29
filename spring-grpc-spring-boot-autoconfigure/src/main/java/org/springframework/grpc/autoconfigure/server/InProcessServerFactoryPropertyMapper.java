package org.springframework.grpc.autoconfigure.server;

import io.grpc.inprocess.InProcessServerBuilder;

import org.springframework.boot.context.properties.PropertyMapper;

class InProcessServerFactoryPropertyMapper {

	private final GrpcServerProperties properties;

	InProcessServerFactoryPropertyMapper(GrpcServerProperties properties) {
		this.properties = properties;
	}

	void customizeBuilder(InProcessServerBuilder serverBuilder) {
	}

}
