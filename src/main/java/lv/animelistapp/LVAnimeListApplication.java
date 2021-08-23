package lv.animelistapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Locale;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@MapperScan("lv.animelistapp.mapper")
public class LVAnimeListApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		Locale.setDefault(Locale.forLanguageTag("lv-LV"));
		SpringApplication.run(LVAnimeListApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		Locale.setDefault(Locale.forLanguageTag("lv-LV"));
		return builder.sources(LVAnimeListApplication.class);
	}

	private static SpringApplicationBuilder customizerBuilder(SpringApplicationBuilder builder) {
		Locale.setDefault(Locale.forLanguageTag("lv-LV"));
		return builder.sources(LVAnimeListApplication.class).bannerMode(Banner.Mode.OFF);
	}
}
