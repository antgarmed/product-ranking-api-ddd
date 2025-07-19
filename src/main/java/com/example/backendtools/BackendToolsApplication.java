package com.example.backendtools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.example.backendtools.shared.domain.Criterion;
import com.example.backendtools.shared.domain.UseCase;

@SpringBootApplication
@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = UseCase.class),
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Criterion.class)
})
public class BackendToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendToolsApplication.class, args);
	}

}
