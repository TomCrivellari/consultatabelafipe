package br.com.projetostom.consultatabelafipe;

import br.com.projetostom.consultatabelafipe.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultatabelafipeApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ConsultatabelafipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.exibeMenu();
	}
}
