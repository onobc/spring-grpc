/*
 * Copyright 2024-2024 the original author or authors.
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

package org.springframework.grpc.client;

import io.grpc.ChannelCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Grpc;
import io.grpc.ManagedChannelBuilder;

/**
 * Factory interface for creating {@link ManagedChannelBuilder} instances.
 *
 * @author Dave Syer
 * @author Chris Bono
 * @see ManagedChannelBuilder
 */
public interface GrpcChannelFactory {

	/**
	 * Creates a {@link GrpcChannelBuilder} for the given target string. The target can
	 * be either a valid nameresolver-compliant URI or an authority string as described in
	 * {@link Grpc#newChannelBuilder(String, ChannelCredentials)}, or a named channel
	 * which will return a builder that is based on a user-configured channel. The format
	 * for a named channel is {@code named://<name>} (e.g. 'named://my-channel').
	 *
	 * <p>The returned builder is configured to use all globally registered
	 * {@link ClientInterceptor interceptors}.
	 *
	 * @param target the target string as described in method javadocs
	 * @return a builder configured for the given target
	 */
	GrpcChannelBuilder createChannel(String target);

}
