package uoi_project;

public class UBID {
   private String centroid;
   private String north_extent;
   private String east_extent;
   private String south_extent;
   private String west_extent;

   public UBID(String centroid, String north_extent, String east_extent, String south_extent, String west_extent){
       this.centroid = centroid;
       this.north_extent = north_extent;
       this.east_extent = east_extent;
       this.south_extent = south_extent;
       this.west_extent = west_extent;
   }

   public String getUBID(){
       return centroid + "-" + north_extent + "-" + east_extent + "-" + south_extent + "-" + west_extent;
   }
}
