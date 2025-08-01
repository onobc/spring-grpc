/*
 * Copyright 2016-present the original author or authors.
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
 *
 * Partial copy from net.devh:grpc-spring-boot-starter.
 */

package org.springframework.grpc.server.lifecycle;

import static java.util.Objects.requireNonNull;

import java.time.Clock;

import org.springframework.context.ApplicationEvent;

import io.grpc.Server;

/**
 * The base event for {@link GrpcServerLifecycle}.
 *
 * @author Michael (yidongnan@gmail.com)
 */
public abstract class GrpcServerLifecycleEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final Server server;

	/**
	 * Creates a new GrpcServerLifecycleEvent.
	 * @param lifecyle The lifecycle that caused this event.
	 * @param clock The clock used to determine the timestamp.
	 * @param server The server related to this event.
	 */
	protected GrpcServerLifecycleEvent(final GrpcServerLifecycle lifecyle, final Clock clock, final Server server) {
		super(lifecyle, clock);
		this.server = requireNonNull(server, "server");
	}

	/**
	 * Creates a new GrpcServerLifecycleEvent.
	 * @param lifecyle The lifecycle that caused this event.
	 * @param server The server related to this event.
	 */
	protected GrpcServerLifecycleEvent(final GrpcServerLifecycle lifecyle, final Server server) {
		super(lifecyle);
		this.server = requireNonNull(server, "server");
	}

	/**
	 * Gets the server related to this event.
	 * @return The server instance.
	 */
	public Server getServer() {
		return this.server;
	}

	/**
	 * Gets the lifecycle instance that triggered this event.
	 */
	@Override
	public GrpcServerLifecycle getSource() {
		return (GrpcServerLifecycle) super.getSource();
	}

}
