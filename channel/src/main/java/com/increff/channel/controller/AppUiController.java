package com.increff.channel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		ModelAndView mavObject = mav("home.html");
		return mavObject;
	}

	@RequestMapping(value = "/ui/channel")
	public ModelAndView channel() {
		ModelAndView mavObject = mav("channel.html");
		return mavObject;
	}

	@RequestMapping(value = "/ui/order")
	public ModelAndView order() {
		ModelAndView mavObject = mav("order.html");
		return mavObject;
	}

	@RequestMapping(value = "/ui/listing")
	public ModelAndView listing() {
		ModelAndView mavObject = mav("channel_listing.html");
		return mavObject;
	}
}
