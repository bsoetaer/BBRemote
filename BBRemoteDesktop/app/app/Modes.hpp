/*
Requirements covered:
3.2.2.2.1. Mode Select with Valid Connection
*/

#pragma once
enum class Mode { NO_MODE = 0, KEYBOARD = 1, TOUCHPAD = 2, OPTICAL = 3, GAMEPAD = 6 };
const int MOUSE_PACKET_SIZE = 4;
const int KEYBOARD_PACKET_SIZE = 8;
const int GAMEPAD_PACKET_SIZE = 6;

const int KEYBOARD_MAX_KEYS_DOWN = 6;