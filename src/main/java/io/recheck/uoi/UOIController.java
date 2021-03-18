package io.recheck.uoi;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;

import java.util.ArrayList;

@RestController
public class UOIController {

    @Autowired
    UOIRepository uoiRepository;

    @Autowired
    UOIService service;

    @Operation(summary = "Creating a new basic UOI without metadata.")
    @GetMapping("/new")
    public UOINode generateNewUOI(@RequestParam(value = "countryCode", defaultValue = "NL") String countryCode,
                                  @RequestParam(value = "level", defaultValue = "ROOM") LEVEL level,
                                  @RequestParam(value = "uoiClass", required = false) String uoiClass,
                                  @RequestParam(value = "parentUOI", required = false) String parentUOI) throws Exception {
        return service.generateNewUOI(countryCode, level, uoiClass, parentUOI);
    }

    @Operation(summary = "Search for a UOI node by UOI or property.")
    @GetMapping("/search/uoi")
    public Object getNodes(@RequestParam(value = "uoi") String uoi) {
        return service.search(uoi);
    }

    @Operation(summary = "adding properties to a node.")
    @PutMapping("/node/properties")
    public UOINode putNodeProperties(@RequestParam(value = "uoi") String uoi,
                                     @RequestParam(value = "key") String key,
                                     @RequestParam(value = "value") String value) {
        return service.putProperties(uoi, key, value);

    }

    @Operation(summary = "Search for UOI by existing properties.")
    @GetMapping("/search/properties")
    public ArrayList getNodeByProps(@RequestParam(value = "key") String key,
                                 @RequestParam(value = "value") String value,
                                 @RequestParam(value = "withMetaData" , defaultValue = "false") boolean withMetaData){

        return (ArrayList) service.searchByProperties(key, value, withMetaData);

    }


    //TODO: da napravq DTO s 2te id-ta i da se razpoznava tipa na vryzkata i da se napravi
//    @PutMapping("/node")
//    public String nodePartOfAnother(@RequestParam(value = "uoi") String uoi,
//                                    @RequestParam(value = "uoiPartOf") String uoiPartOf) {
//        int nodePlace = 999999999;
//        int nodePlacePartOf = 999999999;
//        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
//        for (int i = 0; i < nodes.size(); i++) {
//            if (nodes.get(i).getUoi().equals(uoi)) {
//                nodePlace = i;
//            }
//            if (nodes.get(i).getUoi().equals(uoiPartOf)) {
//                nodePlacePartOf = i;
//            }
//        }
//        if (nodePlace < 99999999) {
//            if (nodePlacePartOf < 99999999) {
//                nodes.get(nodePlacePartOf).partOf(nodes.get(nodePlace));
//                uoiRepository.saveAll(nodes);
//            }
//        }
//        ArrayList<UOINode> usedNodes = new ArrayList();
//        usedNodes.add(nodes.get(nodePlacePartOf));
//        usedNodes.add(nodes.get(nodePlace));
//        return usedNodes.toString();
//    }

//    //TODO: da se razkara i da byde obedineno s gornoto
//    @GetMapping("/node")
//    public String nodeConsistedOfAnother(@RequestParam(value = "uuid") String uuid, @RequestParam(value = "uuidConsistedOf") String uuidConsistedOf) {
//        int nodePlace = 999999999;
//        int nodePlaceConsistedOf = 999999999;
//        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
//        for (int i = 0; i < nodes.size(); i++) {
//            if (nodes.get(i).getUoi().equals(uuidConsistedOf)) {
//                nodePlaceConsistedOf = i;
//            }
//            if (nodes.get(i).getUoi().equals(uuid)) {
//                nodePlace = i;
//            }
//        }
//        if (nodePlace < 99999999) {
//            if (nodePlaceConsistedOf < 99999999) {
//                nodes.get(nodePlace).consistsOf(nodes.get(nodePlaceConsistedOf));
//                uoiRepository.saveAll(nodes);
//            }
//        }
//        ArrayList<UOINode> usedNodes = new ArrayList();
//        usedNodes.add(nodes.get(nodePlaceConsistedOf));
//        usedNodes.add(nodes.get(nodePlace));
//        return usedNodes.toString();
//    }
//
//    //TODO: da go razkaram i da vleze pri ostanalite za relationship
//    @GetMapping("/node")
//    public String nodeHistoryOfAnother(@RequestParam(value = "uoi") String uoi, @RequestParam(value = "uoiHistoryOf") String uoiHistoryOf) {
//        int nodePlace = 999999999;
//        int nodePlaceHistoryOf = 999999999;
//        ArrayList<UOINode> nodes = (ArrayList<UOINode>) uoiRepository.findAll();
//        for (int i = 0; i < nodes.size(); i++) {
//            if (nodes.get(i).getUoi().equals(uoi)) {
//                nodePlace = i;
//            }
//            if (nodes.get(i).getUoi().equals(uoiHistoryOf)) {
//                nodePlaceHistoryOf = i;
//            }
//        }
//        if (nodePlace < 99999999) {
//            if (nodePlaceHistoryOf < 99999999) {
//                nodes.get(nodePlaceHistoryOf).historyOf(nodes.get(nodePlace));
//                uoiRepository.saveAll(nodes);
//            }
//        }
//        ArrayList<UOINode> usedNodes = new ArrayList();
//        usedNodes.add(nodes.get(nodePlaceHistoryOf));
//        usedNodes.add(nodes.get(nodePlace));
//        return usedNodes.toString();
//    }


    @PostMapping(path = "/newWithInformation", consumes = "application/json", produces = "application/json")
    public UOINode addMember(@RequestBody UOINode uoiNode) {
        uoiRepository.save(uoiNode);
        return uoiNode;
    }

    @GetMapping("/demoNodes")
    public String executeDemoNode() {
        uoiRepository.deleteAll();
        service.demoNodes(uoiRepository);
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503104983629824/Untitled_Diagram1.png'/> " +
                "</body></html>";
    }

    @GetMapping("/scenarioCombine")
    public String executeDemoNodeCombineTwoRooms() {
        uoiRepository.deleteAll();
        service.demoNodesCombineTwoRooms(uoiRepository);
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503102551064576/Untitled_Diagram3.png'/> " +
                "</body></html>";
    }

    @GetMapping("/clearDB")
    public String clearDB() {
        uoiRepository.deleteAll();
        return "DB has been cleared!";
    }

    @GetMapping("/")
    public String error() {
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/675683560673509389/776827370161700874/addtext_com_MTAwMTI1MzExODY.jpg'/> " +
                "</body></html>";

    }

    @GetMapping("/scenarioAddANewRoom")
    public String executeDemoNodeAddANewRoom() {
        uoiRepository.deleteAll();
//        service.demoNodesAddANewRoom(uoiRepository);
        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503092572553226/Untitled_Diagram4.png'/> " +
                "</body></html>";
    }



}
