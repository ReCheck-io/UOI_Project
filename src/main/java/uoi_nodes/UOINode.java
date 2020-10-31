package uoi_nodes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

@NodeEntity
public class UOINode {

    String uuid;
    double length;
    double height;
    double width;
    List materials;
    List physicalID;
    String timestamp;
    String owner;
    String tenant;

    @JsonIgnoreProperties("UOINode")
    @Relationship("PART_OF")
    String parent = new UOINode().getUuid();

    @JsonIgnoreProperties("UOINode")
    @Relationship("CONSISTS_OF")
    List child = new List<UOINode>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<UOINode> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return null;
        }

        @Override
        public boolean add(UOINode uoiNode) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends UOINode> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, Collection<? extends UOINode> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public UOINode get(int i) {
            return null;
        }

        @Override
        public UOINode set(int i, UOINode uoiNode) {
            return null;
        }

        @Override
        public void add(int i, UOINode uoiNode) {

        }

        @Override
        public UOINode remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<UOINode> listIterator() {
            return null;
        }

        @Override
        public ListIterator<UOINode> listIterator(int i) {
            return null;
        }

        @Override
        public List<UOINode> subList(int i, int i1) {
            return null;
        }
    };

    @JsonIgnoreProperties("UOINode")
    @Relationship("HISTORY_OF")
    String historyUOI = new UOINode().getUuid();


    // physicalID 1:1 UOI
    // physicalID da e unique
    // dali moje neo4j da tyrsi po partial string matching
    // dali moje neo4j da tyrsi po List string matching
    // da namerq UOI v koito PID e v spisyk


    public UOINode(String parent, String timestamp) {
        //parent
        this.parent = parent;
        this.uuid = "NL " + UUID.randomUUID();
        this.timestamp = timestamp;
    }

    public UOINode() {
        this.uuid = "NL " + UUID.randomUUID();
    }

    public String getUuid() {
        return uuid;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public List getMaterials() {
        return materials;
    }

    public void setMaterials(List materials) {
        this.materials = materials;
    }

    public List getChild() {
        return child;
    }

    public void setChild(List child) {
        this.child = child;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List getPhysicalID() {
        return physicalID;
    }

    public void setPhysicalID(List physicalID) {
        this.physicalID = physicalID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }


}
