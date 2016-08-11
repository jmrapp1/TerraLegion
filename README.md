# TerraLegion 

A clone of the popular game, Terraria, developed using Java and the LibGDX framework. The reason that this project is open-source is to encourage community contributions and create a game centered around the development done by those in the gaming community. Contributions are heavily welcomed and I would love to see developers being able to place their own mark on the game. The name "TerraLegion" was heavily influenced by the idea of community contributions. "Terra" making a reference to "Terraria", the inspiration of this game, and "Legion" referring to the army of developers behind it.

![](https://cloud.githubusercontent.com/assets/7306503/17391892/7f93f3f2-59ce-11e6-8e88-9a50d4074c0c.png)

### Contributing
When adding your own features to the game or modifying existing code, there are a few things to keep in mind to ensure that your pull requests are accepted. Before submitting new additions, be sure to reference the list below as it may be modified in the future.

1. Because the game is aimed at being developed for mobile devices, the processing and graphical power that we're able to work with is very small when put in comparison with desktop computers and laptops. A game that runs at 60 FPS on a desktop computer may only run at 20 FPS on a mobile device depending on the optimization techniques applied. Before pushing new code to the repository, be sure to test your version of the game on an Android device to ensure that it runs smoothly. If you don't have an Android device to test it on, use an application like [Genymotion](https://www.genymotion.com/) which provides emulators of Android devices. 
2. Following proper coding standards is a must. Be sure to follow object-oriented design standards, implement design patterns when able to, and document all code following documentation standards for Java.  
3. If your additions add a new utility or module to the game, be sure to add a separate testing application to the "test" package of the code. This application should simply provide test cases, showing the functions of your new additions. For example, you can find the [InventoryTest Application](https://github.com/baseball435/Terraria-Clone/blob/master/core/src/com/jmrapp/terralegion/test/InventoryTest.java) which simply tests different methods of the inventory class. You can also find the [Noise Visualization Utility](https://github.com/baseball435/Terraria-Clone/blob/master/core/src/com/jmrapp/terralegion/test/NoiseVisualizationScreen.java) which allows you to test and visualize the outcomes of Simplex Noise.  

### Want To Contribute But Don't Know Where To Start?
[Read the Wiki!](https://github.com/jmrapp1/TerraLegion/wiki) This will provide all of the information you need to understand the architecture of the game and how it functions.

### Current Features
- **World Generation**: Over 1,500,000 blocks in the world.
- **Lighting System**: Efficient with almost no boundaries when placing light sources.
- **World Saving**: The world is split into chunks and saved to the device. It can then be loaded again.
- **World Destruction**: Break blocks using the different tools. The action joystick will place or break blocks within a certain radius dependent on the item you have in your hand.
- **Item System**: Pick up and use items or blocks. Blocks will be placed on the ground and items, if they're tools, will destroy the environment
- **Entity System**: Implement custom AI and entities with the entity system that is in place.

### What Can You Work On?

- **Issues:** Working on [opened issues](https://github.com/jmrapp1/TerraLegion/issues) is the first thing I'd recommend trying out before going on to add your own content. It will give you a bit of a feel for the code and make adding content a little bit smoother later on.
- **Inventory Block:** In Minecraft and Terraria having a place to put your items is very important. We will need to create a new Block type called an *InventoryBlock*. This should be an abstract class that extends the *Block* class. It should contain an *Inventory* instance and provide getter/setter methods for it. From this other classes should extend upon it. For example, a *Chest* class can be created that extends the *InventoryBlock* and allows interaction by tapping on it (for now). When it is tapped on, it should open the *InventoryScreen* and display the contents of the inventory. At some point an extension of the *InventoryScreen* class will need to be made to show the Player's inventory along with the inventory of the chest.
- **Graphics:** If you consider yourself to be a good graphic artist, the game is in dire need of new graphics. New images for tiles, characters, entities, etc. need to be created and I would love to see any work that you have. At some point animations will also need to be added to the game. These will most likely work using sprite strips, but that is not set in stone. 
- **Chunk Size Change:** Use a chunk size that is a power of two (such as 64) and not 50. This would allow you to use bitshifts and masks to determine block positions rather than using division and multiplication. If you do not care for limiting your game world then the upper bits of an integer would be the chunk position, the next lower bits would be the tile position in the chunk, and the remaining bits could be sub-tile position but this would not be required unless you want uniform object space coordinates and world space coordinates. *Suggested by [mpasteven](https://www.reddit.com/user/mpasteven)*
- **Separate Rendering From Logic:** Split the rendering logic from the game code and instead have it in its own. This way in the event we need to rework or add additions to the game logic or the render logic we do not need to decouple the code to change it. You could place the renderer code in a `renderer` package potentially. *Suggested by [mpasteven](https://www.reddit.com/user/mpasteven)*

### Installation & Building
1. Fork the repository and create your own branch.
2. Using Android Studio, open an existing project and navigate to the directory where your branch is stored. Select build.gradle and open the project from that file.
3. From the top menu, select *Run->Edit Configurations*. We need to add a new run configuration to allow you to run the game on your desktop/laptop.
4. Select the *+* button and then choose *Application* from the drop down menu.
5. Enter a name for the configuration at the top. 
6. For the *Main Class* setting, press the browse button and select the *DesktopLauncher* class.
7. For the *Working Directory* setting, press the browse button and navigate to and select the directory *<your branch>/android/assets/*
8. For the *Use Classpath of Module* setting, select the *desktop* project from the drop down menu.
9. Apply the changes and press *OK*. 
10. You should now be able to run the game from Desktop and Android using the two run configurations.

### Gallery (Artwork done by [Kenney](http://kenney.nl/))
**Video:**
https://www.youtube.com/watch?v=-z4o8CwJR8E

**Images**

![](https://cloud.githubusercontent.com/assets/7306503/17391892/7f93f3f2-59ce-11e6-8e88-9a50d4074c0c.png)
![](https://cloud.githubusercontent.com/assets/7306503/17390271/5988fdaa-59c0-11e6-9000-9f74783870ce.png)

### License
The MIT License (MIT)

Copyright (c) 2016 Jon R

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
