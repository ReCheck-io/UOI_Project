package io.recheck.uoi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.Properties;

import java.util.*;

@Data
@NoArgsConstructor
@NodeEntity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UOINode {

    @Id
    @GeneratedValue
    @Setter @Getter (AccessLevel.PRIVATE)private Long id;
    private String uoi;
    @Properties
    private Map<String, String> properties;
    private String timestamp;
    private String owner;
    private String parentUOI = null;
    private String uoiClass;
    private String countryCode;
    private LEVEL level;
//    private ArrayList children = new ArrayList();

    public UOINode(String countryCode, LEVEL level, String uoiClass) {
        this.uoi = countryCode + "." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.uoiClass = uoiClass;
    }

    public UOINode(String countryCode, LEVEL level, String owner, String uoiClass) {
        this.uoi = countryCode + "." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.uoiClass = uoiClass;
        this.owner = owner;
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "PART_OF", direction = Relationship.OUTGOING)
    @Setter @Getter (AccessLevel.PRIVATE)
    private UOINode parent = null;

    public UOINode(LEVEL level, String owner) {
        this.uoi = "NL." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.owner = owner;
    }

    public void partOf(UOINode parent) {
        setParent(parent);
        setParentUOI(parent.getUoi());
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "CONSISTS_OF", direction = Relationship.OUTGOING)
    private List<String> children = new ArrayList<>();

    public void consistsOf(UOINode childUOI) {
        if (this.getChildren() == null) {
            this.setChildren(children);
        }

        if (!this.getChildren().contains(childUOI.getUoi())) {
            this.getChildren().add(childUOI.getUoi());

        }else {
            System.out.println("The node is already registered as a child");
        }
    }

//    @JsonIgnoreProperties("UOINode")
//    @Relationship(type = "HISTORY_OF", direction = Relationship.OUTGOING)
//    private UOINode historyOf = null;

//    public void historyOf(UOINode presentUOI) {
//        setHistoryOf(presentUOI);
//    }


    public UOINode(LEVEL level) {
        this.uoi = "NL." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
    }

    public void addMoreProperties(String key, String value) {
        if (this.properties != null) {
            this.properties.put(key, value);
        } else {
            this.properties = new HashMap();
            this.properties.put(key, value);
        }

    }



}
