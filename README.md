# UOI_Project


## Road Map : 

Creation of the uoi_nodes and storing it on the graph db till 23th

browse and traverse (API)- till 30th

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
