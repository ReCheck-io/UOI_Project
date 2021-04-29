package io.recheck.uoi;


import io.recheck.uoi.dto.*;
import io.recheck.uoi.exceptions.GeneralErrorException;
import io.recheck.uoi.exceptionhandler.RestExceptionHandler;
import io.recheck.uoi.exceptions.NodeNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.recheck.uoi.entity.LEVEL;
import io.recheck.uoi.entity.UOINode;

import java.util.ArrayList;

@Slf4j
@RestController
public class UOIController {

    @Autowired
    RestExceptionHandler restExceptionHandler;

    @Autowired
    UOIRepository uoiRepository;

    @Autowired
    UOIService service;

    @Operation(summary = "Creating a new basic UOI without metadata.")
    @Tag(name = "Create")
    @GetMapping("/new")
    public UOINode generateNewUOI(@RequestParam(value = "countryCode", defaultValue = "NL") String countryCode,
                                  @RequestParam(value = "level", defaultValue = "ROOM") LEVEL level,
                                  @RequestParam(value = "owner", required = false) String owner,
                                  @RequestParam(value = "uoiClass", required = false) String uoiClass,
                                  @RequestParam(value = "parentUOI", required = false) String parentUOI) throws Exception {
        return service.generateNewUOI( new NewUOIDTO(countryCode, level, owner, uoiClass, parentUOI));
    }

    @Operation(summary = "Search for a UOI node by UOI or property.")
    @Tag(name = "Search")
    @GetMapping("/search/uoi")
    public Object getNodes(@RequestParam(value = "uoi") String uoi) throws NodeNotFoundException {
        return service.search(uoi);
    }

    @Operation(summary = "adding properties to a node.")
    @Tag(name = "Update")
    @PutMapping("/node/properties")
    public Object putNodeProperties(@RequestBody UOIPutRequestDTO uoiPutRequestDTO) throws NodeNotFoundException {
        return service.putProperties(uoiPutRequestDTO);
    }

    @Operation(summary = "Search for UOI by existing properties.")
    @Tag(name = "Search")
    @GetMapping("/search/properties")
    public Object getNodeByProps(@RequestParam(value = "key") String key,
                                 @RequestParam(value = "value") String value,
                                 @RequestParam(value = "withMetaData" , defaultValue = "false") boolean withMetaData) throws NodeNotFoundException {


            return service.searchByProperties( new UOISearchByPropertiesDTO(key, value, withMetaData));
    }

    @Operation(summary = "Make a child-parent relationship between nodes.")
    @Tag(name = "Update")
    @PutMapping("/node/relationship")
    public Object nodePartOfAnother(@RequestBody UOIRelationshipDTO uoiRelationshipDTO) throws NodeNotFoundException {
        return service.makeRelationship(uoiRelationshipDTO);
    }


    @PostMapping(path = "/newWithInformation", consumes = "application/json", produces = "application/json")
    public UOINode addMember(@RequestBody UOINode uoiNode) {
        uoiRepository.save(uoiNode);
        return uoiNode;
    }

    @Operation(summary = "Search for UOIs that are owned by user:")
    @Tag(name = "Search")
    @GetMapping(path = "/search/owner")
    public Object getNodesByOwner(@RequestParam(value = "owner") String owner) throws NodeNotFoundException {
        return service.searchByOwner(owner);
    }

    @Operation(summary = "Search for UOIs that are owned by user:")
    @Tag(name = "Search")
    @GetMapping(path = "/search/uoiClass")
    public Object getNodesByUoiClass(@RequestParam(value = "uoiClass") String uoiClass) throws NodeNotFoundException {
        return service.searchByUoiClass(uoiClass);
    }

    @Operation(summary = "Retrieve information about the UOI's children, if Level provided, it will be level dependent.")
    @Tag(name = "Retrieve")
    @GetMapping(path = "/retrieve/uoi/children")
    public ArrayList getNodeChildren(@RequestParam(value = "uoi") String uoi,
                                     @RequestParam(value = "level" , required = false) LEVEL level) throws NodeNotFoundException {
        return service.getNodeChildren( new GetChildrenDTO(uoi, level));
    }

    @Operation(summary = "Set the owner of the specific UOI node.")
    @Tag(name = "Update")
    @PutMapping(path = "/set/node/owner")
    public Object setNodeOwner(@RequestBody SetNodeOwnerDTO setNodeOwnerDTO) throws NodeNotFoundException {
       return service.setNodeOwner(setNodeOwnerDTO);
    }

    @Operation(summary = "The system and user are going to be sent to an external system to retrieve information.")
    @Tag(name = "Requests for token")
    @PostMapping(path = "/uoi")
    public void requestAccess(@RequestBody RequestAccessDTO requestAccessDTO){
        service.requestAccess(requestAccessDTO);
    }

    @Tag(name = "Test")
    @GetMapping(path = "/testCheckToken")
    public void testCheck(){
        service.checkToken(new CheckTokenDTO("uoi", "token"));
    }

    @Tag(name = "Test")
    @GetMapping(path = "/testRequestToken")
    public void testRequest(@RequestParam(value = "uoi") String uoi){
        service.requestToken(new RequestAccessDTO(uoi, "systemID","userId"));
    }

    @Tag(name = "Test")
    @GetMapping(path = "/testQueryDocuments")
    public void testRequestDocuments(){
        service.queryForDocuments(new CheckTokenDTO("uoi", "token"));
    }

    @Tag(name = "Test")
    @GetMapping(path = "/testQuerySingleDocument")
    public void testRequestSigleDocument(){
        service.queryForSingleDocument(new GetDocumentDTO("uoi", "token", "document"));
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

    @Hidden
    @GetMapping("/clearDB")
    public String clearDB() {
        uoiRepository.deleteAll();
        return "DB has been cleared!";
    }

    @Hidden
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
