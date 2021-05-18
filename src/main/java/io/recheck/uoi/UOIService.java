package io.recheck.uoi;

import io.recheck.uoi.dto.*;
import io.recheck.uoi.exceptions.NodeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.recheck.uoi.entity.UOINode;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service

public class UOIService {

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
        if (result.size() == 0) {
            throw new NodeNotFoundException("There are no nodes that are owned by " + owner);
        } else {
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
                        if (node.getProperties().get(uoiSearchByPropertiesDTO.getKey()).equals(uoiSearchByPropertiesDTO.getValue())) {
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

    public ArrayList getNodeChildren(GetChildrenDTO getChildrenDTO) throws NodeNotFoundException {
        ArrayList<UOINode> result = new ArrayList<>();
        UOINode node = uoiRepository.findByUoi(getChildrenDTO.getUoi());
        if (node == null) {
            throw new NodeNotFoundException("The node has not been found in the database.");
        }
        List<String> children = node.getChildren();
        if (getChildrenDTO.getLevel() != null) {
            for (String child : children) {
                UOINode childNode = uoiRepository.findByUoi(child);
                if (childNode.getLevel().equals(getChildrenDTO.getLevel())) {
                    result.add(childNode);
                }
            }
            if (result.size() == 0) {
                throw new NodeNotFoundException("There are no children found at this level.");
            } else {
                return result;
            }

        } else {
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
        if (result.size() == 0) {
            throw new NodeNotFoundException("There are no nodes that are classified as " + uoiClass);
        } else {
            return result;
        }

    }

}
