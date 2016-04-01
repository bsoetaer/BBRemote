package com.example.bbschool.bbremotemobile;

import java.util.HashMap;

/**
 * Created by Braeden on 3/9/2016.
 */
public final class AndroidKeyCodes {

    private static HashMap<Integer, String> CodeToKey = new HashMap<Integer, String>();
    private static HashMap<String, Integer> KeyToCode = new HashMap<String, Integer>();

    //TODO Find some way to use the integers from the resource file instead of hard coding here
    //TODO Make not strings on other side. Maybe an enum with reverse lookup somehow
    static {
        CodeToKey.put(0, "0");
        CodeToKey.put(1, "1");
        CodeToKey.put(2, "2");
        CodeToKey.put(3, "3");
        CodeToKey.put(4, "4");
        CodeToKey.put(5, "5");
        CodeToKey.put(6, "6");
        CodeToKey.put(7, "7");
        CodeToKey.put(8, "8");
        CodeToKey.put(9, "9");
        CodeToKey.put(10, "A");
        CodeToKey.put(11, "B");
        CodeToKey.put(12, "C");
        CodeToKey.put(13, "D");
        CodeToKey.put(14, "E");
        CodeToKey.put(15, "F");
        CodeToKey.put(16, "G");
        CodeToKey.put(17, "H");
        CodeToKey.put(18, "I");
        CodeToKey.put(19, "J");
        CodeToKey.put(20, "K");
        CodeToKey.put(21, "L");
        CodeToKey.put(22, "M");
        CodeToKey.put(23, "N");
        CodeToKey.put(24, "O");
        CodeToKey.put(25, "P");
        CodeToKey.put(26, "Q");
        CodeToKey.put(27, "R");
        CodeToKey.put(28, "S");
        CodeToKey.put(29, "T");
        CodeToKey.put(30, "U");
        CodeToKey.put(31, "V");
        CodeToKey.put(32, "W");
        CodeToKey.put(33, "X");
        CodeToKey.put(34, "Y");
        CodeToKey.put(35, "Z");
        CodeToKey.put(36, "SHIFT");
        CodeToKey.put(37, "CTRL");
        CodeToKey.put(38, "ALT");
        CodeToKey.put(39, "DEL");
        CodeToKey.put(40, "BACKSPACE");
        CodeToKey.put(41, "WIN");
        CodeToKey.put(42, "TAB");
        CodeToKey.put(43, "SPACE");
        CodeToKey.put(44, "ENTER");
        CodeToKey.put(45, "ESC");
        CodeToKey.put(46, "PRINT SCREEN");
        CodeToKey.put(47, "SCROLL LOCK");
        CodeToKey.put(48, "PAUSE");
        CodeToKey.put(49, "INSERT");
        CodeToKey.put(50, "HOME");
        CodeToKey.put(51, "END");
        CodeToKey.put(52, "PAGE UP");
        CodeToKey.put(53, "PAGE DOWN");
        CodeToKey.put(54, "LEFT ARROW");
        CodeToKey.put(55, "RIGHT ARROW");
        CodeToKey.put(56, "DOWN ARROW");
        CodeToKey.put(57, "UP ARROW");
        CodeToKey.put(58, "F1");
        CodeToKey.put(59, "F2");
        CodeToKey.put(60, "F3");
        CodeToKey.put(61, "F4");
        CodeToKey.put(62, "F5");
        CodeToKey.put(63, "F6");
        CodeToKey.put(64, "F7");
        CodeToKey.put(65, "F8");
        CodeToKey.put(66, "F9");
        CodeToKey.put(67, "F10");
        CodeToKey.put(68, "F11");
        CodeToKey.put(69, "F12");
        CodeToKey.put(70, "!");
        CodeToKey.put(71, "@");
        CodeToKey.put(72, "#");
        CodeToKey.put(73, "$");
        CodeToKey.put(74, "/");
        CodeToKey.put(75, "^");
        CodeToKey.put(76, "&");
        CodeToKey.put(77, "*");
        CodeToKey.put(78, "(");
        CodeToKey.put(79, ")");
        CodeToKey.put(80, "-");
        CodeToKey.put(81, "'");
        CodeToKey.put(82, "\"");
        CodeToKey.put(83, ":");
        CodeToKey.put(84, ";");
        CodeToKey.put(85, ",");
        CodeToKey.put(86, "?");
        CodeToKey.put(87, ".");
        CodeToKey.put(88, "+");
        CodeToKey.put(89, "=");
        CodeToKey.put(90, "<");
        CodeToKey.put(91, ">");
        CodeToKey.put(92, "{");
        CodeToKey.put(93, "}");
        CodeToKey.put(94, "[");
        CodeToKey.put(95, "]");
        CodeToKey.put(96, "%");
        CodeToKey.put(97, "~");
        CodeToKey.put(98, "`");
        CodeToKey.put(99, "_");
        CodeToKey.put(100, "\\");
        CodeToKey.put(101, "|");
        CodeToKey.put(102, "CHANGE MODE");
        CodeToKey.put(103, "CAPS");

        for (Integer i: CodeToKey.keySet()) {
            KeyToCode.put(CodeToKey.get(i), i);
        }
    }

    private AndroidKeyCodes(){}

    public static Integer lookupCode(String key){
        return KeyToCode.get(key);
    }

    public static String lookupKey(Integer code){
        return CodeToKey.get(code);
    }
    
}
