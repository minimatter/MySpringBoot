package end;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;


@SpringBootApplication

@MapperScan("end.dao")//使用MapperScan批量扫描所有的Mapper接口；
public class DemoApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
//	}

	public static void main(String[] args) {
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.addDialect(new LayoutDialect());
		SpringApplication.run(DemoApplication.class, args);
	}

}
