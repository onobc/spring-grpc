package org.springframework.grpc.autoconfigure.server;

import io.grpc.netty.NettyServerBuilder;

class NettyServerFactoryPropertyMapper extends BaseServerFactoryPropertyMapper {

	NettyServerFactoryPropertyMapper(GrpcServerProperties properties) {
		super(properties);
	}

	void customizeNettyServerBuilder(NettyServerBuilder nettyServerBuilder) {
		super.customizeServerBuilder(nettyServerBuilder);
	}

}
