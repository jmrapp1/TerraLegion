# Terraria-Clone
A clone of the popular game, Terraria, developed using Java and the LibGDX framework. The reason that this project is open-source is to encourage community contributions and create a game centered around the development done by those in the gaming community. Contributions are heavily welcomed and I would love to see developers being able to place their own mark on the game.

### Contributing
When adding your own features to the game or modifying existing code, there are a few things to keep in mind to ensure that your pull requests are accepted. Before submitting new additions, be sure to reference the list below as it may be modified in the future.

1. Because the game is aimed at being developed for mobile devices, the processing and graphical power that we're able to work with is very small when put in comparison with desktop computers and laptops. A game that runs at 60 FPS on a desktop computer may only run at 20 FPS on a mobile device depending on the optimization techniques applied. Before pushing new code to the repository, be sure to test your version of the game on an Android device to ensure that it runs smoothly. If you don't have an Android device to test it on, use an application like [Genymotion](https://www.genymotion.com/) which provides emulators of Android devices. 
2. Following proper coding standards is a must. Be sure to follow object-oriented design standards, implement design patterns when able to, and document all code following documentation standards for Java.  
3. If your additions add a new utility or module to the game, be sure to add a separate testing application to the "test" package of the code. This application should simply provide test cases, showing the functions of your new additions. For example, you can find the [InventoryTest Application](https://github.com/baseball435/Terraria-Clone/blob/master/core/src/com/jmr/terraria/test/InventoryTest.java) which simply tests different methods of the inventory class. You can also find the [Noise Visualization Utility](https://github.com/baseball435/Terraria-Clone/blob/master/core/src/com/jmr/terraria/test/NoiseVisualizationScreen.java) which allows you to test and visualize the outcomes of Simplex Noise.  

### Current Features
- **World Generation**: Over 1,500,000 blocks in the world.
- **Lighting System**: Efficient with almost no boundaries when placing light sources.
- **World Saving**: The world is split into chunks and saved to the device. It can then be loaded again.
- **World Destruction**: Break blocks using the different tools. The action joystick will place or break blocks within a certain radius dependent on the item you have in your hand.
- **Item System**: Pick up and use items or blocks. Blocks will be placed on the ground and items, if they're tools, will destroy the environment
- **Entity System**: Implement custom AI and entities with the entity system that is in place.

### Being Worked On

- Documentation of all existing code

\* *If you are working on a new feature and plan on completing it, please request to add the feature to this list.* \*

### Known Issues
- **Lighting [Difficult]**: The lighting system has been one of the toughest systems to implement as it has to be **very** efficient and **very** dynamic. As of right now placing a single light source and then removing the light source sets and removes the light values correctly. The problems arise when clumping light sources around eachother. The light values are added correctly, but when removing the light sources it doesn't remove all of the light that it should. The algorithm is a recursive algorithm and may take time to trace through and actually understand. 
- **Crafting [Easy]**: The design of the crafting screen that I have in my head is for the crafting recipes to be sorted in categories based on the type of item they are. So separation of tools, weapons, miscellaneous, etc. As of right now it is sorting them in separate categories, but it is not checking if you have the required items to actually craft it. When selecting the craft option it just gives the player the item. 
- **Blocks Placed In Entities [Medium Difficulty]**: Blocks are able to be placed on top of entities. This specific (issue was submitted by AppPhil)[https://github.com/baseball435/Terraria-Clone/issues/2] when he was able to place a block ontop of a buny.

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

### Video
https://www.youtube.com/watch?v=-z4o8CwJR8E

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
