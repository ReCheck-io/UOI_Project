package io.recheck.uoi;

import io.recheck.uoi.dto.*;
import io.recheck.uoi.entity.ConsistsOf;
import io.recheck.uoi.exceptions.GeneralErrorException;
import io.recheck.uoi.exceptionhandler.RestExceptionHandler;
import io.recheck.uoi.exceptions.NodeNotFoundException;
import io.recheck.uoi.exceptions.ValidationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

@Slf4j
@Service

public class UOIService {

    @Autowired
    RestExceptionHandler restExceptionHandler;

    @Autowired
    UOIRepository uoiRepository;

    public UOINode generateNewUOI(NewUOIDTO newUOIDTO) throws Exception {
        UOINode node = null;
        if (StringUtils.hasText(newUOIDTO.getParentUOI())) {
            try {
                UOINode parentNode = uoiRepository.findByUoi(newUOIDTO.getParentUOI());
                node = new UOINode(newUOIDTO.getCountryCode(), newUOIDTO.getLevel(), newUOIDTO.getOwner(), newUOIDTO.getUoiClass());
                uoiRepository.save(node);
                node.partOf(parentNode);
                uoiRepository.save(node);
                uoiRepository.save(parentNode);
                parentNode.consistsOf(node);
                uoiRepository.save(parentNode);
                uoiRepository.save(node);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                throw new NodeNotFoundException();
            }
        } else {
            node = new UOINode(newUOIDTO.getCountryCode(), newUOIDTO.getLevel(), newUOIDTO.getOwner(), newUOIDTO.getUoiClass());
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

    public Object searchByOwner(String owner) throws NodeNotFoundException {
        ArrayList<UOINode> result = new ArrayList();
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        if (nodes != null) {
            for (UOINode node : nodes) {
                if (StringUtils.hasText(node.getOwner())) {
                    if (node.getOwner().equals(owner)) {
                        result.add(node);
                    }
                }
            }
        } else {
            //TODO: DTO that returns when there are no nodes found.
            log.warn("The database could be corrupted from testing with nested data");
            throw new NodeNotFoundException("There are no nodes found following the requested criteria.");
        }
        //TODO: what to return
        if (result.size()==0){
            throw new NodeNotFoundException("There are no nodes that are owned by "+owner);
        }else {
            return result;
        }

    }

    public List searchByProperties(UOISearchByPropertiesDTO uoiSearchByPropertiesDTO) throws NodeNotFoundException {
        ArrayList<UOINode> result = new ArrayList();
        ArrayList<OnlyUOIDTO> resultUOIOnly = new ArrayList();
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        if (nodes != null) {
            for (int i = 0; i < nodes.size(); i++) {
                UOINode node = nodes.get(i);
                if (node.getProperties() != null && !node.getProperties().isEmpty()) {
                    if (node.getProperties().containsKey(uoiSearchByPropertiesDTO.getKey())) {
                        if (node.getProperties().get(uoiSearchByPropertiesDTO.getKey()).contains(uoiSearchByPropertiesDTO.getValue())) {
                            result.add(node);
                            //is this the most efficient way ?
                            resultUOIOnly.add(new OnlyUOIDTO(node.getUoi()));
                        }
                    }
                }
            }
            if (uoiSearchByPropertiesDTO.isMetaData()) {
                log.info("Result with metaData " + result);
                return result;
            } else {
                return resultUOIOnly;
            }
        } else {
            //TODO: DTO that returns when there are no nodes found.
            log.warn("The database could be corrupted from testing with nested data");
            throw new NodeNotFoundException("There are no nodes found following the requested criteria.");
        }

    }

    public UOINode putProperties(UOIPutRequestDTO uoiPutRequestDTO) throws NodeNotFoundException {
        UOINode node = uoiRepository.findByUoi(uoiPutRequestDTO.getUoi());
        if (node != null) {
            node.addMoreProperties(uoiPutRequestDTO.getKey().trim(), uoiPutRequestDTO.getValue().trim());
            uoiRepository.save(node);
            log.info(String.valueOf(node));
            return node;
        } else {
            throw new NodeNotFoundException();
        }

    }

    public Object makeRelationship(UOIRelationshipDTO uoiRelationshipDTO) throws NodeNotFoundException {
        //find the two nodes
        //check what type of relationship it is
        //connect the nodes
        UOINode parent = uoiRepository.findByUoi(uoiRelationshipDTO.getParentNode());
        if (parent == null) {
            throw new NodeNotFoundException("The parent node is not found in the db.");
        }
        UOINode child = uoiRepository.findByUoi(uoiRelationshipDTO.getChildNode());
        if (child == null) {
            throw new NodeNotFoundException("The child node is not found in the db.");
        }
        String res = "";
        if (uoiRelationshipDTO.getRelationship().equals(RELATIONSHIP.PARTOF) || uoiRelationshipDTO.getRelationship().equals(RELATIONSHIP.CONSISTSOF)) {
            child.partOf(parent);
            uoiRepository.save(parent);
            uoiRepository.save(child);
            parent.consistsOf(child);
            uoiRepository.save(parent);
            uoiRepository.save(child);


            //       }else if(uoiRelationshipDTO.getRelationship().equals(RELATIONSHIP.HISTORYOF)){

//            parent.historyOf(child);
        }
        List<UOINode> result = new ArrayList<>();
        result.add(parent);
        result.add(child);
        return result;
    }

    public Object setNodeOwner(SetNodeOwnerDTO setNodeOwnerDTO) throws NodeNotFoundException {
        UOINode node = uoiRepository.findByUoi(setNodeOwnerDTO.getUoi());
        //TODO: ask if this works
        if (StringUtils.hasText(node.toString())) {
            log.info(node.toString());
            node.setOwner(setNodeOwnerDTO.getOwner());
            uoiRepository.save(node);
            log.info("The new owner is " + node.getOwner());
            return node;
        } else {
            throw new NodeNotFoundException();
        }
    }

    public void requestAccess(RequestAccessDTO requestAccessDTO){

    }

    public ArrayList getNodeChildren(GetChildrenDTO getChildrenDTO) throws NodeNotFoundException {
        ArrayList<UOINode> result = new ArrayList<>();
        UOINode node = uoiRepository.findByUoi(getChildrenDTO.getUoi());
        if (node == null){
            throw new NodeNotFoundException("The node has not been found in the database.");
        }
        List<String> children = node.getChildren();
        if (getChildrenDTO.getLevel() != null){
            for (String child : children) {
                UOINode childNode = uoiRepository.findByUoi(child);
                if (childNode.getLevel().equals(getChildrenDTO.getLevel())) {
                    result.add(childNode);
                }
            }
            if (result.size()==0){
                throw new NodeNotFoundException("There are no children found at this level.");
            }else {
                return result;
            }

        }else {
            for (String child : children) {
                UOINode childNode = uoiRepository.findByUoi(child);
                result.add(childNode);
            }
            return result;
        }




    }

    public Object searchByUoiClass(String uoiClass) throws NodeNotFoundException {
        ArrayList<UOINode> result = new ArrayList();
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        if (nodes != null) {
            for (UOINode node : nodes) {
                if (StringUtils.hasText(node.getUoiClass())) {
                    if (node.getUoiClass().equals(uoiClass)) {
                        result.add(node);
                    }
                }
            }
        } else {
            //TODO: DTO that returns when there are no nodes found.
            log.warn("The database could be corrupted from testing with nested data");
            throw new NodeNotFoundException("There are no nodes found following the requested criteria.");
        }
        //TODO: what to return
        if (result.size()==0){
            throw new NodeNotFoundException("There are no nodes that are classified as "+uoiClass);
        }else {
            return result;
        }

    }


//
//    public void demoNodes(UOIRepository uoiRepository) {
//        List<UOINode> nodes = new ArrayList<UOINode>();
//
//        //walls
//        UOINode wall1 = new UOINode(LEVEL.WALL);
//        UOINode wall2 = new UOINode(LEVEL.WALL);
//        UOINode wall3 = new UOINode(LEVEL.WALL);
//        UOINode wall4 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall1);
//        nodes.add(wall2);
//        nodes.add(wall3);
//        nodes.add(wall4);
//
//        UOINode room1 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room1);
//
//        UOINode wall5 = new UOINode(LEVEL.WALL);
//        UOINode wall6 = new UOINode(LEVEL.WALL);
//        UOINode wall7 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall5);
//        nodes.add(wall6);
//        nodes.add(wall7);
//
//        UOINode room2 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room2);
//
//        UOINode unit1 = new UOINode(LEVEL.UNIT);
//
//        nodes.add(unit1);
//
//        uoiRepository.saveAll(nodes);
//
//        //adding the walls to a room1
//        wall1.partOf(room1);
//        wall2.partOf(room1);
//        wall3.partOf(room1);
//        wall4.partOf(room1);
//
//        uoiRepository.saveAll(nodes);
//
//        //room1 is consisting of the walls
//        room1.consistsOf(wall1);
//        room1.consistsOf(wall2);
//        room1.consistsOf(wall3);
//        room1.consistsOf(wall4);
//
//        uoiRepository.saveAll(nodes);
//        //adding the walls to a room2
//        wall5.partOf(room2);
//        wall6.partOf(room2);
//        wall7.partOf(room2);
//        wall4.partOf(room2);
//
//        uoiRepository.saveAll(nodes);
//
//        //room2 is consisting of the walls
//        room2.consistsOf(wall5);
//        room2.consistsOf(wall6);
//        room2.consistsOf(wall7);
//        room2.consistsOf(wall4);
//
//        uoiRepository.saveAll(nodes);
//
//        //adding the rooms to unit 1
//        room1.partOf(unit1);
//        room2.partOf(unit1);
//
//        uoiRepository.saveAll(nodes);
//
//        //the unit is consisted of
//        unit1.consistsOf(room1);
//        unit1.consistsOf(room2);
//
//        uoiRepository.saveAll(nodes);
//    }
//
//    public void demoNodesCombineTwoRooms(UOIRepository uoiRepository) {
//        List<UOINode> nodes = new ArrayList<UOINode>();
//
//        //walls
//        UOINode wall1 = new UOINode(LEVEL.WALL);
//        UOINode wall2 = new UOINode(LEVEL.WALL);
//        UOINode wall3 = new UOINode(LEVEL.WALL);
//        UOINode wall4 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall1);
//        nodes.add(wall2);
//        nodes.add(wall3);
//        nodes.add(wall4);
//
//        UOINode room1 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room1);
//
//        UOINode wall5 = new UOINode(LEVEL.WALL);
//        UOINode wall6 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall5);
//        nodes.add(wall6);
//
//        UOINode room2 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room2);
//
//        // adding the new room that combines the two initial rooms
//        // combining two rooms
//        UOINode roomCombined = new UOINode(LEVEL.ROOM);
//        nodes.add(roomCombined);
//
//        UOINode unit1 = new UOINode(LEVEL.UNIT);
//
//        nodes.add(unit1);
//
//        uoiRepository.saveAll(nodes);
//
//
//        //adding the walls to a room1
//        wall1.partOf(roomCombined);
//        wall2.partOf(roomCombined);
//        wall3.partOf(roomCombined);
//        wall4.partOf(roomCombined);
//
//        uoiRepository.saveAll(nodes);
//
//        //room1 is consisting of the walls
//        roomCombined.consistsOf(wall1);
//        roomCombined.consistsOf(wall2);
//        roomCombined.consistsOf(wall3);
//        roomCombined.consistsOf(wall4);
//
//        uoiRepository.saveAll(nodes);
//        //adding the walls to a room2
//        wall5.partOf(roomCombined);
//        wall6.partOf(roomCombined);
//
//        uoiRepository.saveAll(nodes);
//
//        //room2 is consisting of the walls
//        roomCombined.consistsOf(wall5);
//        roomCombined.consistsOf(wall6);
//
//        uoiRepository.saveAll(nodes);
//
//        //adding the room to unit 1
//        roomCombined.partOf(unit1);
//
//        uoiRepository.saveAll(nodes);
//
//        //the unit is consisted of
//        unit1.consistsOf(roomCombined);
//        uoiRepository.saveAll(nodes);
//
//        //History of the rooms
////        room1.historyOf(roomCombined);
////        room2.historyOf(roomCombined);
//        uoiRepository.saveAll(nodes);
//    }
//
//    public void demoNodesAddANewRoom(UOIRepository uoiRepository) {
//        List<UOINode> nodes = new ArrayList<UOINode>();
//
//        //walls
//        UOINode wall1 = new UOINode(LEVEL.WALL);
//        UOINode wall2 = new UOINode(LEVEL.WALL);
//        UOINode wall3 = new UOINode(LEVEL.WALL);
//        UOINode wall4 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall1);
//        nodes.add(wall2);
//        nodes.add(wall3);
//        nodes.add(wall4);
//
//        UOINode room1 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room1);
//
//        UOINode wall5 = new UOINode(LEVEL.WALL);
//        UOINode wall6 = new UOINode(LEVEL.WALL);
//        UOINode wall7 = new UOINode(LEVEL.WALL);
//        UOINode wall8 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall5);
//        nodes.add(wall6);
//        nodes.add(wall7);
//        nodes.add(wall8);
//
//        UOINode room2 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room2);
//
//        UOINode wall9 = new UOINode(LEVEL.WALL);
//        UOINode wall10 = new UOINode(LEVEL.WALL);
//
//        nodes.add(wall9);
//        nodes.add(wall10);
//
//        UOINode room3 = new UOINode(LEVEL.ROOM);
//
//        nodes.add(room3);
//
//        UOINode unit1 = new UOINode(LEVEL.UNIT);
//
//        nodes.add(unit1);
//
//        uoiRepository.saveAll(nodes);
//
//        //adding the walls to a room1
//        wall1.partOf(room1);
//        wall2.partOf(room1);
//        wall3.partOf(room1);
//        wall4.partOf(room1);
//
//        uoiRepository.saveAll(nodes);
//
//        //room1 is consisting of the walls
//        room1.consistsOf(wall1);
//        room1.consistsOf(wall2);
//        room1.consistsOf(wall3);
//        room1.consistsOf(wall4);
//
//        uoiRepository.saveAll(nodes);
//        //adding the walls to a room2
//        wall5.partOf(room2);
//        wall6.partOf(room2);
//        wall7.partOf(room2);
//        wall8.partOf(room2);
//
//        uoiRepository.saveAll(nodes);
//
//        //room2 is consisting of the walls
//        room2.consistsOf(wall5);
//        room2.consistsOf(wall6);
//        room2.consistsOf(wall7);
//        room2.consistsOf(wall8);
//
//        uoiRepository.saveAll(nodes);
//
//        //adding the walls to a room3
//        wall4.partOf(room3);
//        wall8.partOf(room3);
//        wall9.partOf(room3);
//        wall10.partOf(room3);
//
//        uoiRepository.saveAll(nodes);
//
//        //room2 is consisting of the walls
//        room3.consistsOf(wall4);
//        room3.consistsOf(wall8);
//        room3.consistsOf(wall9);
//        room3.consistsOf(wall10);
//
//        uoiRepository.saveAll(nodes);
//
//        //adding the rooms to unit 1
//        room1.partOf(unit1);
//        room2.partOf(unit1);
//        room3.partOf(unit1);
//
//        uoiRepository.saveAll(nodes);
//
//        //the unit is consisted of
//        unit1.consistsOf(room1);
//        unit1.consistsOf(room2);
//        unit1.consistsOf(room3);
//
//        uoiRepository.saveAll(nodes);
//    }

}
