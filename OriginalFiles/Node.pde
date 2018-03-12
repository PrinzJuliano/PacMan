class Node {
   PVector position;
   int id;
   int left;
   int right;
   int top;
   int bottom;
   
   public Node(int id, PVector pos, int left, int top, int right, int bottom){
       this.id = id;
       this.position = pos;
       this.left = left;
       this.right = right;
       this.top = top;
       this.bottom = bottom;
   }
}