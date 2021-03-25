package io.recheck.uoi;

import io.recheck.uoi.dto.UOIPutRequestDTO;
import io.recheck.uoi.exceptions.GeneralErrorException;
import io.recheck.uoi.exceptionhandler.RestExceptionHandler;
import io.recheck.uoi.exceptions.NodeNotFoundException;
import io.recheck.uoi.exceptions.ValidationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class UOIService {

    @Autowired
    RestExceptionHandler restExceptionHandler;

    @Autowired
    UOIRepository uoiRepository;

    public UOINode generateNewUOI(String countryCode, LEVEL level, String uoiClass, String parentUOI) throws Exception {
        countryCode = countryCode.toUpperCase(Locale.ROOT);
        if (countryCode.trim().length()!=2){
            throw new ValidationErrorException("The country acronym has to be 2 letters");
        }

        //TODO: checking the LEVEL of the newly created UOI
//        if (){
//            throw new ValidationErrorException("The UOI have to have a hierarchy level.");
//        }

        UOINode node = null;
        if (parentUOI != null) {
            try {
                UOINode parentNode = uoiRepository.findByUoi(parentUOI);
                node = new UOINode(countryCode, level, uoiClass, parentUOI);
                uoiRepository.save(node);

                node.partOf(parentNode);
                uoiRepository.save(node);
                uoiRepository.save(parentNode);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new NodeNotFoundException();
            }
        } else {
            node = new UOINode(countryCode, level, uoiClass);
        }
        System.out.println(node.toString());
        uoiRepository.save(node);
        return node;
    }

    public Object search(String uoi) throws NodeNotFoundException {
        UOINode node = uoiRepository.findByUoi(uoi);
        if (node != null) {
            System.out.println(node);
            return node;
        } else {
            throw new NodeNotFoundException();
        }
    }

    public List searchByProperties(String key, String value, boolean withMetaData) throws GeneralErrorException {
        ArrayList<UOINode> result = new ArrayList();
        ArrayList<String> resultUOIOnly = new ArrayList();
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        if (nodes != null) {
            for (int i = 0; i < nodes.size(); i++) {
                UOINode node = nodes.get(i);
                if (node.getProperties() != null && !node.getProperties().isEmpty()) {
                    if (node.getProperties().containsKey(key)) {
                        if (node.getProperties().get(key).equals(value)) {
                            result.add(node);
                            resultUOIOnly.add(node.getUoi());
                        }
                    }
                }
            }
            if (withMetaData) {
                System.out.println(result);
                return result;
            } else {
                return resultUOIOnly;
            }
        } else {
            throw new GeneralErrorException();
        }

    }

    public UOINode putProperties(UOIPutRequestDTO uoiPutRequestDTO) throws NodeNotFoundException {
        UOINode node = uoiRepository.findByUoi(uoiPutRequestDTO.getUoi());
        if (node != null) {
            node.addMoreProperties(uoiPutRequestDTO.getKey().trim(), uoiPutRequestDTO.getValue().trim());
            uoiRepository.save(node);
            return node;
        } else {
            throw new NodeNotFoundException();
        }

    }

    public void demoNodes(UOIRepository uoiRepository) {
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

    public void demoNodesCombineTwoRooms(UOIRepository uoiRepository) {
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

        nodes.add(wall5);
        nodes.add(wall6);

        UOINode room2 = new UOINode(LEVEL.ROOM);

        nodes.add(room2);

        // adding the new room that combines the two initial rooms
        // combining two rooms
        UOINode roomCombined = new UOINode(LEVEL.ROOM);
        nodes.add(roomCombined);

        UOINode unit1 = new UOINode(LEVEL.UNIT);

        nodes.add(unit1);

        uoiRepository.saveAll(nodes);


        //adding the walls to a room1
        wall1.partOf(roomCombined);
        wall2.partOf(roomCombined);
        wall3.partOf(roomCombined);
        wall4.partOf(roomCombined);

        uoiRepository.saveAll(nodes);

        //room1 is consisting of the walls
        roomCombined.consistsOf(wall1);
        roomCombined.consistsOf(wall2);
        roomCombined.consistsOf(wall3);
        roomCombined.consistsOf(wall4);

        uoiRepository.saveAll(nodes);
        //adding the walls to a room2
        wall5.partOf(roomCombined);
        wall6.partOf(roomCombined);

        uoiRepository.saveAll(nodes);

        //room2 is consisting of the walls
        roomCombined.consistsOf(wall5);
        roomCombined.consistsOf(wall6);

        uoiRepository.saveAll(nodes);

        //adding the room to unit 1
        roomCombined.partOf(unit1);

        uoiRepository.saveAll(nodes);

        //the unit is consisted of
        unit1.consistsOf(roomCombined);
        uoiRepository.saveAll(nodes);

        //History of the rooms
//        room1.historyOf(roomCombined);
//        room2.historyOf(roomCombined);
        uoiRepository.saveAll(nodes);
    }

    public void demoNodesAddANewRoom(UOIRepository uoiRepository) {
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
        UOINode wall8 = new UOINode(LEVEL.WALL);

        nodes.add(wall5);
        nodes.add(wall6);
        nodes.add(wall7);
        nodes.add(wall8);

        UOINode room2 = new UOINode(LEVEL.ROOM);

        nodes.add(room2);

        UOINode wall9 = new UOINode(LEVEL.WALL);
        UOINode wall10 = new UOINode(LEVEL.WALL);

        nodes.add(wall9);
        nodes.add(wall10);

        UOINode room3 = new UOINode(LEVEL.ROOM);

        nodes.add(room3);

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
        wall8.partOf(room2);

        uoiRepository.saveAll(nodes);

        //room2 is consisting of the walls
        room2.consistsOf(wall5);
        room2.consistsOf(wall6);
        room2.consistsOf(wall7);
        room2.consistsOf(wall8);

        uoiRepository.saveAll(nodes);

        //adding the walls to a room3
        wall4.partOf(room3);
        wall8.partOf(room3);
        wall9.partOf(room3);
        wall10.partOf(room3);

        uoiRepository.saveAll(nodes);

        //room2 is consisting of the walls
        room3.consistsOf(wall4);
        room3.consistsOf(wall8);
        room3.consistsOf(wall9);
        room3.consistsOf(wall10);

        uoiRepository.saveAll(nodes);

        //adding the rooms to unit 1
        room1.partOf(unit1);
        room2.partOf(unit1);
        room3.partOf(unit1);

        uoiRepository.saveAll(nodes);

        //the unit is consisted of
        unit1.consistsOf(room1);
        unit1.consistsOf(room2);
        unit1.consistsOf(room3);

        uoiRepository.saveAll(nodes);
    }

}
