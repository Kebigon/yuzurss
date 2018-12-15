package fr.lrgn.yuzurss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class YuzuRssApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(YuzuRssApplication.class, args);
	}
}