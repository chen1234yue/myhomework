//package test;
import java.*;
import org.junit.*;
import java.util.ArrayList;

import static org.junit.Assert.fail;

public class PlayerTest {
    Huluwa hu;
    Field field;
    private final int OFFSET = 240;
    public PlayerTest()
    {
        field=new Field();
        hu = new Huluwa(0,0,field,1);
        hu.setEnemyFriend(field.getHuluwaTeam(),field.getSnakeTeam());
    }
    @BeforeClass
    public static void classBefore()
    {
        System.out.println("Test Begin------");
    }
    @AfterClass
    public static void classAfter()
    {
        System.out.println("Test End-------");
    }

    public void testPosition()
    {
        if(hu.x()<0||hu.x()+60>field.getBoardWidth()||hu.y()<0||hu.y()+60>field.getBoardHeight())
            fail("Position out bounder.");
        ArrayList world = new ArrayList();

        world.addAll(field.getHuluwaTeam());
        world.addAll(field.getSnakeTeam());
        for (int i = 0; i < world.size(); i++) {

            Player item = (Player) world.get(i);
            if (hu.samePlace(item)) {
                fail("Two Players stand in the same Place.");
            }
        }
    }
    @Test
    public void testMove()
    {
        //System.out.println(field.getWidth()+" "+);
        hu.moveto(0,0);
        for(int i=0;i<10;i++) {
            hu.move(Player.Direction.UP);
          //  System.out.println(hu.x()+" "+hu.y());
            testPosition();
        }
        hu.moveto(0,0);
        for(int i=0;i<10;i++) {
            hu.move(Player.Direction.DOWN);
            testPosition();;
        }
        hu.moveto(0,0);
        for(int i=0;i<10;i++) {
            hu.move(Player.Direction.LEFT);
            testPosition();
        }
        hu.moveto(0,0);
        for(int i=0;i<10;i++) {
            hu.move(Player.Direction.RIGHT);
            testPosition();
        }
        System.out.println("Test Move Pass");
    }
    @Test
    public void testFight()
    {
         Snake t=new Snake(0,0,field);
         Littlell s = new Littlell(0,0,field,1);
         hu.fight(s);
         if((hu.isDie())||(!s.isDie()))
            fail("Test Fight Error.");
         hu.moveto(0,0);
         hu.fight(t);
         if((!hu.isDie())||t.isDie())
             fail("Test Fight Error.");
         System.out.println("Test Fight Pass.");
    }
}
