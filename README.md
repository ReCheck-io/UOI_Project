# UOI_Project

UOI - Unique object identifier, is an ID that would represent an element up to a whole building in an estate. You can learn more about it in the educational resources. 

# [Educational resources](https://github.com/ReCheck-io/UOI_Project/tree/master/educational_resources) 

It is important that people get familiar with what is a UOI and why it is important. You can find a link with the problem found in the real estate and the solution this identificator is going to solve [by clicking right here](https://www.youtube.com/watch?v=aKcuLQ8Weyk). 

[In the folder **educational_resources** you can find more information. Or by clicking here.](https://github.com/ReCheck-io/UOI_Project/tree/master/educational_resources)

## ALPHA API: 
- https://uoi.recheck.io/swagger-ui/index.html?configUrl=/api-docs/swagger-config


## Road Map : 

- creating a Graphical demo interface 


## Tech Stack
- Java - 1.8
- neo4j - 3.5.22
- neo4j driver - 4.1.1

### uoi_nodes structure
 
 ``` {
    "uuid": "NL.dfb2d5f4-89e3-43b0-840d-7c1cc57aa014",
    "timestamp": "1614179433096",
    "owner": null,
    "level": "BUILDING",
    “parentUOI: “NL.f68d91e6-2a0d-4055-8a0f-bc90a6d939ab”,
    "children": [],
    "historyOf": null,
    "uoiClass": "Shop",
    "properties" : [
        {"length": 0},
        {"height": 0},
        {"width": 0},
        {"materials": null},
        {"tenant": null},
        {"address": null},
        {"longitude": 0},
        {"latitude": 0},
        {"resources": null},        
        {"BAG" : "123123213"},
        {"Company Owner" : "Hello Inc"}, 
        {"VAT" : "123123123213"}
    ]
}

```

### Relationships Structure

- CONSISTS_OF - a UOI that consists of another UOI (Flat consists of Room)

- PART_OF - a UOI that is part of another UOI (Room is part of Flat)
```
    {
        Timestamp: 
    }
```

- HISTORY_OF
```
    {
        Timestamp: 
        validated: t/f 
    }
```
