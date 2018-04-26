package org.ipni.legacy.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ipni")
public class LegacyController {

	@GetMapping("/advPlantNameSearch.do")
	public ModelAndView search(@RequestParam Map<String, String> legacyParams, ModelMap model) {
		String format = legacyParams.get("output_format");

		if (isDelimited(format)) {
			return new ModelAndView("forward:/ipni/search/delimited");
		} else {
			Map<String, String> params = LegacyParamMapper.translate(legacyParams);
			model = new ModelMap();
			model.addAllAttributes(params);
			if ("full".equals(format)) {
				model.addAttribute("expanded", "true");
			}
			return new ModelAndView("redirect:/", model);
		}
	}

	private boolean isDelimited(String format) {
		return format != null
				&& (format.equals("delimited")
						|| format.equals("delimited-minimal")
						|| format.equals("delimited-short")
						|| format.equals("delimited-extended"));
	}

	@GetMapping("/idPlantNameSearch.do")
	public String nameIdSearch(@RequestParam String id) {
		return String.format("redirect:/n/%s", id);
	}

	@GetMapping("/idPublicationSearch.do")
	public String publicationIdSearch(@RequestParam String id) {
		return String.format("redirect:/p/%s", id);
	}

	@GetMapping("/idAuthorSearch.do")
	public String authorIdSearch(@RequestParam String id) {
		return String.format("redirect:/a/%s", id);
	}
}
