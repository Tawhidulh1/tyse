package com.Tawhidul.Tyse.constants;

import org.springframework.beans.factory.annotation.Value;

public final class ApplicationConstants {
	private ApplicationConstants() {
	}

	@Value("${elasticsearch.enabled}")
	public static final boolean elasticsearchEnabled = false;

}
