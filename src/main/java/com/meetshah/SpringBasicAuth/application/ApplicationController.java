package com.meetshah.SpringBasicAuth.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetshah.SpringBasicAuth.application.models.ApplicationInfo;

@RestController
public class ApplicationController {

	/*
	 * end-point not protected.
	 */
	@GetMapping("/api/application")
	public ApplicationInfo getApplicationInfo() {
		ApplicationInfo info = new ApplicationInfo();
		info.setVersion("1.5.0");

		return info;
	}
}
