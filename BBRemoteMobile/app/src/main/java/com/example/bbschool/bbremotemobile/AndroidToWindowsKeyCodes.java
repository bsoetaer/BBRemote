package com.example.bbschool.bbremotemobile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brendan on 3/31/2016.
 */
public final class AndroidToWindowsKeyCodes {

    private AndroidToWindowsKeyCodes() {}

    private static final Map<Integer, Integer> AndroidToWindows = new HashMap<>();

    static {
        AndroidToWindows.put(0, 39);
        AndroidToWindows.put(1, 30);
        AndroidToWindows.put(2, 31);
        AndroidToWindows.put(3, 32);
        AndroidToWindows.put(4, 33);
        AndroidToWindows.put(5, 34);
        AndroidToWindows.put(6, 35);
        AndroidToWindows.put(7, 36);
        AndroidToWindows.put(8, 37);
        AndroidToWindows.put(9, 38);
        AndroidToWindows.put(10, 4);
        AndroidToWindows.put(11, 5);
        AndroidToWindows.put(12, 6);
        AndroidToWindows.put(13, 7);
        AndroidToWindows.put(14, 8);
        AndroidToWindows.put(15, 9);
        AndroidToWindows.put(16, 10);
        AndroidToWindows.put(17, 11);
        AndroidToWindows.put(18, 12);
        AndroidToWindows.put(19, 13);
        AndroidToWindows.put(20, 14);
        AndroidToWindows.put(21, 15);
        AndroidToWindows.put(22, 16);
        AndroidToWindows.put(23, 17);
        AndroidToWindows.put(24, 18);
        AndroidToWindows.put(25, 19);
        AndroidToWindows.put(26, 20);
        AndroidToWindows.put(27, 21);
        AndroidToWindows.put(28, 22);
        AndroidToWindows.put(29, 23);
        AndroidToWindows.put(30, 24);
        AndroidToWindows.put(31, 25);
        AndroidToWindows.put(32, 26);
        AndroidToWindows.put(33, 27);
        AndroidToWindows.put(34, 28);
        AndroidToWindows.put(35, 29);
        AndroidToWindows.put(36, 225); // left shift
        AndroidToWindows.put(37, 224); // left ctrl
        AndroidToWindows.put(38, 226); // left alt
        AndroidToWindows.put(39, 76);
        AndroidToWindows.put(40, 42);
        AndroidToWindows.put(41, 231);
        AndroidToWindows.put(42, 43);
        AndroidToWindows.put(43, 44);
        AndroidToWindows.put(44, 40);
        AndroidToWindows.put(45, 41);
        AndroidToWindows.put(46, 70);
        AndroidToWindows.put(47, 71);
        AndroidToWindows.put(48, 72);
        AndroidToWindows.put(49, 73);
        AndroidToWindows.put(50, 74);
        AndroidToWindows.put(51, 77);
        AndroidToWindows.put(52, 75);
        AndroidToWindows.put(53, 78);
        AndroidToWindows.put(54, 80);
        AndroidToWindows.put(55, 79);
        AndroidToWindows.put(56, 81);
        AndroidToWindows.put(57, 82);
        AndroidToWindows.put(58, 58);
        AndroidToWindows.put(59, 59);
        AndroidToWindows.put(60, 60);
        AndroidToWindows.put(61, 61);
        AndroidToWindows.put(62, 62);
        AndroidToWindows.put(63, 63);
        AndroidToWindows.put(64, 64);
        AndroidToWindows.put(65, 65);
        AndroidToWindows.put(66, 66);
        AndroidToWindows.put(67, 67);
        AndroidToWindows.put(68, 68);
        AndroidToWindows.put(69, 69);
        AndroidToWindows.put(70, 30); // 1
        AndroidToWindows.put(71, 31); // 2
        AndroidToWindows.put(72, 32); // 3
        AndroidToWindows.put(73, 33); // 4
        AndroidToWindows.put(74, 56);
        AndroidToWindows.put(75, 35); // 6
        AndroidToWindows.put(76, 36); // 7
        AndroidToWindows.put(77, 85);
        AndroidToWindows.put(78, 38); // 9
        AndroidToWindows.put(79, 39); // 0
        AndroidToWindows.put(80, 45);
        AndroidToWindows.put(81, 52);
        AndroidToWindows.put(82, 52); // '
        AndroidToWindows.put(83, 51); // ;
        AndroidToWindows.put(84, 51);
        AndroidToWindows.put(85, 54);
        AndroidToWindows.put(86, 56); // /
        AndroidToWindows.put(87, 55);
        AndroidToWindows.put(88, 87); // keypad +
        AndroidToWindows.put(89, 46);
        AndroidToWindows.put(90, 54); // ,
        AndroidToWindows.put(91, 55); // .
        AndroidToWindows.put(92, 47); // [
        AndroidToWindows.put(93, 48); // ]
        AndroidToWindows.put(94, 47);
        AndroidToWindows.put(95, 48);
        AndroidToWindows.put(96, 34); // 5
        AndroidToWindows.put(97, 53);
        AndroidToWindows.put(98, 53); // `
        AndroidToWindows.put(99, 45); // -
        AndroidToWindows.put(100, 49);
        AndroidToWindows.put(101, 100);
        //AndroidToWindows.put(102, "CHANGE MODE"); // note: this doesn't exist on a keyboard
        AndroidToWindows.put(103, 57);
        //AndroidToWindows.put(104, "LEFT CLICK");
        //AndroidToWindows.put(105, "RIGHT CLICK");
        //AndroidToWindows.put(106, "MIDDLE CLICK");
    }

    public static Integer lookupCode(Integer androidCode) {
        return AndroidToWindows.get(androidCode);
    }

}
