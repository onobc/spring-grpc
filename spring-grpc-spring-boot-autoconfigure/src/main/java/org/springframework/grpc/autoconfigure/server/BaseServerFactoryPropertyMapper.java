package org.springframework.grpc.autoconfigure.server;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.grpc.ServerBuilder;

import org.springframework.boot.context.properties.PropertyMapper;

class BaseServerFactoryPropertyMapper {

	protected final GrpcServerProperties properties;

	BaseServerFactoryPropertyMapper(GrpcServerProperties properties) {
		this.properties = properties;
	}

	void customizeServerBuilder(ServerBuilder<?> serverBuilder) {
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		GrpcServerProperties.KeepAlive keepAlive = this.properties.getKeepAlive();
		map.from(keepAlive.getTime()).to(durationProperty(serverBuilder::keepAliveTime));
		map.from(keepAlive.getTimeout()).to(durationProperty(serverBuilder::keepAliveTimeout));
	}

	protected Consumer<Duration> durationProperty(BiConsumer<Long, TimeUnit> setter) {
		return (duration) -> setter.accept(duration.toNanos(), TimeUnit.NANOSECONDS);
	}
}
