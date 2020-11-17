package uoi_project;

import org.neo4j.ogm.annotation.Relationship;
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

    public void demoNodes(UOIRepository uoiRepository){
        List<UOINode> nodes = new ArrayList<UOINode>();

        //walls
        UOINode wall1 = new UOINode(LEVEL.WALL);
        UOINode wall2 = new UOINode(LEVEL.WALL);
        UOINode wall3 = new UOINode(LEVEL.WALL);
        UOINode wall4 = new UOINode(LEVEL.WALL);

        nodes.add(wall1);
        nodes.add(wall2);
        nodes.add(wall3);
        nodes.add(wall4);

        UOINode room1 = new UOINode(LEVEL.ROOM);

        nodes.add(room1);

        UOINode wall5 = new UOINode(LEVEL.WALL);
        UOINode wall6 = new UOINode(LEVEL.WALL);
        UOINode wall7 = new UOINode(LEVEL.WALL);

        nodes.add(wall5);
        nodes.add(wall6);
        nodes.add(wall7);

        UOINode room2 = new UOINode(LEVEL.ROOM);

        nodes.add(room2);

        UOINode unit1 = new UOINode(LEVEL.UNIT);

        nodes.add(unit1);

        uoiRepository.saveAll(nodes);

        //adding the walls to a room1
        wall1.partOf(room1);
        wall2.partOf(room1);
        wall3.partOf(room1);
        wall4.partOf(room1);

        uoiRepository.saveAll(nodes);

        //room1 is consisting of the walls
        room1.consistsOf(wall1);
        room1.consistsOf(wall2);
        room1.consistsOf(wall3);
        room1.consistsOf(wall4);

        uoiRepository.saveAll(nodes);
        //adding the walls to a room2
        wall5.partOf(room2);
        wall6.partOf(room2);
        wall7.partOf(room2);
        wall4.partOf(room2);

        uoiRepository.saveAll(nodes);

        //room2 is consisting of the walls
        room2.consistsOf(wall5);
        room2.consistsOf(wall6);
        room2.consistsOf(wall7);
        room2.consistsOf(wall4);

        uoiRepository.saveAll(nodes);

        //adding the rooms to unit 1
        room1.partOf(unit1);
        room2.partOf(unit1);

        uoiRepository.saveAll(nodes);

        //the unit is consisted of
        unit1.consistsOf(room1);
        unit1.consistsOf(room2);

        uoiRepository.saveAll(nodes);
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
            demoNodes(uoiRepository);
        };
    }

}