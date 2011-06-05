package vpc_service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
/**
 * Klasa obsługi wbudowanych komend emulujących używanie skrótów klawiszowych Windows.
 */
public class Command {
    /**
     * Obiekt klasy Robot umożliwiający emulację wciśniecia klawisza.
     */
    private  Robot r;
    /**
     * Konstruktor inicjalizujący obiekt klasy Robot
     * @throws AWTException Błąd inicjalizacji obiektu Robot
     */
    public Command() throws AWTException {
        r = new Robot();
    }
    /**
     * Emulacja wciśnięcia klawiszy Alt+F4 (zamknij)
     */
    public void close()
    {
         r.keyPress(KeyEvent.VK_ALT);
         r.keyPress(KeyEvent.VK_F4);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_ALT);
         r.keyRelease(KeyEvent.VK_F4);
    }
    /**
     * Emulacja wciśnięcia klawiszy Super+M (zminimalizuj wszystko)
     */
    public void hideAll()
    {

         r.keyPress(KeyEvent.VK_WINDOWS);
         r.keyPress(KeyEvent.VK_M);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_WINDOWS);
         r.keyRelease(KeyEvent.VK_M);  
    }
    /**
     * Emulacja wciśnięcia klawiszy Super+Shift+M (cofnij minimalizację)
     */
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
    /**
     * Emulacja wciśnięcia klawisza Page Down
     */
    public void scrollDown()
    {
    
         r.keyPress(KeyEvent.VK_PAGE_DOWN);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_PAGE_DOWN); 
    }
    /**
     * Emulacja wciśnięcia klawisza Page Up
     */
    public void scrollUp()
    {
          
         r.keyPress(KeyEvent.VK_PAGE_UP);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_PAGE_UP);  
    }
    /**
     * Emulacja wciśnięcia klawiszy Ctrl+F (wyszukaj)
     */
    public void search()
    {
         r.keyPress(KeyEvent.VK_CONTROL);
         r.keyPress(KeyEvent.VK_F);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_CONTROL);  
         r.keyRelease(KeyEvent.VK_F);  
    }
    /**
     * Emulacja wciśnięcia klawiszy Alt+Tab (przełącz następną aplikację - w prawo)
     */
    public void switchRight()
    {
         r.keyPress(KeyEvent.VK_ALT);
         r.keyPress(KeyEvent.VK_TAB);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_ALT);  
         r.keyRelease(KeyEvent.VK_TAB);  
    }
    /**
     * Emulacja wciśnięcia klawiszy Alt+Shift+Tab (przełącz poprzednią aplikację - w lewo)
     */
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
    /**
     * Emulacja wciśnięcia klawiszy Ctrl+S (zapisz)
     */
    public void save()
    {
         r.keyPress(KeyEvent.VK_CONTROL);
         r.keyPress(KeyEvent.VK_S);
         r.waitForIdle();
         r.keyRelease(KeyEvent.VK_CONTROL);  
         r.keyRelease(KeyEvent.VK_S);  
    }
    
}           