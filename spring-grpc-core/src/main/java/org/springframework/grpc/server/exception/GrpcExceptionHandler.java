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
package org.springframework.grpc.server.exception;

import io.grpc.StatusException;

/**
 * Defines an exception handler for handling exceptions that occur during gRPC server-side
 * processing. Implementations of this interface can be used to customize the error
 * handling behavior of a gRPC server.
 *
 * @author Dave Syer
 */
public interface GrpcExceptionHandler {

	/**
	 * Handle the given exception that occurred during gRPC server-side processing.
	 * @param exception the exception to handle
	 * @return the status to return to the client, or {@code null} if the exception cannot
	 * be classified
	 */
	StatusException handleException(Throwable exception);

}
