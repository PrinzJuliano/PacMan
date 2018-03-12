import ptmx.*;

float lastTime = 0;
float delta = 0;

Map map;
Player player;
PGraphics gfx;

void setup() {
  size(224, 248, P2D);
  surface.setResizable(true);
  frame.setLocationRelativeTo(null);

  map = new Map(new Ptmx(this, "data/map.tmx"));
  
  PVector mapSize = map.getPixelPerfectSize();
  
  player = new Player(map);

  gfx = createGraphics((int)mapSize.x, (int)mapSize.y);
}

void scaleImageUp(){
  PVector mapSize = map.getPixelPerfectSize();
  //calculate scaling factor for pixel perfect scaling
  float scaleX = width/mapSize.x;
  float scaleY = height/mapSize.y;
  float smalestScale = min(scaleX, scaleY);

  int actualWidth = (int)(mapSize.x*smalestScale);
  int actualHeight = (int)(mapSize.y*smalestScale);

  float offsetX = (width-actualWidth)/2.0f;
  float offsetY = (height-actualHeight)/2.0f;

  image(gfx, offsetX, offsetY, actualWidth, actualHeight);
}

void keyPressed(){
   if(key == 'W' || key == 'w'){
      player.setSpeed(Direction.UP); 
   }
   if(key == 'A' || key == 'a'){
      player.setSpeed(Direction.LEFT); 
   }
   if(key == 'S' || key == 's'){
      player.setSpeed(Direction.DOWN); 
   }
   if(key == 'D' || key == 'd'){
      player.setSpeed(Direction.RIGHT); 
   }
}

void draw() {
  delta = millis() - lastTime;
  background(0); 
  
  //Rendering
  map.draw(gfx);
  
  player.update();
  player.draw(gfx);
  
  //Scaling
  scaleImageUp();
  lastTime = millis();
  
}