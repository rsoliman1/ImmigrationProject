package edu.gmu.cs321;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ImmigrantTest 
{
    @Test
    public void testcheckAnum()
    {
        Immigrant testImm = new Immigrant(); 
        boolean testRest = testImm.checkANum("1");
        assertTrue( testRest );
    }  
    
    @Test
    public void testcheckAnumRev()
    {
        ImmigrantRev testImm = new ImmigrantRev(); 
        testImm.setANumber("1");
        boolean testRest = testImm.checkANum("1");
        assertTrue( testRest );
    } 
}
