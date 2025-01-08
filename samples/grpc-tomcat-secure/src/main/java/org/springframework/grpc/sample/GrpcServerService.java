package org.springframework.grpc.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.grpc.sample.proto.HelloReply;
import org.springframework.grpc.sample.proto.HelloRequest;
import org.springframework.grpc.sample.proto.SimpleGrpc;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.grpc.stub.StreamObserver;

@Service
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

	private static Log log = LogFactory.getLog(GrpcServerService.class);

	@Override
	public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
		log.info("Hello " + req.getName());
		log.info("Security " + SecurityContextHolder.getContext().getAuthentication());
		HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void streamHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
		log.info("Hello " + req.getName());
		int count = 0;
		while (count < 10) {
			HelloReply reply = HelloReply.newBuilder().setMessage("Hello(" + count + ") ==> " + req.getName()).build();
			responseObserver.onNext(reply);
			count++;
			try {
				Thread.sleep(1000L);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				responseObserver.onError(e);
				return;
			}
		}
		responseObserver.onCompleted();
	}

}