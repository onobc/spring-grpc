/*
 * Copyright 2026-present the original author or authors.
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

package org.springframework.boot.grpc.client.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.boot.grpc.client.autoconfigure.GrpcClientProperties.ChannelConfig;
import org.springframework.grpc.client.NegotiationType;
import org.springframework.util.unit.DataSize;

/**
 * Tests for {@link ChannelConfigUtils#applyDefaultsIfNecessary}.
 *
 * @author Chris Bono
 */
class ChannelConfigUtilsApplyDefaultsTests {

	private ChannelConfigUtils utils = new ChannelConfigUtils();

	private ChannelConfig channelWithAllNonDefaultValues() {
		ChannelConfig channel = new ChannelConfig();
		channel.setAddress(channel.getAddress() + "1");
		channel.setDefaultDeadline(
				Optional.ofNullable(channel.getDefaultDeadline()).orElse(Duration.ofSeconds(1)).plusSeconds(60));
		channel.setDefaultLoadBalancingPolicy(channel.getDefaultLoadBalancingPolicy() + "1");
		channel.setEnableKeepAlive(!channel.isEnableKeepAlive());
		channel.getHealth().setEnabled(!channel.getHealth().isEnabled());
		channel.getHealth()
			.setServiceName(Optional.ofNullable(channel.getHealth().getServiceName()).orElse("someservice") + "1");
		channel.setIdleTimeout(channel.getIdleTimeout().plusSeconds(60));
		channel.setInheritDefaults(!channel.isInheritDefaults());
		channel.setKeepAliveTime(channel.getKeepAliveTime().plusSeconds(60));
		channel.setKeepAliveTimeout(channel.getKeepAliveTimeout().plusSeconds(60));
		channel.setKeepAliveWithoutCalls(!channel.isKeepAliveWithoutCalls());
		channel.setMaxInboundMessageSize(DataSize.ofBytes(channel.getMaxInboundMessageSize().toBytes() + 1000L));
		channel.setMaxInboundMetadataSize(DataSize.ofBytes(channel.getMaxInboundMetadataSize().toBytes() + 1000L));
		int nextIdx = channel.getNegotiationType().ordinal() % NegotiationType.values().length;
		channel.setNegotiationType(NegotiationType.values()[nextIdx]);
		channel.setSecure(!channel.isSecure());
		channel.getSsl().setEnabled(!Optional.ofNullable(channel.getSsl().isEnabled()).orElse(false));
		channel.getSsl().setBundle(Optional.ofNullable(channel.getSsl().getBundle()).orElse("somebundle") + "1");
		channel.getServiceConfig().put("some", "entry");
		channel.setUserAgent(Optional.ofNullable(channel.getUserAgent()).orElse("someguy") + "1");
		return channel;
	}

	@Test
	void defaultsAppliedWhenUserNotModifiedAndDefaultsSpecified() {
		ChannelConfig channelDefaults = channelWithAllNonDefaultValues();
		ChannelConfig channelWithoutUserModsBeforeApply = new ChannelConfig();
		ChannelConfig channelWithoutUserModsAfterApply = new ChannelConfig();

		channelWithoutUserModsAfterApply = utils.applyDefaultsIfNecessary(channelWithoutUserModsAfterApply,
				channelDefaults);

		// it should not be the same as it originally was
		assertThat(channelWithoutUserModsAfterApply).usingRecursiveComparison()
			.isNotEqualTo(channelWithoutUserModsBeforeApply);

		// it should be the same as channel defaults
		assertThat(channelWithoutUserModsAfterApply).usingRecursiveComparison().isEqualTo(channelDefaults);
	}

	@Test
	void defaultsNotAppliedWhenUserModifiedAndDefaultsNotSpecified2() {
		ChannelConfig channelDefaults = new ChannelConfig();
		ChannelConfig channelWithUserModsBeforeApply = channelWithAllNonDefaultValues();
		ChannelConfig channelWithUserModsAfterApply = channelWithAllNonDefaultValues();

		channelWithUserModsAfterApply = utils.applyDefaultsIfNecessary(channelWithUserModsAfterApply, channelDefaults);

		// it should be the same as it originally was
		assertThat(channelWithUserModsAfterApply).usingRecursiveComparison().isEqualTo(channelWithUserModsBeforeApply);

		// it should not be the same as channel defaults
		assertThat(channelWithUserModsAfterApply).usingRecursiveComparison().isNotEqualTo(channelDefaults);
	}

	@Test
	void defaultsNotAppliedWhenUserNotModifiedAndDefaultsNotSpecified() {
		ChannelConfig channelDefaults = new ChannelConfig();
		ChannelConfig channelWithoutUserModsBeforeApply = new ChannelConfig();
		ChannelConfig channelWithoutUserModsAfterApply = new ChannelConfig();

		channelWithoutUserModsAfterApply = utils.applyDefaultsIfNecessary(channelWithoutUserModsAfterApply,
				channelDefaults);

		// it should be the same as it originally was
		assertThat(channelWithoutUserModsAfterApply).usingRecursiveComparison()
			.isEqualTo(channelWithoutUserModsBeforeApply);

		// it should be the same as channel defaults
		assertThat(channelWithoutUserModsAfterApply).usingRecursiveComparison().isEqualTo(channelDefaults);
	}

}
