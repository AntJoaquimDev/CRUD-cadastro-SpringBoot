package curso.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		/*// criptografar senha
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result= encoder.encode("1234");
		System.out.println(result);  */
	}

}
