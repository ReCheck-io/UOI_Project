package io.recheck.uoi;


import io.recheck.uoi.dto.UOIPutRequestDTO;
import io.recheck.uoi.dto.UOIRelationshipDTO;
import io.recheck.uoi.exceptions.GeneralErrorException;
import io.recheck.uoi.exceptionhandler.RestExceptionHandler;
import io.recheck.uoi.exceptions.NodeNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;

@RestController
public class UOIController {

    @Autowired
    RestExceptionHandler restExceptionHandler;

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
    public Object getNodes(@RequestParam(value = "uoi") String uoi) throws NodeNotFoundException {
        return service.search(uoi);
    }

    @Operation(summary = "adding properties to a node.")
    @PutMapping("/node/properties")
    public UOINode putNodeProperties(@RequestBody UOIPutRequestDTO uoiPutRequestDTO) throws NodeNotFoundException {
        return service.putProperties(uoiPutRequestDTO);

    }

    @Operation(summary = "Search for UOI by existing properties.")
    @GetMapping("/search/properties")
    public Object getNodeByProps(@RequestParam(value = "key") String key,
                                 @RequestParam(value = "value") String value,
                                 @RequestParam(value = "withMetaData" , defaultValue = "false") boolean withMetaData) throws GeneralErrorException {

            return service.searchByProperties(key, value, withMetaData);


    }


    @PutMapping("/node/relationship")
    public String nodePartOfAnother(@RequestBody UOIRelationshipDTO uoiRelationshipDTO) throws NodeNotFoundException {
        return service.makeRelationship(uoiRelationshipDTO);
    }

    @PostMapping(path = "/newWithInformation", consumes = "application/json", produces = "application/json")
    public UOINode addMember(@RequestBody UOINode uoiNode) {
        uoiRepository.save(uoiNode);
        return uoiNode;
    }

//    @GetMapping("/demoNodes")
//    public String executeDemoNode() {
//        uoiRepository.deleteAll();
//        service.demoNodes(uoiRepository);
//        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503104983629824/Untitled_Diagram1.png'/> " +
//                "</body></html>";
//    }
//
//    @GetMapping("/scenarioCombine")
//    public String executeDemoNodeCombineTwoRooms() {
//        uoiRepository.deleteAll();
//        service.demoNodesCombineTwoRooms(uoiRepository);
//        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503102551064576/Untitled_Diagram3.png'/> " +
//                "</body></html>";
//    }

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

//    @GetMapping("/scenarioAddANewRoom")
//    public String executeDemoNodeAddANewRoom() {
//        uoiRepository.deleteAll();
//        service.demoNodesAddANewRoom(uoiRepository);
//        return "<html><body>" + "<img src='https://cdn.discordapp.com/attachments/709719423094751313/777503092572553226/Untitled_Diagram4.png'/> " +
//                "</body></html>";
//    }



}
