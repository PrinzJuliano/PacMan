class Player {

  PVector pos;
  PVector direction;
  float movementSpeed;
  int playerWidth;
  int playerHeight;

  StringDict spawnData;
  Map map;

  public Player(Map m) {
    this.playerWidth = 12;
    this.playerHeight = 12;
    
    movementSpeed = 0.1f;
    
    this.map = m;
    this.spawnData = map.getObjectByName(Map.SPAWNS, "Player");
    float x = parseFloat(this.spawnData.get("x"));
    float y = parseFloat(this.spawnData.get("y"));
    this.pos = new PVector(x, y); 
    this.direction = new PVector(0, 0);
  }

  public Direction getDirection() {
    if (direction.x < 0) {
      return Direction.LEFT;
    } else if (direction.x > 0) {
      return Direction.RIGHT;
    } else if (direction.y < 0) {
      return Direction.UP;
    } else {
      return Direction.DOWN;
    }
  }

  public void setSpeed(Direction d) {
    switch(d) {
    case UP:
      this.direction.x = 0;
      this.direction.y = -1;
      break;
    case DOWN:
      this.direction.x = 0;
      this.direction.y = 1;
      break;
    case RIGHT:
      this.direction.x = 1;
      this.direction.y = 0;
      break;
    case LEFT:
      this.direction.x = -1;
      this.direction.y = 0;
      break;
    default:
      this.direction.x = 0;
      this.direction.y = 0;
    }
  }

  public void update() {
    PVector speed = this.direction.copy().mult(movementSpeed * delta);
    
    PVector nextTile = map.positionToTileCoordinate(this.pos.copy());
    
    switch(getDirection()){
       case UP:
         nextTile.y -= 1;
       break;
       case DOWN:
         nextTile.y += 1;
         break;
       case LEFT:
         nextTile.x -= 1;
         break;
       case RIGHT:
         nextTile.x += 1;
         break;
       case NONE:
         break;
    }
    
    //Check if nextPos is a wall
    if(map.getMap().getTileIndex(Map.WALLS, (int)nextTile.x, (int)nextTile.y) != -1)
    {
        //Clamp player to that pos
        
        PVector tileCoord = map.positionToTileCoordinate(this.pos);
        this.pos = map.tileCoordinateToPosition((int)tileCoord.x, (int)tileCoord.y);
        this.setSpeed(Direction.NONE);
        
    } else {
    
      pos.add(speed);
    }
  }

  public void draw(PGraphics gfx) {
    gfx.beginDraw();
    gfx.pushMatrix();
    gfx.ellipseMode(CENTER);
    
    
    gfx.fill(255, 255, 0);
    gfx.noStroke();
    gfx.ellipse(pos.x+playerWidth/2 - 2, pos.y + playerHeight/2 - 2, playerWidth, playerHeight);
    
    

    gfx.popMatrix();
    gfx.endDraw();
  }
}