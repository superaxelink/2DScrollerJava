package enemy;

import entity.Entity;
import main.GamePanel;
import surrounding.Background;

public class GreenSlime extends Entity{

  public int groundLine;

  //Slime states
  public enum MonsterState{
    Attack_1,
    Attack_2,
    Attack_3,
    Dead,
    Hurt,
    Idle_1,
    Jump,
    Run,
    RunAttack,
    Walk
  }

  public GreenSlime(GamePanel gp, Background background){
    this.gp = gp;
    this.background = background;
  }

  public void setDefaultValues(){

  }

  public void update(){}

  public void gravity(){}

  public void checkDirection(){}

  public void horizontalMovent(){}

  public boolean onGround(){
    return false;
  }

  public void draw(){}

  public void initStates(){}

}
