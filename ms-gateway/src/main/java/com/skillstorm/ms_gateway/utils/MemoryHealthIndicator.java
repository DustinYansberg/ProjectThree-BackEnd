package com.skillstorm.ms_gateway.utils;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator 
{

	/**
	 * Checks if our application is healthy based on
	 * memory usage in the JVM
	 * 
	 * Currently sets to unhealthy when free memory is less than 25%
	 */
	
	@Override
	public Health health()
	{
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		double freeMemoryPercent = ((double) freeMemory / (double) totalMemory) * 100;
		if (freeMemoryPercent < 25) 
		{
			return Health.down()
					.withDetail("free_memory", freeMemory + " bytes")
					.withDetail("total_memory", totalMemory + " bytes")
					.withDetail("free_memory_percent", String.format("%.2f percent", freeMemoryPercent))
					.build();
		}
		return Health.up()
				.withDetail("free_memory", freeMemory + " bytes")
				.withDetail("total_memory", totalMemory + " bytes")
				.withDetail("free_memory_percent", String.format("%.2f percent", freeMemoryPercent))
				.build();
	}

}
