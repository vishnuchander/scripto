This is a script engine built on top of Java's AWT library. It allows you to automate mouse movements and clicks and key presses. It can also function as a calculator and has built in file reading capabilities to respond to things like server logs. It reads plain text script files that can either be opened using a file chooser or passed to the program via CLI. 

The following commands work with this script engine:
-read: reads another file
USAGE: read [at least one filename]

-comment: the script engine ignores this line, effectively allowing you to comment code for description or explanation
USAGE: comment [stuff you want to say]

-function: creates a function, which is effectively a sequence of commands to be executed in order. Commands that take functions as arguments:
--rpt
--brpt
--check
--check2
--logrun
--run
USAGE: function [at least one name]

-rpt: turns the main thread into a loop. 
USAGE: rpt [times] (set times to 0 if you want it to repeat indefinitely)

-brpt: turns a function into a loop
USAGE: brpt [times] [function name] (set times to 0 if you want it to repeat indefinitely)

-counter: creates a counter. Effectively this is an int. 
USAGE: counter [list of names]

-set: sets a counter
USAGE: set [counter name] [value]

-rset: sets a counter to a random number between 0 and the specified value
USAGE: rset [counter name] [value]

-increment: increases a counter by 1
USAGE: increment [counter name]

-decrement: decreases a counter by 1
USAGE: decrement [counter name]

-point: saves the location of a point on screen that is at the tip of your cursor
USAGE: point [at least one point name]

-spnp: saves the location of a point on screen that is at the tip of your cursor without using a popup. This command is only usable via a command line interface.
USAGE: spnp [at least one point name]

-move: move cursor to a point instantaneously
USAGE: move [point name]

-naturalmove: move cursor to a point slowly and gradually. Simulates user moving the mouse.
USAGE: naturalmove [point name]

-click: send a mouse click event. No arguments.
USAGE: click 

-mouseup: send a left mouse button release event. No arguments.
USAGE: mouserelease

-mousedown: send a left mouse button down event. No arguments.
USAGE: mousedown

-pixel: saves the color of the pixel at the tip of your cursor
USAGE: pixel [at least one name]

-check: checks if the color of the point specified is the same as its original value (when the color was saved). If not, it executes a function.
USAGE: check [pixel name] [function to execute]

-check2: checks if the color of the point specified is the same as its original value (when the color was saved). If it is, it executes a function.
USAGE: check2 [pixel name] [function to execute]

-keydown: sends a key down event
USAGE: keydown [key]

-keyup: sends a key release event
USAGE: keyup [key]

-keypress: sends a key press event
USAGE: keypress [key]

-log: creates a log reader object. This can be used to respond to logged events
USAGE: log [at least one log reader name]

-logf: creates a log reader object without using a file chooser window. 
USAGE: logf [log reader name] [path to log reader]

-logrun: execute a function as soon as a log reader object detects that a specific string has been written to a log file. Only detects exact matches for the entire string.
USAGE: logrun [log reader name] [function name] [string to watch for]
EXAMPLE: logrun ReaderObject MyFunction Hello World!

-wait: cause the current thread to wait for a specified amount of time
USAGE: wait [amount of time to wait]

-rwait: cause the current thread to wait for a random amount of time that is at most as long as the interval specified
USAGE: rwait [maximum amount of time to wait]

-run: execute a function
USAGE: run [function name]

-print: print a string to standard output. Does not end the printed statement with a newline character.
USAGE: print [string to output]

-lprint: print a string to standard output. Ends the printed statement with a newline character.
USAGE: lprint [string to output]

-cprint: print a counter to standard output. Does not end the printed statement with a newline character.
USAGE: cprint [counter name]

-lcprint: print a counter to standard output. Ends the printed statement with a newline character.
USAGE: lcprint [counter name]

-thread: Create a thread object that executes a function.
USAGE: thread [at least one function name]

-gt/ge/eq/le/lt/ne: Conditional logic functions. Greater Than, Greater than or Equal to, EQual, Less than or Equal to, Less Than, Not Equal. Compares two counters' values. Executes a function if the statement evaluates to true. 
USAGE: gt/ge/eq/le/lt/ne [counter name] [integer or counter name] [function to execute if true]
EXAMPLE: 
gt MyCounter 1 ComputeResult
comment the above statement compares MyCounter to 1. If MyCounter ] 1, it will execute ComputResult. 
