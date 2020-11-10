package uoi_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            UOINode greg = new UOINode(LEVEL.GREG);
            System.out.println("greg "+  greg.toString());
            UOINode roy = new UOINode(LEVEL.ROY);
            System.out.println("roy "+  roy.toString());
            UOINode pepi = new UOINode(LEVEL.EMO);
            System.out.println("Emo "+  pepi.toString());
            UOINode daka = new UOINode(LEVEL.DAKA);
            System.out.println("daka "+  daka.toString());

            List<UOINode> nodes = new ArrayList<UOINode>();
            nodes.add(greg);
            nodes.add(roy);
            nodes.add(pepi);
            nodes.add(daka);

            uoiRepository.saveAll(nodes);
            //old room
            greg.historyOf(pepi);
            uoiRepository.save(greg);

            //old room
            roy.historyOf(pepi);
            uoiRepository.save(roy);

             pepi.partOf(daka);
//            partOf.setUoiNodePartOf(pepi);
//            partOf.setUoiNode(daka);
//            partOf.setTimestamp(String.valueOf(new Date().getTime()));
//            uoiRepository.saveAll(nodes);
            uoiRepository.save(pepi);
            // newRoom pepi
            daka.consistsOf(pepi);
            uoiRepository.save(daka);


        };
    }

}