class Map {

  public static final int GROUND = 0,
                          WALLS = 1,
                          COLLECTABLES = 2,
                          SPAWNS = 3,
                          NODES = 4;
  
  Ptmx rawMap;
  
  ArrayList<Path> paths;
  ArrayList<Node> nodes;

  public Map(Ptmx map) {
    this.rawMap = map;
    nodes = new ArrayList<Node>();
    paths = new ArrayList<Path>();
    
    for(StringDict dict : map.getObjects(NODES)) {
        int id = parseInt(dict.get("name"));
        int left = parseInt(dict.get("left"));
        int right = parseInt(dict.get("right"));
        int top = parseInt(dict.get("top"));
        int bottom = parseInt(dict.get("bottom"));
        int x = parseInt(dict.get("x"));
        int y = parseInt(dict.get("y"));
        PVector pos = new PVector(x, y);
        
        nodes.add(new Node(id, pos, left, top, right, bottom));
    }
    
    for(Node n : nodes){
        if(n.left != -1){
            
        }
    }
  }
  
  public Ptmx getMap(){
     return this.rawMap; 
  }
  
  public StringDict[] getDataFromLayer(int layer){
     return this.rawMap.getObjects(layer); 
  }
  
  public StringDict[] getObjectsByType(int layer, String type){
     StringDict[] allObjs = this.getDataFromLayer(layer);
     ArrayList<StringDict> filteredResults = new ArrayList<StringDict>();
     
     for(StringDict dict : allObjs){
        if( dict.hasKey("type") && dict.get("type").equalsIgnoreCase(type)){
           filteredResults.add(dict); 
        }
     }
     
     return (StringDict[])filteredResults.toArray();
  }
  
  public StringDict getObjectByName(int layer, String name){
    StringDict[] allObjs = this.getDataFromLayer(layer);
    for(StringDict dict : allObjs){
      if( dict.hasKey("name") && dict.get("name").equalsIgnoreCase(name)){
          return dict;
      }
    }
    return null;
  }
  
  public PVector getPixelPerfectSize(){
      PVector mapSize = this.getMap().getMapSize().copy();
      mapSize.x *= this.getMap().getTileSize().x;
      mapSize.y *= this.getMap().getTileSize().y;
      
      return mapSize;
  }
  
  public PVector tileCoordinateToPosition(int x, int y){
      PVector pos = new PVector(x, y);
      pos.x *= rawMap.getTileSize().x;
      pos.y *= rawMap.getTileSize().y;
      return pos;
  }
  
  public PVector positionToTileCoordinate(float x, float y){
     PVector tileCoord = new PVector();
     tileCoord.x = (int)(x/rawMap.getTileSize().x);
     tileCoord.y = (int)(y/rawMap.getTileSize().y);
     return tileCoord;
  }
  
  public PVector positionToTileCoordinate(PVector pos){
     return positionToTileCoordinate(pos.x, pos.y); 
  }
  

  public void draw(PGraphics gfx) {
    gfx.beginDraw();
    gfx.background(0);
    gfx.endDraw();
    
    rawMap.draw(gfx, 0, 0);
    
    gfx.beginDraw();
    
    
    
    gfx.endDraw();
  }
}