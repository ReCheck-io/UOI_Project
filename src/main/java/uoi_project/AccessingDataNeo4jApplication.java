package uoi_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class AccessingDataNeo4jApplication {

    private final static Logger log = LoggerFactory.getLogger(AccessingDataNeo4jApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AccessingDataNeo4jApplication.class, args);
        System.exit(0);
    }


    @Bean
    CommandLineRunner deleteDB(UOIRepository uoiRepository){
        return args -> {
         uoiRepository.deleteAll();
        };
    }

    @Bean
    CommandLineRunner demo(UOIRepository uoiRepository) {
        return args -> {
            UOINode greg = new UOINode("3");
            System.out.println("greg "+  greg.toString());
            UOINode roy = new UOINode("3");
            System.out.println("roy "+  roy.toString());
            UOINode craig = new UOINode("3");
            System.out.println("craig "+  craig.toString());
            UOINode daka = new UOINode("4");
            System.out.println("daka "+  daka.toString());

            uoiRepository.save(greg);
            uoiRepository.save(roy);
            uoiRepository.save(craig);
            uoiRepository.save(daka);

            //old room
            greg.historyOf(craig);
            uoiRepository.save(greg);
            uoiRepository.save(craig);
            //old room
            roy.historyOf(craig);
            uoiRepository.save(roy);
            uoiRepository.save(craig);

            // newRoom craig
            daka.consistsOf(craig);
            uoiRepository.save(daka);
            uoiRepository.save(craig);
        };
    }

}