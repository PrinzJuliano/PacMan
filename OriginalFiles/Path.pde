class Path {
   Node start;
   Node end;
   
   public Path(Node start, Node end){
      this.start = start;
      this.end = end;
   }
   
   public Node getStart(){
      return  start;
   }
   
   public Node getEnd(){
      return end; 
   }
}