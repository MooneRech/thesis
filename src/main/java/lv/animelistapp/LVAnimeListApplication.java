package lv.animelistapp;

import org.mybatis.spring.annotation.MapperScan;
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
		/*Msg msg = new Msg();
		msg.changeLocale("lv-LV");*/
		Locale.setDefault(Locale.forLanguageTag("lv-LV"));
		SpringApplication.run(LVAnimeListApplication.class, args);
	}

	/**
	 * Configure the application. Normally all you would need to do it add sources (e.g.
	 * config classes) because other settings have sensible defaults. You might choose
	 * (for instance) to add default command line arguments, or set an active Spring
	 * profile.
	 *
	 * @param builder a builder for the application context
	 * @return the application builder
	 * @see SpringApplicationBuilder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		//return super.configure(builder); //This is also possible
		return builder.sources(LVAnimeListApplication.class);
	}
}
