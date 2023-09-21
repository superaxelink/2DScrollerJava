package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateHurt extends States{

  public StateHurt(GamePanel gp, Player player) {
    //parent class constructor
    super(gp, player);

    //basic characteristics

    spriteThreshold=6;
    totalSprites = 3;
    baseWidth = 384;
    baseHeight = 128;
    dividedWidth = baseWidth/totalSprites;

    height = 73;
    yStartSubimage = 55;

    widthOffset = 0;
    width = dividedWidth;
    
    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Hurt", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/HurtLeft", baseWidth, baseHeight);
  }
  
  public void update(){
    String direction = player.direction;
    frameCounter++;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      if(direction.equals("left")){
        if(spriteCounter>0){
          spriteCounter--;
        }
      }
      else if(direction.equals("right")){
        if(spriteCounter<totalSprites-1){
          spriteCounter++;
        }
      }
    }
  }

  public void inputHandler(String direction){
    if(gp.keyH.keys.size()!=0){
      if(gp.keyH.keys.contains("left") || gp.keyH.keys.contains("right")){
        changeState(PlayerState.Walk.ordinal(),direction);
      }
      if(gp.keyH.keys.contains("up")){
        changeState(PlayerState.Jump.ordinal(),direction);
      }
    }else{
      changeState(PlayerState.Idle_1.ordinal(),direction);
    }
  }
}
