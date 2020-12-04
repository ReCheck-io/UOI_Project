# UOI_Project

UOI - Unique object identifier, is an ID that would represent an element up to a whole building in an estate. You can learn more about it in the educational resources. 

# [Educational resources](https://github.com/ReCheck-io/UOI_Project/tree/master/educational_resources) 

It is important that people get familiar with what is a UOI and why it is important. You can find a link with the problem found in the real estate and the solution this identificator is going to solve [by clicking right here](https://www.youtube.com/watch?v=aKcuLQ8Weyk). 

[In the folder **educational_resources** you can find more information. Or by clicking here.](https://github.com/ReCheck-io/UOI_Project/tree/master/educational_resources)

## Road Map : 

- properly code all methods 

- finish the APIs


## Tech Stack
- Java - 1.8
- neo4j - 3.5.22
- neo4j driver - 4.1.1

### uoi_nodes structure
 
 ``` {
 length: 
 height: 
 width:
 materials: {
             }
 child:
 parent: 
 Physical ID (PID)
 Timestamp
 role
 owner
 history
 }
```

### Relationships Structure

- CONSISTS_OF / PART_OF
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
