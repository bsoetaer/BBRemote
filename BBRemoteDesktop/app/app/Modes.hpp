#pragma once
enum class Mode { KEYBOARD = 1, TOUCHPAD = 2, OPTICAL = 3, GAMEPAD = 6};
const int MOUSE_PACKET_SIZE = 3;
const int KEYBOARD_PACKET_SIZE = 8;
const int GAMEPAD_PACKET_SIZE = 6;

const int KEYBOARD_MAX_KEYS_DOWN = 6;