package org.ipni.legacy.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ipni")
public class LegacyController {

	@GetMapping(path = { "/advPlantNameSearch.do", "/simplePlantNameSearch.do" })
	public ModelAndView searchPlants(@RequestParam Map<String, String> legacyParams, ModelMap model) {
		return decide(legacyParams, LegacyPlantNameParamMapper.translate(legacyParams), "plantname", model);
	}

	@GetMapping(path = { "/advAuthorSearch.do" })
	public ModelAndView searchAuthors(@RequestParam Map<String, String> legacyParams, ModelMap model) {
		return decide(legacyParams, LegacyAuthorParamMapper.translate(legacyParams), "author", model);
	}

	@GetMapping(path = { "/advPublicationSearch.do" })
	public ModelAndView searchPublications(@RequestParam Map<String, String> legacyParams, ModelMap model) {
		return decide(legacyParams, LegacyPublicationParamMapper.translate(legacyParams), "publication", model);
	}

	private ModelAndView decide(Map<String, String> legacyParams, Map<String, String> params, String type, ModelMap model) {
		String format = legacyParams.get("output_format");

		if (isDelimited(format)) {
			String forward = String.format("forward:/ipni/search/%s/delimited", type);
			log.debug("forwarding request to {}", forward);
			return new ModelAndView(forward);
		} else {
			model = new ModelMap();
			model.addAllAttributes(params);
			if ("full".equals(format)) {
				model.addAttribute("expanded", "true");
			}
			return new ModelAndView("redirect:/", model);
		}
	}

	private boolean isDelimited(String format) {
		return format != null && (format.equals("delimited")
				|| format.equals("delimited-classic")
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
