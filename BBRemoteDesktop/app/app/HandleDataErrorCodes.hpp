/*
Requirements covered:
3.2.3.2.1. Send Key Input
3.2.4.2.1. Send Mouse Movement
3.2.4.2.3. Left Click
3.2.4.2.4. Right Click
3.2.4.2.5. Scrolling
3.2.5.2.1. Send Mouse Movement
3.2.5.2.4. Left Click
3.2.5.2.5. Right Click
3.2.5.2.6. Scrolling
3.2.5.2.8. Middle Mouse Button
3.2.7.2.1. Press Gamepad Button
3.2.7.2.2. Adjust Gamepad Axis
3.2.7.2.3. Press Toggle Button
3.2.7.2.7. Multiple Input Press
3.2.7.2.9. Rotate Phone
3.2.9.2.1. Send Sound
3.2.10.2.1. Send Video
3.2.10.2.2. Send Audio
3.2.11.2.1. Use Mic and a touchscreen input together
*/

#pragma once

#define SUCCESS 0
#define ERROR_NOT_AXIS_OR_BUTTON 1
#define ERROR_UNEVEN_BYTE_COUNT 2
#define ERROR_AXIS_DOES_NOT_EXIST 3
#define ERROR_BUTTON_DOES_NOT_EXIST 4
#define ERROR_BUTTON_VALUE_INVALID 5
#define ERROR_NOT_AXIS 6
#define ERROR_NOT_BUTTON 7