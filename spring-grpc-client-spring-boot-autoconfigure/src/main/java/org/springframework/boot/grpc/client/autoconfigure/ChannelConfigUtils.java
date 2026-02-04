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

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.grpc.client.autoconfigure.GrpcClientProperties.ChannelConfig;
import org.springframework.boot.grpc.client.autoconfigure.GrpcClientProperties.ChannelConfig.Health;
import org.springframework.boot.grpc.client.autoconfigure.GrpcClientProperties.ChannelConfig.Ssl;
import org.springframework.util.ObjectUtils;

/**
 * Utility to help w/ applying default values to channel configs.
 *
 * @author Chris Bono
 */
final class ChannelConfigUtils {

	ChannelConfigUtils() {
	}

	/**
	 * Applies values from {@code channelDefaults} to each field in the specified
	 * {@code channel} if the field has not been modified by the user and the default is
	 * specified for the field.
	 * <p>
	 * The base value for each field is considered to be the value assigned to the field
	 * in the {@link ChannelConfig} class (i.e. the coded value). If the value for the
	 * field in the specified instance is not the same as this base value, then it is
	 * considered modified (i.e. user modified the base coded value). This applies to both
	 * the candidate channel and the channel defaults.
	 * <p>
	 * For example, given: <pre class="code">
	 * class ChannelConfig {
	 *    String address = "base-server:9090";
	 * }
	 * </pre> If an instance has an address other than "base-server:9090" then it was
	 * modified.
	 * @param channel the candidate channel to apply defaults to
	 * @param channelDefaultsConfig the defaults to apply
	 * @return the same candidate instance passed in with defaults applied where necessary
	 */
	ChannelConfig applyDefaultsIfNecessary(ChannelConfig channel, ChannelConfig channelDefaultsConfig) {
		ChannelConfig baseConfig = new ChannelConfig();
		PropertyMapper map = PropertyMapper.get();
		map.from(channelDefaultsConfig::getAddress)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getAddress, channel::getAddress))
			.to(channel::setAddress);
		map.from(channelDefaultsConfig::getDefaultDeadline)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getDefaultDeadline, channel::getDefaultDeadline))
			.to(channel::setDefaultDeadline);
		map.from(channelDefaultsConfig::getDefaultLoadBalancingPolicy)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getDefaultLoadBalancingPolicy,
					channel::getDefaultLoadBalancingPolicy))
			.to(channel::setDefaultLoadBalancingPolicy);
		map.from(channelDefaultsConfig::isEnableKeepAlive)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::isEnableKeepAlive, channel::isEnableKeepAlive))
			.to(channel::setEnableKeepAlive);
		map.from(channelDefaultsConfig::isInheritDefaults)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::isInheritDefaults, channel::isInheritDefaults))
			.to(channel::setInheritDefaults);
		map.from(channelDefaultsConfig::getIdleTimeout)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getIdleTimeout, channel::getIdleTimeout))
			.to(channel::setIdleTimeout);
		map.from(channelDefaultsConfig::getKeepAliveTime)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getKeepAliveTime, channel::getKeepAliveTime))
			.to(channel::setKeepAliveTime);
		map.from(channelDefaultsConfig::getKeepAliveTimeout)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getKeepAliveTimeout, channel::getKeepAliveTimeout))
			.to(channel::setKeepAliveTimeout);
		map.from(channelDefaultsConfig::isKeepAliveWithoutCalls)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::isKeepAliveWithoutCalls,
					channel::isKeepAliveWithoutCalls))
			.to(channel::setKeepAliveWithoutCalls);
		map.from(channelDefaultsConfig::getMaxInboundMessageSize)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getMaxInboundMessageSize,
					channel::getMaxInboundMessageSize))
			.to(channel::setMaxInboundMessageSize);
		map.from(channelDefaultsConfig::getMaxInboundMetadataSize)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getMaxInboundMetadataSize,
					channel::getMaxInboundMetadataSize))
			.to(channel::setMaxInboundMetadataSize);
		map.from(channelDefaultsConfig::getNegotiationType)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getNegotiationType, channel::getNegotiationType))
			.to(channel::setNegotiationType);
		map.from(channelDefaultsConfig::isSecure)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::isSecure, channel::isSecure))
			.to(channel::setSecure);
		map.from(channelDefaultsConfig::getUserAgent)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getUserAgent, channel::getUserAgent))
			.to(channel::setUserAgent);
		this.applyDefaultsIfNecessary(channel.getHealth(), channelDefaultsConfig.getHealth());
		this.applyDefaultsIfNecessary(channel.getSsl(), channelDefaultsConfig.getSsl());
		map.from(channelDefaultsConfig::getServiceConfig)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfig::getServiceConfig, channel::getServiceConfig))
			.to((channelDefaultsServiceConfig) -> {
				channel.getServiceConfig().clear();
				channel.getServiceConfig().putAll(channelDefaultsServiceConfig);
			});
		return channel;
	}

	void applyDefaultsIfNecessary(Health channelHealth, Health channelDefaultsConfigHealth) {
		PropertyMapper map = PropertyMapper.get();
		Health baseConfigHealth = new ChannelConfig().getHealth();
		map.from(channelDefaultsConfigHealth::isEnabled)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfigHealth::isEnabled, channelHealth::isEnabled))
			.to(channelHealth::setEnabled);
		map.from(channelDefaultsConfigHealth::getServiceName)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfigHealth::getServiceName,
					channelHealth::getServiceName))
			.to(channelHealth::setServiceName);
	}

	void applyDefaultsIfNecessary(Ssl channelSsl, Ssl channelDefaultsConfigSsl) {
		PropertyMapper map = PropertyMapper.get();
		Ssl baseConfigSsl = new ChannelConfig().getSsl();
		map.from(channelDefaultsConfigSsl::isEnabled)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfigSsl::isEnabled, channelSsl::isEnabled))
			.to(channelSsl::setEnabled);
		map.from(channelDefaultsConfigSsl::getBundle)
			.when(notModifiedByUserAndDefaultsSpecified(baseConfigSsl::getBundle, channelSsl::getBundle))
			.to(channelSsl::setBundle);
	}

	private static <T> Predicate<T> notModifiedByUserAndDefaultsSpecified(Supplier<T> baseConfigValueSupplier,
			Supplier<T> candidateConfigValueSupplier) {
		return (T channelDefaultsConfigValue) -> {
			T baseConfigValue = baseConfigValueSupplier.get();
			T candidateConfigValue = candidateConfigValueSupplier.get();
			// if field in candidate is changed from coded defaults in ChannelConfig then
			// user modified it so default should not be applied
			if (!ObjectUtils.nullSafeEquals(baseConfigValue, candidateConfigValue)) {
				return false;
			}
			// if field in defaults is changed from coded defaults in ChannelConfig
			// then user specified the default so it needs to be applied to candidate
			return !ObjectUtils.nullSafeEquals(baseConfigValue, channelDefaultsConfigValue);
		};
	}

}
