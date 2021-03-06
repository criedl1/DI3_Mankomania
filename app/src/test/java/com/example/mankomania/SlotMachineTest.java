package com.example.mankomania;

import com.example.mankomania.slotmachine.SlotMachineActivity;
import com.example.mankomania.slotmachine.SlotMachineLogic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlotMachineTest {

    SlotMachineLogic sml;
    SlotMachineActivity sma; //i want to test this too, because i want to see if SlotMachineLogic
                            //changes the values in SlotMachineActivity correctly.

    @Before
    public void setUp(){
        sma = new SlotMachineActivity();
    }

    @Test
    public void test3DollarSignsMoney(){
        sml = new SlotMachineLogic(3, 3, 3, sma);
        sml.checkWin();
        assertEquals(230000, sml.getMoney());
        assertEquals(230000, sma.getMoney());
    }

    @Test
    public void testThreeEqualSymbols(){
        sml = new SlotMachineLogic(1, 1, 1, sma);
        sml.checkWin();
        assertEquals(130000, sml.getMoney());
        assertEquals(130000, sma.getMoney());
    }

    @Test
    public void testTwoEqualSymbolsVar1(){
        //condition id1 == id2
        sml = new SlotMachineLogic(2, 2, 0, sma);
        sml.checkWin();
        assertEquals(30000, sml.getMoney());
        assertEquals(30000, sma.getMoney());
    }

    @Test
    public void testTwoEqualSymbolsVar2(){
        //condition id2 == id3
        sml = new SlotMachineLogic(0, 1, 1, sma);
        sml.checkWin();
        assertEquals(30000, sml.getMoney());
        assertEquals(30000, sma.getMoney());
    }

    @Test
    public void testTwoEqualSymbolsVar3(){
        //condition id1 == id3
        sml = new SlotMachineLogic(3, 0, 3, sma);
        sml.checkWin();
        assertEquals(30000, sml.getMoney());
        assertEquals(30000, sma.getMoney());
    }

    @Test
    public void testLost(){
        sml = new SlotMachineLogic(1, 2, 3, sma);
        sml.checkWin();
        assertEquals(-20000, sml.getMoney());
        assertEquals(-20000, sma.getMoney());
    }

    @Test
    public void testReturnString(){
        sml = new SlotMachineLogic(3, 3, 3, sma);
        sml.checkWin();
        assertEquals("Hauptgewinn! Du hast 230000 Gewonnen!", sml.getReturnString());
        assertEquals("Hauptgewinn! Du hast 230000 Gewonnen!", sma.getReturnString());
    }

    @Test
    public void testWinString(){
        sml = new SlotMachineLogic(1, 1, 1, sma);
        sml.checkWin();
        assertEquals(" Gewonnen!", sml.getWinString());
        assertEquals(" Gewonnen!", sma.getWinString());
    }
}
