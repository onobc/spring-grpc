package org.springframework.grpc.autoconfigure.server;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

class ShadedNettyServerFactoryPropertyMapper extends BaseServerFactoryPropertyMapper {

	ShadedNettyServerFactoryPropertyMapper(GrpcServerProperties properties) {
		super(properties);
	}

	void customizeNettyServerBuilder(NettyServerBuilder nettyServerBuilder) {
		super.customizeServerBuilder(nettyServerBuilder);
	}

}
