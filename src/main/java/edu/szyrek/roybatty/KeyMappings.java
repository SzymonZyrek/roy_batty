package edu.szyrek.roybatty;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyMappings {
    private static Map<Integer,Integer> _awtToJnativeCodes = new HashMap<>();
    private static Map<Integer,Integer> _jnativeToAwtCodes = new HashMap<>();
    private static Map<String,Integer> _textToJnativeCodes = new HashMap<>();
    private static Map<Integer,String> _nativeCodesToText = new HashMap<>();

    public static String nativeCodesToText(int nativeCode)
    {
        if (_nativeCodesToText.containsKey(nativeCode))
        {
            return _nativeCodesToText.get(nativeCode);
        }
        else
        {
            RoyBatty.logError("Not mapped native key code: "+nativeCode);
            return "";
        }
    }

    public static int awtToJnativeCodes(int awtCode)
    {
        if (_awtToJnativeCodes.containsKey(awtCode))
        {
            return _awtToJnativeCodes.get(awtCode);
        }
        else
        {
            RoyBatty.logError("Not mapped awt key code: "+awtCode);
            return -1;
        }
    }

    public static int jnativeToAwtCodes(int nativeCode)
    {
        if (_jnativeToAwtCodes.containsKey(nativeCode))
        {
            return _jnativeToAwtCodes.get(nativeCode);
        }
        else
        {
            RoyBatty.logError("Not mapped native key code: "+nativeCode);
            return -1;
        }
    }

    public static int textToJnativeCodes(final String text)
    {
        if (_textToJnativeCodes.containsKey(text))
        {
            return _textToJnativeCodes.get(text);
        }
        else
        {
            RoyBatty.logError("Not mapped key text: "+text);
            return -1;
        }
    }

    static
    {
        _awtToJnativeCodes.put(KeyEvent.VK_SPACE, NativeKeyEvent.VC_SPACE);
        _awtToJnativeCodes.put(KeyEvent.VK_ENTER, NativeKeyEvent.VC_ENTER);
        _awtToJnativeCodes.put(KeyEvent.VK_BACK_SPACE, NativeKeyEvent.VC_BACKSPACE);
        _awtToJnativeCodes.put(KeyEvent.VK_DELETE, NativeKeyEvent.VC_DELETE);
        _awtToJnativeCodes.put(KeyEvent.VK_END, NativeKeyEvent.VC_END);
        _awtToJnativeCodes.put(KeyEvent.VK_HOME, NativeKeyEvent.VC_HOME);
        _awtToJnativeCodes.put(KeyEvent.VK_SLASH, NativeKeyEvent.VC_SLASH);
        _awtToJnativeCodes.put(KeyEvent.VK_BACK_SLASH, NativeKeyEvent.VC_BACK_SLASH);
        _awtToJnativeCodes.put(KeyEvent.VK_QUOTE, NativeKeyEvent.VC_QUOTE);
        _awtToJnativeCodes.put(KeyEvent.VK_BACK_QUOTE, NativeKeyEvent.VC_BACKQUOTE);
        _awtToJnativeCodes.put(KeyEvent.VK_COLON, NativeKeyEvent.VC_SEMICOLON);
        _awtToJnativeCodes.put(KeyEvent.VK_SEMICOLON, NativeKeyEvent.VC_SEMICOLON);
        _awtToJnativeCodes.put(KeyEvent.VK_COMMA, NativeKeyEvent.VC_COMMA);
        _awtToJnativeCodes.put(KeyEvent.VK_UP, NativeKeyEvent.VC_UP);
        _awtToJnativeCodes.put(KeyEvent.VK_DOWN, NativeKeyEvent.VC_DOWN);
        _awtToJnativeCodes.put(KeyEvent.VK_LEFT, NativeKeyEvent.VC_LEFT);
        _awtToJnativeCodes.put(KeyEvent.VK_RIGHT, NativeKeyEvent.VC_RIGHT);
        _awtToJnativeCodes.put(KeyEvent.VK_KP_UP, NativeKeyEvent.VC_UP);
        _awtToJnativeCodes.put(KeyEvent.VK_KP_DOWN, NativeKeyEvent.VC_DOWN);
        _awtToJnativeCodes.put(KeyEvent.VK_KP_LEFT, NativeKeyEvent.VC_LEFT);
        _awtToJnativeCodes.put(KeyEvent.VK_KP_RIGHT, NativeKeyEvent.VC_RIGHT);
        //jnativeTo_awtCodes.put(KeyEvent.VK_LEFT_PARENTHESIS, NativeKeyEvent.VC_OPEN_BRACKET);
        //jnativeTo_awtCodes.put(KeyEvent.VK_RIGHT_PARENTHESIS, NativeKeyEvent.VC_CLOSE_BRACKET);
        _awtToJnativeCodes.put(KeyEvent.VK_PAGE_UP, NativeKeyEvent.VC_PAGE_UP);
        _awtToJnativeCodes.put(KeyEvent.VK_PAGE_DOWN, NativeKeyEvent.VC_PAGE_DOWN);
        _awtToJnativeCodes.put(KeyEvent.VK_CAPS_LOCK, NativeKeyEvent.VC_CAPS_LOCK);
        _awtToJnativeCodes.put(KeyEvent.VK_SCROLL_LOCK, NativeKeyEvent.VC_SCROLL_LOCK);
        _awtToJnativeCodes.put(KeyEvent.VK_NUM_LOCK, NativeKeyEvent.VC_NUM_LOCK);
        _awtToJnativeCodes.put(KeyEvent.VK_EQUALS, NativeKeyEvent.VC_EQUALS);
        _awtToJnativeCodes.put(KeyEvent.VK_MINUS, NativeKeyEvent.VC_MINUS);
        //jnativeTo_awtCodes.put(KeyEvent.VK_PLUS, NativeKeyEvent.VC_);
        //jnativeTo_awtCodes.put(KeyEvent.VK_ASTERISK, NativeKeyEvent.VC_);
        _awtToJnativeCodes.put(KeyEvent.VK_TAB, NativeKeyEvent.VC_TAB);
        _awtToJnativeCodes.put(KeyEvent.VK_SHIFT, NativeKeyEvent.VC_SHIFT);
        _awtToJnativeCodes.put(KeyEvent.VK_ALT, NativeKeyEvent.VC_ALT);
        _awtToJnativeCodes.put(KeyEvent.VK_CONTROL, NativeKeyEvent.VC_CONTROL);
        _awtToJnativeCodes.put(KeyEvent.VK_INSERT, NativeKeyEvent.VC_INSERT);

        _awtToJnativeCodes.put(KeyEvent.VK_0, NativeKeyEvent.VC_0);
        _awtToJnativeCodes.put(KeyEvent.VK_1, NativeKeyEvent.VC_1);
        _awtToJnativeCodes.put(KeyEvent.VK_2, NativeKeyEvent.VC_2);
        _awtToJnativeCodes.put(KeyEvent.VK_3, NativeKeyEvent.VC_3);
        _awtToJnativeCodes.put(KeyEvent.VK_4, NativeKeyEvent.VC_4);
        _awtToJnativeCodes.put(KeyEvent.VK_5, NativeKeyEvent.VC_5);
        _awtToJnativeCodes.put(KeyEvent.VK_6, NativeKeyEvent.VC_6);
        _awtToJnativeCodes.put(KeyEvent.VK_7, NativeKeyEvent.VC_7);
        _awtToJnativeCodes.put(KeyEvent.VK_8, NativeKeyEvent.VC_8);
        _awtToJnativeCodes.put(KeyEvent.VK_9, NativeKeyEvent.VC_9);

        _awtToJnativeCodes.put(KeyEvent.VK_Q, NativeKeyEvent.VC_Q);
        _awtToJnativeCodes.put(KeyEvent.VK_W, NativeKeyEvent.VC_W);
        _awtToJnativeCodes.put(KeyEvent.VK_E, NativeKeyEvent.VC_E);
        _awtToJnativeCodes.put(KeyEvent.VK_R, NativeKeyEvent.VC_R);
        _awtToJnativeCodes.put(KeyEvent.VK_T, NativeKeyEvent.VC_T);
        _awtToJnativeCodes.put(KeyEvent.VK_Y, NativeKeyEvent.VC_Y);
        _awtToJnativeCodes.put(KeyEvent.VK_U, NativeKeyEvent.VC_U);
        _awtToJnativeCodes.put(KeyEvent.VK_I, NativeKeyEvent.VC_I);
        _awtToJnativeCodes.put(KeyEvent.VK_O, NativeKeyEvent.VC_O);
        _awtToJnativeCodes.put(KeyEvent.VK_P, NativeKeyEvent.VC_P);
        _awtToJnativeCodes.put(KeyEvent.VK_A, NativeKeyEvent.VC_A);
        _awtToJnativeCodes.put(KeyEvent.VK_S, NativeKeyEvent.VC_S);
        _awtToJnativeCodes.put(KeyEvent.VK_D, NativeKeyEvent.VC_D);
        _awtToJnativeCodes.put(KeyEvent.VK_F, NativeKeyEvent.VC_F);
        _awtToJnativeCodes.put(KeyEvent.VK_G, NativeKeyEvent.VC_G);
        _awtToJnativeCodes.put(KeyEvent.VK_H, NativeKeyEvent.VC_H);
        _awtToJnativeCodes.put(KeyEvent.VK_J, NativeKeyEvent.VC_J);
        _awtToJnativeCodes.put(KeyEvent.VK_K, NativeKeyEvent.VC_K);
        _awtToJnativeCodes.put(KeyEvent.VK_L, NativeKeyEvent.VC_L);
        _awtToJnativeCodes.put(KeyEvent.VK_Z, NativeKeyEvent.VC_Z);
        _awtToJnativeCodes.put(KeyEvent.VK_X, NativeKeyEvent.VC_X);
        _awtToJnativeCodes.put(KeyEvent.VK_C, NativeKeyEvent.VC_C);
        _awtToJnativeCodes.put(KeyEvent.VK_V, NativeKeyEvent.VC_V);
        _awtToJnativeCodes.put(KeyEvent.VK_B, NativeKeyEvent.VC_B);
        _awtToJnativeCodes.put(KeyEvent.VK_N, NativeKeyEvent.VC_N);
        _awtToJnativeCodes.put(KeyEvent.VK_M, NativeKeyEvent.VC_M);

        _awtToJnativeCodes.put(KeyEvent.VK_F1, NativeKeyEvent.VC_F1);
        _awtToJnativeCodes.put(KeyEvent.VK_F2, NativeKeyEvent.VC_F2);
        _awtToJnativeCodes.put(KeyEvent.VK_F3, NativeKeyEvent.VC_F3);
        _awtToJnativeCodes.put(KeyEvent.VK_F4, NativeKeyEvent.VC_F4);
        _awtToJnativeCodes.put(KeyEvent.VK_F5, NativeKeyEvent.VC_F5);
        _awtToJnativeCodes.put(KeyEvent.VK_F6, NativeKeyEvent.VC_F6);
        _awtToJnativeCodes.put(KeyEvent.VK_F7, NativeKeyEvent.VC_F7);
        _awtToJnativeCodes.put(KeyEvent.VK_F8, NativeKeyEvent.VC_F8);
        _awtToJnativeCodes.put(KeyEvent.VK_F9, NativeKeyEvent.VC_F9);
        _awtToJnativeCodes.put(KeyEvent.VK_F10, NativeKeyEvent.VC_F10);
        _awtToJnativeCodes.put(KeyEvent.VK_F11, NativeKeyEvent.VC_F11);
        _awtToJnativeCodes.put(KeyEvent.VK_F12, NativeKeyEvent.VC_F12);

        for (final Integer key: _awtToJnativeCodes.keySet())
        {
            Integer value = _awtToJnativeCodes.get(key);
            _jnativeToAwtCodes.put(value, key);
            _textToJnativeCodes.put(NativeKeyEvent.getKeyText(value), value);
            _nativeCodesToText.put(value, NativeKeyEvent.getKeyText(value));
        }
    }
}
