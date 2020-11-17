package uoi_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class BaseInformationController {

    @Autowired
    UOIRepository uoiRepository;

    @GetMapping("/new")
    public UOINode generateNewUOI(@RequestParam(value = "level", defaultValue = "ROOM") LEVEL level) {
//        double length = 3;
//        double height = 3;
//        double width = 0.5;
//        String owner = "Odyssey";
//        String tenant = "Momentum";

//        // unique building ID https://github.com/pnnl/buildingid
//        String ubid = "849VQJH6+97CVG-1279-797-1043-922";
//
//        //needed for beacons/chip
//        double longitude = 0.000001;
//        double latitude =0.000001953125;

        UOINode node = new UOINode(level);
//        UOINode node = new UOINode(LEVEL.WALL,length, height, width, owner, tenant, ubid, longitude, latitude);
        uoiRepository.save(node);
        return node;
    }

    @GetMapping("/getNodeByUOI")
    public String getNodes(@RequestParam(value = "uuid") String uuid) {
        UOINode node = uoiRepository.findByUuid(uuid);
        System.out.println(node);
        return node.toString();
    }

    @GetMapping("/updateUBIDByGivenUOI")
    public String updateUBID(@RequestParam(value = "uuid") String uuid, @RequestParam(value = "ubid") String ubid){
        int nodePlace= 99999;
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        for (int i=0; i<nodes.size();i++){
            if (nodes.get(i).getUuid().equals(uuid)) {
                nodePlace = i;
            }
        }
        nodes.get(nodePlace).setUbid(ubid);
        uoiRepository.saveAll(nodes);
        return nodes.get(nodePlace).toString();
    }

    @GetMapping("/getAllNodes")
    public String getAllNodes() {
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        return nodes.toString();
    }

    @GetMapping("/setNodeBePartOfAnotherNode")
    public String nodePartOfAnother(@RequestParam(value = "uuid") String uuid, @RequestParam(value = "uuidPartOf") String uuidPartOf) {
        int nodePlace = 999999999;
        int nodePlacePartOf = 999999999;
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getUuid().equals(uuid)) {
                nodePlace = i;
            }
            if (nodes.get(i).getUuid().equals(uuidPartOf)) {
                nodePlacePartOf = i;
            }
        }
        if (nodePlace < 99999999) {
            if (nodePlacePartOf < 99999999) {
                nodes.get(nodePlacePartOf).partOf(nodes.get(nodePlace));
                uoiRepository.saveAll(nodes);
            }
        }
        ArrayList<UOINode> usedNodes = new ArrayList();
        usedNodes.add(nodes.get(nodePlacePartOf));
        usedNodes.add(nodes.get(nodePlace));
        return usedNodes.toString();
    }

    @GetMapping("/setNodeBeConsistedOfAnotherNode")
    public String nodeConsistedOfAnother(@RequestParam(value = "uuid") String uuid, @RequestParam(value = "uuidConsistedOf") String uuidConsistedOf) {
        int nodePlace = 999999999;
        int nodePlaceConsistedOf = 999999999;
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getUuid().equals(uuidConsistedOf)) {
                nodePlaceConsistedOf = i;
            }
            if (nodes.get(i).getUuid().equals(uuid)) {
                nodePlace = i;
            }
        }
        if (nodePlace < 99999999) {
            if (nodePlaceConsistedOf < 99999999) {
                nodes.get(nodePlace).consistsOf(nodes.get(nodePlaceConsistedOf));
                uoiRepository.saveAll(nodes);
            }
        }
        ArrayList<UOINode> usedNodes = new ArrayList();
        usedNodes.add(nodes.get(nodePlaceConsistedOf));
        usedNodes.add(nodes.get(nodePlace));
        return usedNodes.toString();
    }

    @GetMapping("/setNodeBeHistoryOfAnotherNode")
    public String nodeHistoryOfAnother(@RequestParam(value = "uuid") String uuid, @RequestParam(value = "uuidHistoryOf") String uuidHistoryOf) {
        int nodePlace = 999999999;
        int nodePlaceHistoryOf = 999999999;
        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getUuid().equals(uuid)) {
                nodePlace = i;
            }
            if (nodes.get(i).getUuid().equals(uuidHistoryOf)) {
                nodePlaceHistoryOf = i;
            }
        }
        if (nodePlace < 99999999) {
            if (nodePlaceHistoryOf < 99999999) {
                nodes.get(nodePlaceHistoryOf).historyOf(nodes.get(nodePlace));
                uoiRepository.saveAll(nodes);
            }
        }
        ArrayList<UOINode> usedNodes = new ArrayList();
        usedNodes.add(nodes.get(nodePlaceHistoryOf));
        usedNodes.add(nodes.get(nodePlace));
        return usedNodes.toString();
    }

    @PostMapping(path = "/newWithInformation", consumes = "application/json", produces = "application/json")
    public UOINode addMember(@RequestBody UOINode uoiNode) {
        uoiRepository.save(uoiNode);
        return uoiNode;
    }

    @GetMapping("/demoNodes")
    public String executeDemoNode(){
        uoiRepository.deleteAll();
        demoNodes(uoiRepository);
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503104983629824/Untitled_Diagram1.png'/> " +
                "</body></html>";
    }

    @GetMapping("/scenarioCombine")
    public String executeDemoNodeCombineTwoRooms(){
        uoiRepository.deleteAll();
        demoNodesCombineTwoRooms(uoiRepository);
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503102551064576/Untitled_Diagram3.png'/> " +
                "</body></html>";
    }

    @GetMapping("/clearDB")
    public String clearDB(){
        uoiRepository.deleteAll();
        return "DB has been cleared!";
    }

    @GetMapping("/")
    public String error(){
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/675683560673509389/776827370161700874/addtext_com_MTAwMTI1MzExODY.jpg'/> " +
                "</body></html>";

    }

    @GetMapping("/scenarioAddANewRoom")
    public String executeDemoNodeAddANewRoom(){
        uoiRepository.deleteAll();
        demoNodesAddANewRoom(uoiRepository);
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503092572553226/Untitled_Diagram4.png'/> " +
                "</body></html>";
    }


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

    public void demoNodesCombineTwoRooms(UOIRepository uoiRepository){
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
        room1.historyOf(roomCombined);
        room2.historyOf(roomCombined);
        uoiRepository.saveAll(nodes);
    }

    public void demoNodesAddANewRoom(UOIRepository uoiRepository){
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
