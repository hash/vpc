package vpc_service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;



public class Command {
    private  Robot r;
    
    public Command() throws AWTException {
        r = new Robot();
    }
    public void close()
    {
         r.keyPress(KeyEvent.VK_ALT);
         r.keyPress(KeyEvent.VK_F4);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_ALT);
         r.keyRelease(KeyEvent.VK_F4);
    }
    public void hideAll()
    {

         r.keyPress(KeyEvent.VK_WINDOWS);
         r.keyPress(KeyEvent.VK_M);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_WINDOWS);
         r.keyRelease(KeyEvent.VK_M);  
    }
    public void showAll()
    {     
         r.keyPress(KeyEvent.VK_WINDOWS);
         r.keyPress(KeyEvent.VK_SHIFT);
         r.keyPress(KeyEvent.VK_M);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_WINDOWS);
         r.keyRelease(KeyEvent.VK_SHIFT); 
         r.keyRelease(KeyEvent.VK_M);  
    
    }
    public void scrollDown()
    {
    
         r.keyPress(KeyEvent.VK_PAGE_DOWN);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_PAGE_DOWN); 
    }
    public void scrollUp()
    {
          
         r.keyPress(KeyEvent.VK_PAGE_UP);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_PAGE_UP);  
    }
    public void search()
    {
         r.keyPress(KeyEvent.VK_CONTROL);
         r.keyPress(KeyEvent.VK_F);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_CONTROL);  
         r.keyRelease(KeyEvent.VK_F);  
    }
    public void switchRight()
    {
         r.keyPress(KeyEvent.VK_ALT);
         r.keyPress(KeyEvent.VK_TAB);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_ALT);  
         r.keyRelease(KeyEvent.VK_TAB);  
    }
    public void switchLeft()
    {
         r.keyPress(KeyEvent.VK_ALT);
         r.keyPress(KeyEvent.VK_SHIFT);
         r.keyPress(KeyEvent.VK_TAB);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_ALT);  
         r.keyRelease(KeyEvent.VK_SHIFT); 
         r.keyRelease(KeyEvent.VK_TAB);
    }
    public void save()
    {
         r.keyPress(KeyEvent.VK_CONTROL);
         r.keyPress(KeyEvent.VK_S);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_CONTROL);  
         r.keyRelease(KeyEvent.VK_S);  
    }
    
}           